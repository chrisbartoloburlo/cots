package driver.interpreter

import driver.model._
import driver.model.Scope
import driver.synth.SynthDriver

import java.io.File
import scala.annotation.tailrec
import scala.collection.mutable
import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

class STInterpreter(sessionType: SessionType, path: String, preamble: String) {
  private val toolbox = currentMirror.mkToolBox()

  private var scopes = new mutable.HashMap[String, Scope]()
  private var curScope = "global"
  scopes(curScope) = new Scope(curScope, curScope, null)

  private var branches = new mutable.HashMap[Statement, String]()
  private var apis: List[String] = List()

  var synthDriver = new SynthDriver(this, path)

  def getRecursiveVarScope(recursiveVar: RecursiveVar): Scope = {
    checkRecVariable(scopes(curScope), recursiveVar)
  }

  def getBranchLabel(statement: Statement): String = {
    branches(statement)
  }

  def getCurScope(): String ={
    curScope
  }

  def getScopes: mutable.HashMap[String, Scope] = {
    scopes
  }

  def getApis(): List[String] = {
    apis
  }

  /**
   * Starts the interpreter and invokes the initialisation methods of SynthMon and SynthProtocol.
   *
   * @return A tuple consisting of two string builders in which the generated code for
   *         the monitor and cpsp classes is found.
   */
  def run(rejectSameLabels: Boolean): StringBuilder = {
    synthDriver.startInit(preamble)

    initialWalk(sessionType.statement, rejectSameLabels)
    curScope = "global"
    synthDriver.endInit(sessionType.name)
    synthDriver.defineReportSummary()
    summaryWalk(sessionType.statement)
    synthDriver.endDefineReportSummary()

    walk(sessionType.statement)
    synthDriver.end()
    synthDriver.getMon()
  }

  /**
   * Traverses the parse tree to set up the scopes found within the session type.
   *
   * @param root The root statement of the parse tree.
   */
  def initialWalk(root: Statement, rejectSameLabels: Boolean): Unit = {
    root match {
      case s@ReceiveStatement(api, label, statementID, types, probability, condition, continuation) =>
        createAndUpdateScope(label, statementID, rejectSameLabels)
        checkAndInitVariables(statementID, pruneSendingTypes(types), condition)
        synthDriver.handlePayloads(s)
        if (!apis.contains(api)) apis = apis :+ api
        initialWalk(continuation, rejectSameLabels)

      case s@SendStatement(label, responseCode, statementID, types, condition, continuation) =>
        createAndUpdateScope(label, statementID, rejectSameLabels)
        checkAndInitVariables(statementID, types, condition)
        synthDriver.handlePayloads(s)
        initialWalk(continuation, rejectSameLabels)

      case ReceiveChoiceStatement(label, choices) =>
        createAndUpdateScope(label, label, rejectSameLabels)
        val tmpScope = curScope
        for(choice <- choices) {
          createAndUpdateScope(choice.asInstanceOf[ReceiveStatement].label, choice.asInstanceOf[ReceiveStatement].statementID, rejectSameLabels)
          checkAndInitVariables(choice.asInstanceOf[ReceiveStatement].statementID, pruneSendingTypes(choice.asInstanceOf[ReceiveStatement].types), choice.asInstanceOf[ReceiveStatement].condition)
          synthDriver.handlePayloads(choice.asInstanceOf[ReceiveStatement])
          if (!apis.contains(choice.asInstanceOf[ReceiveStatement].api)) apis = apis :+ choice.asInstanceOf[ReceiveStatement].api
          initialWalk(choice.asInstanceOf[ReceiveStatement].continuation, rejectSameLabels)
          curScope = tmpScope
        }

      case s@SendChoiceStatement(label, choices) =>
        createAndUpdateScope(label, label, rejectSameLabels)
        val tmpScope = curScope
        synthDriver.handlePayloads(s)
        for(choice <- choices) {
          createAndUpdateScope(choice.asInstanceOf[SendStatement].label, choice.asInstanceOf[SendStatement].statementID, rejectSameLabels)
          checkAndInitVariables(choice.asInstanceOf[SendStatement].statementID, choice.asInstanceOf[SendStatement].types, choice.asInstanceOf[SendStatement].condition)
          synthDriver.handlePayloads(choice.asInstanceOf[SendStatement])
          initialWalk(choice.asInstanceOf[SendStatement].continuation, rejectSameLabels)
          curScope = tmpScope
        }

      case RecursiveStatement(label, body) =>
        scopes(curScope).recVariables(label) = body
        initialWalk(body, rejectSameLabels)

      case RecursiveVar(name, continuation) =>
        initialWalk(continuation, rejectSameLabels)

      case End() =>
    }
  }

  /**
   * Traverses the parse tree while invoking the respective methods in SynthMon
   * and SynthProtocol to generate the code.
   *
   * @param statement The root statement of the parse tree.
   */
  def walk(statement: Statement): Unit = {
    statement match {
      case statement @ ReceiveStatement(api, label, id, types, probability, condition, _) =>
        curScope = id
        checkCondition(label, pruneSendingTypes(types), condition)
        synthDriver.handleReceive(statement, statement.continuation, scopes(curScope).isUnique) // Change isUnique accordingly
        walk(statement.continuation)

      case statement @ SendStatement(label, responseCode, id, types, condition, _) =>
        curScope = id
        checkCondition(label, types, condition)

        synthDriver.handleSend(statement, statement.continuation, scopes(curScope).isUnique) // Change isUnique accordingly
        walk(statement.continuation)

      case statement @ ReceiveChoiceStatement(label, choices) =>
        curScope = label
        val tmpScope = curScope
        synthDriver.handleReceiveChoice(statement)
        var totalProb=0.0

        for(choice <- choices) {
          curScope = choice.asInstanceOf[ReceiveStatement].statementID
          checkCondition(choice.asInstanceOf[ReceiveStatement].label, pruneSendingTypes(choice.asInstanceOf[ReceiveStatement].types), choice.asInstanceOf[ReceiveStatement].condition)
          totalProb+=choice.asInstanceOf[ReceiveStatement].probability
          if(totalProb.round > 1){
            throw new Exception("Probabilities (" + totalProb + ") in " + label + choices + " exceed 1")
          }

          walk(choice.asInstanceOf[ReceiveStatement].continuation)
          curScope = tmpScope
        }

      case statement @ SendChoiceStatement(label, choices) =>
        curScope = label
        val tmpScope = curScope
        synthDriver.handleSendChoice(statement)

        for(choice <- choices) {
          curScope = choice.asInstanceOf[SendStatement].statementID
          checkCondition(choice.asInstanceOf[SendStatement].label, choice.asInstanceOf[SendStatement].types, choice.asInstanceOf[SendStatement].condition)

          walk(choice.asInstanceOf[SendStatement].continuation)
          curScope = tmpScope
        }

      case statement @ RecursiveStatement(label, body) =>
        walk(statement.body)

      case statement @ RecursiveVar(name, continuation) =>
        checkRecVariable(scopes(curScope), statement)
        walk(statement.continuation)

      case End() =>

      }
  }

  def summaryWalk(statement: Statement): Unit = {
    statement match {
      case ReceiveStatement(_, _, _, _, _, _, continuation) =>
        summaryWalk(continuation)
      case SendStatement(_, _, _, _, _, continuation) =>
        summaryWalk(continuation)
      case ReceiveChoiceStatement(_, choices)=>
        for(choice <- choices) {
          summaryWalk(choice)
        }
      case s @ SendChoiceStatement(_, choices) =>
        synthDriver.handelSendChoiceInfo(s)
        for(choice <- choices) {
          summaryWalk(choice)
        }
      case RecursiveStatement(_, body) =>
        summaryWalk(body)

      case RecursiveVar(_, continuation) =>
        summaryWalk(continuation)

      case End() =>
    }
  }

  /**
   * Creates a new scope and adds it to the mapping. The current scope is setup as the
   * parent scope of the new scope.
   *
   * @param label The label of the current statement as the name of the new scope.
   */
  private def createAndUpdateScope(label: String, id: String, rejectSameLabels: Boolean): Unit ={
//    if (searchParentScope(label) != null) {
//      throw new Exception("Label " + label + " is already defined in scope")
//    } else {
      val tmpScopes = searchScope(label)
      if(tmpScopes.isEmpty){
        scopes(id) = new Scope(label, id, scopes(curScope))
      } else {
        if(rejectSameLabels) throw new Exception("Label "+label+" already defined in session type; turn off flag to permit duplicate label names (labels will be renamed)")
        scopes(id) = new Scope(label, id, scopes(curScope))
        scopes(id).isUnique = false
        for(tmpScope <- tmpScopes){
          scopes(tmpScope.id).isUnique = false
//          new Scope(tmpScope.name, tmpScope.id, tmpScope.parentScope, false)
        }
      }
      curScope = id
//    }
  }

  def getScope(scopeName: String): Scope = {
    scopes(scopeName)
  }

  def getScope(statement: Statement): Scope = {
    statement match {
      case ReceiveStatement(api, label, statementID, types, probability, condition, continuation) =>
        scopes(statementID)

      case SendStatement(label, responseCode, statementID, types, condition, continuation) =>
        scopes(statementID)

      case ReceiveChoiceStatement(label, choices) =>
       scopes(label)

      case SendChoiceStatement(label, choices) =>
        scopes(label)

      case RecursiveStatement(label, body) =>
        getScope(body)

      case RecursiveVar(name, continuation) =>
        getScope(continuation)

      case End() => scopes(curScope)
    }
  }

  private def searchParentScope(label: String): Scope = {
    for(scope <- scopes){
      if(scope._2.name == label){
        if(scope._2.parentScope.name == curScope){
          return scope._2
        }
      }
    }
    null
  }

  def searchScope(label: String): mutable.ListBuffer[Scope] = {
    var tmpScopes = new mutable.ListBuffer[Scope]()
    for(scope <- scopes){
      if(scope._2.name == label){
        tmpScopes += scope._2
      }
    }
    tmpScopes
  }
  
  /**
   * Searches for a recursive variable recursively through the scopes. Once found it returns the scope.
   * Otherwise, an exception is thrown indicating that the variable does not exist.
   *
   * @param scope The scope to start searching from.
   * @param recursiveVar The recursive variable to search for.
   * @return The scope of the recursive variable if it exists.
   */
  @scala.annotation.tailrec
  private def checkRecVariable(scope: Scope, recursiveVar: RecursiveVar): Scope = {
    if(scope != null){
      if(!scopes(scope.id).recVariables.contains(recursiveVar.name)){
        checkRecVariable(scopes(scope.id).parentScope, recursiveVar)
      } else {
        scope
      }
    } else {
      throw new Exception("Error: Recursive variable "+recursiveVar.name+" not defined.")
    }
  }

  /**
   * Initialises the identifiers within the scope. If the current statement contains a condition,
   * the identifiers within the condition are retrieved and searched for within the previous scopes
   * starting from the current scope. If the identifier is found in another scope a flag is set to
   * indicate that the value it represents shall be used from within other statements.
   *
   * @param label The label of the current statement.
   * @param types A mapping from an identifier to its respective type (representing the payload of
   *              the current statement).
   * @param condition The condition of the current statement.
   */
  private def checkAndInitVariables(label: String, types: Map[String, String], condition: String): Unit ={
    val globalIdentifiersInStatement = getGlobalIdentifiersFromParams(types,  List())
    for(ident <- globalIdentifiersInStatement){
      val identScope = searchIdent(scopes(curScope).parentScope.id, ident)
      scopes(identScope).variables(ident) = (true, scopes(identScope).variables(ident)._2)
    }
    for(typ <- types) {
      if(!globalIdentifiersInStatement.contains(typ._1)) scopes(curScope).variables(typ._1) = (false, typ._2)
    }
    if (condition != null){
      val identifiersInCondition = getIdentifiersFromCondition(condition)
      for(ident <- identifiersInCondition){
        val identScope = searchIdent(curScope, ident)
        if(identScope != curScope) {
          scopes(identScope).variables(ident) = (true, scopes(identScope).variables(ident)._2)
        }
      }
    }
  }

  def pruneSendingTypes(sendingTypes: Map[String, (String, (String, List[String]))]): Map[String, String] = {
    sendingTypes.map{t => if(t._2!=null) (t._1, t._2._1) else (t._1, null)}
  }

  /**
   * Searches for an identifier recursively through the scopes. Once found it returns the scope.
   * Otherwise, an exception is thrown indicating that the identifier does not exist.
   *
   * @param tmpCurScope The scope from which the search starts.
   * @param identifierName The name of the identifier to be searched.
   * @return The scope of the identifier if it exists.
   */
  def searchIdent(tmpCurScope: String, identifierName: String): String = {
    if(!scopes(tmpCurScope).variables.contains(identifierName)){
      if(scopes(tmpCurScope).parentScope==null){
        throw new Exception("STInterpreter - Identifier "+identifierName+" not in scope")
      }
      searchIdent(scopes(tmpCurScope).parentScope.id, identifierName)
    } else {
      tmpCurScope
    }
  }

  /**
   * A traverser for traversing scala parse trees.
   */
  class traverser extends Traverser {
    var identifiers: List[String] = List[String]()

    /**
     * Populates a list of identifiers found within a scala parse tree.
     *
     * @param tree The scala parse tree.
     */
    override def traverse(tree: Tree): Unit = tree match {
      case i @ Ident(_) =>
        identifiers = i.name.decodedName.toString :: identifiers
        super.traverse(tree)
      case _ =>
        super.traverse(tree)
    }
  }

  @tailrec
  private def getGlobalIdentifiersFromParams(types: Map[String, String], identifierList: List[String]): List[String] ={
    if(types.isEmpty) identifierList
    else {
      if(types.head._2==null) getGlobalIdentifiersFromParams(types.tail, identifierList:+types.head._1)
      else getGlobalIdentifiersFromParams(types.tail, identifierList)
    }
  }

  /**
   * Parses a string to obtain a Scala parse tree which is passed to the traverse function in the traverser
   * and returns the distinct identifiers of the traverser excluding util since it represents the file
   * containing any boolean functions used in the condition.
   *
   * @param condition The condition to retrieve identifiers from.
   * @return The distinct identifiers found in the condition.
   */
  def getIdentifiersFromCondition(condition: String): List[String] = {
    val conditionTree = toolbox.parse(condition)
    val traverser = new traverser
    traverser.traverse(conditionTree)
    traverser.identifiers.distinct.filter(_ != "util")
  }

  /**
   * Type checks a condition of type String using the scala compiler. First, the identifiers are extracted
   * from the condition. Their type is then retrieved and appended to a string as variable declarations. The contents
   * of the util file are extracted as string. The latter, the variable declarations and the condition itself
   * are all appended to a string which is parsed using the Scala parsers and then type-checked using the
   * Scala compiler.
   *
   * @param label The label of the current statement.
   * @param types A mapping from an identifier to its respective type (representing the payload of
   *              the current statement).
   * @param condition The condition to type-check.
   * @return Whether the condition is of type boolean or not.
   */
  private def checkCondition(label: String, types: Map[String, String], condition: String): Boolean ={
    if(condition != null) {
      var util = ""
      if(condition.contains("util.")){
        val source = scala.io.Source.fromFile(path+"/util.scala", "utf-8")
        util = try source.mkString finally source.close()
        util = util.replaceFirst("package .*\n", "")
        util = util.replaceAll("import .*model.*\n", "")
      }
      var models = ""
      val dir = new File(path+"/model/")
      if (dir.exists && dir.isDirectory) {
        for(file <- dir.listFiles()){
          val model = scala.io.Source.fromFile(file, "utf-8")
          models = models.concat(try model.mkString finally model.close())
          models = models.replaceFirst("package .*\n", "")
        }
      }
      var stringVariables = ""
      val identifiersInCondition = getIdentifiersFromCondition(condition)
      for(identName <- identifiersInCondition){
        val identifier = scopes(searchIdent(curScope, identName)).variables(identName)
        stringVariables = stringVariables+"val "+identName+": "+identifier._2+"= ???;"
      }
      val eval = s"""
           |$util
           |$models
           |$stringVariables
           |$condition
           |""".stripMargin
      val tree = toolbox.parse(eval)
      val checked = toolbox.typecheck(tree)
      checked.tpe == Boolean
    }
    true
  }

  /**
   * Retrieves the information of an identifier.
   *
   * @param identName The name of the identifier whose information is to be retrieved.
   * @param tmpCurScope The scope from which to start the search.
   * @return A triple consisting of the scope of the identifier, the flag whether it is global or not,
   *         and the type of the identifier.
   */
  def getVarInfo(identName: String, tmpCurScope: String): (String, (Boolean, String)) = {
    (searchIdent(tmpCurScope, identName), scopes(searchIdent(tmpCurScope, identName)).variables(identName))
  }

}
