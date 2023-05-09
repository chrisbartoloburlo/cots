package driver.synth

import driver.interpreter.STInterpreter
import driver.model._

class SynthDriver(sessionTypeInterpreter: STInterpreter, path: String) {
  private val mon = new StringBuilder()

  def getMon(): StringBuilder = {
    mon
  }

  private var first = true

  /**
   * Generates the code for declaring a monitor including the imports required for the monitor to compile.
   *
   * @param preamble The contents of the preamble file.
   */
  def startInit(preamble: String): Unit = {
    if (preamble!="") mon.append(preamble+"\n")
    mon.append("import sttp.client3._\nimport driver.util.{logger, csvLogger}\nimport scala.util.control.TailCalls.{TailRec, done, tailcall}\nimport java.io.File\n")

    mon.append("class driver(setup: => Unit, teardown: => Unit, minimise: Boolean, max: Int, var seed: Int, repetitions: Int, report: String => Unit)")

    mon.append(" extends Runnable {\n")
    mon.append("\tobject info {\n")
  }

  /**
   * Generates the code for storing values used from other parts in the monitor.
   *
   * @param statement statement to setup the meta information variables of.
   */
  def handlePayloads(statement: Statement): Unit = statement match {
    case ReceiveStatement(_, _, statementID, types, probability, _, _) =>
      mon.append("\t\tobject "+statementID+" {\n")
      for(typ <- sessionTypeInterpreter.pruneSendingTypes(types)){
        if(typ._2!=null) mon.append("\t\t\tvar "+typ._1+": "+typ._2+" = _\n")
      }
      mon.append("\t\t\tvar prob = "+probability+"\n")
      mon.append("\t\t}\n")
    case SendStatement(_, responseCode, statementID, types, _, _) =>
      mon.append("\t\tobject "+statementID+" {\n")
      for(typ <- types){
        if(typ._2!=null) mon.append("\t\t\tvar "+typ._1+": "+typ._2+" = _\n")
      }
      mon.append("\t\t\tvar freq = 0\n")
      mon.append("\t\t}\n")
    case SendChoiceStatement(label, _) =>
      mon.append("\t\tobject "+label+" {\n")
      mon.append("\t\t\tvar freq = 0\n")
      mon.append("\t\t}\n")
}

  def endInit(sessionTypeName: String): Unit = {
    mon.append("\t}\n")
    mon.append("\tabstract class DriverException(choice: Any, message: String) extends Exception {\n\t\tdef getInfo: (Any, String) = {\n\t\t\t(choice, message)\n\t\t}\n\t}\n")
    mon.append("\tclass InvalidMessageException(choice: Any, message: String) extends DriverException(choice, message)\n\tclass AssertionViolationException(choice: Any, message: String) extends DriverException(choice, message)\n\tclass InvalidTestException(choice: Any, message: String) extends DriverException(choice, message)\n")
    mon.append("\tvar InvalidMessageExceptions:collection.mutable.Map[Any, List[(InvalidMessageException, String, Int)]] = collection.mutable.Map()\n\tvar AssertionViolationExceptions:collection.mutable.Map[Any, List[(AssertionViolationException, String, Int)]] = collection.mutable.Map()\n")
    mon.append("\tvar r = new scala.util.Random(seed)\n\tvar passed = true\n\tvar pass = 0.0\n\tvar fail = 0.0\n\tval animationChars = List[Char]('|', '/', '-', '\\\\')\n")
    mon.append("\tval backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()\n")
    for(api <- sessionTypeInterpreter.getApis()){
      mon.append(f"\tval ${Character.toLowerCase(api.charAt(0)) + api.substring(1)} = $api.apply()\n")
    }
    mon.append("\toverride def run(): Unit = {\n\t\treport(\"[DRIVER] Starting tests...\\n\")\n")
    mon.append("\t\tval l: csvLogger = new csvLogger(f\"${System.getProperty(\"user.dir\")}/logs/"+sessionTypeName+"_tests.csv\")\n")
    mon.append("\t\tl.log(\"rep seed passed seq_length sequence\")\n")
    mon.append("\t\treport(\"\\n\")\n")
    mon.append("\t\tfor(rep <- 1 to repetitions){\n")
    mon.append("\t\t\tsetup\n")
    mon.append("\t\t\treport(\"\\u001b[1A\\u001b[2K\")\n")
    mon.append("\t\t\treport(f\"[DRIVER] Running test: $rep ${animationChars(rep % 4)}\\n\")\n")
    mon.append("\t\t\tval sequence = new StringBuilder()\n")
    mon.append("\t\t\tval curlSequence = new StringBuilder()\n")
    mon.append("\t\t\ttry {\n")
    mon.append("\t\t\t\t$firstCall\n")
    mon.append("\t\t\t\tpass+=1\n")
    mon.append("\t\t\t} catch {\n")
    mon.append("\t\t\t\tcase e: InvalidMessageException =>\n\t\t\t\t\tsequence.append(e.getInfo._2)\n\t\t\t\t\tval e_s_r: (InvalidMessageException, String, Int) = InvalidMessageExceptions.getOrElse(e.getInfo._1, List():+(e, sequence.toString(), rep)).minBy(e_s => e_s._2.length)\n\t\t\t\t\tInvalidMessageExceptions.update(e.getInfo._1, InvalidMessageExceptions.getOrElse(e.getInfo._1, List()):+(e, sequence.toString(), rep))\n")
    mon.append("\t\t\t\t\tif(minimise) {\n\t\t\t\t\t\tif(e_s_r._2.length > sequence.length() || e_s_r._2.length == sequence.length() && e_s_r._1==e) {\n\t\t\t\t\t\t\tnew File(f\"${System.getProperty(\"user.dir\")}/debug/"+sessionTypeName+"/invalid_msg_${e_s_r._3}.sh\").delete()\n")
    mon.append("\t\t\t\t\t\t\tval debugLog: logger = new logger(f\"${System.getProperty(\"user.dir\")}/debug/"+sessionTypeName+"/invalid_msg_$rep.sh\")\n\t\t\t\t\t\t\tdebugLog.log(curlSequence.toString())\n\t\t\t\t\t\t}\n\t\t\t\t\t} else {\n\t\t\t\t\t\tval debugLog: logger = new logger(f\"${System.getProperty(\"user.dir\")}/debug/S_pc/invalid_msg_$rep.sh\")\n\t\t\t\t\t\tdebugLog.log(curlSequence.toString())\n\t\t\t\t\t}\n\t\t\t\t\tpassed=false; fail+=1\n\t\t\t\t\tteardown\n")
    mon.append("\t\t\t\tcase e: AssertionViolationException =>\n\t\t\t\t\tsequence.append(e.getInfo._2)\n\t\t\t\t\tval e_s_r: (AssertionViolationException, String, Int) = AssertionViolationExceptions.getOrElse(e.getInfo._1, List():+(e, sequence.toString(), rep)).minBy(e_s => e_s._2.length)\n\t\t\t\t\tAssertionViolationExceptions.update(e.getInfo._1, AssertionViolationExceptions.getOrElse(e.getInfo._1, List()):+(e, sequence.toString(), rep))\n")
    mon.append("\t\t\t\t\tif(minimise) {\n\t\t\t\t\t\tif(e_s_r._2.length > sequence.length() || e_s_r._2.length == sequence.length() && e_s_r._1==e) {\n\t\t\t\t\t\t\tnew File(f\"${System.getProperty(\"user.dir\")}/debug/"+sessionTypeName+"/assertion_violation_${e_s_r._3}.sh\").delete()\n")
    mon.append("\t\t\t\t\t\t\tval debugLog: logger = new logger(f\"${System.getProperty(\"user.dir\")}/debug/"+sessionTypeName+"/assertion_violation_$rep.sh\")\n\t\t\t\t\t\t\tdebugLog.log(curlSequence.toString())\n\t\t\t\t\t\t}\n\t\t\t\t\t} else {\n\t\t\t\t\t\tval debugLog: logger = new logger(f\"${System.getProperty(\"user.dir\")}/debug/S_pc/assertion_violation_$rep.sh\")\n\t\t\t\t\t\tdebugLog.log(curlSequence.toString())\n\t\t\t\t\t}\n\t\t\t\t\tpassed=false; fail+=1\n\t\t\t\t\tteardown\n")
    mon.append("\t\t\t\tcase e: InvalidTestException =>\n\t\t\t\t\tsequence.append(e)\n\t\t\t\t\tl.log(f\"$rep $seed $passed ${sequence.toString().split('.').length} ${sequence.insert(0,\"\\\"\").append(\"\\\"\").toString()}\")\n\t\t\t\t\tteardown\n\t\t\t\t\tthrow new Exception(f\"Error in test (choice: ${e.getInfo._1}) ${e.getInfo._2}\")\n")
    mon.append("\t\t\t\tcase e: Throwable =>\n")
    mon.append("\t\t\t\t\tsequence.append(e.getMessage)\n")
    mon.append("\t\t\t\t\tl.log(f\"$rep $seed $passed ${sequence.toString().split('.').length} ${sequence.insert(0,\"\\\"\").append(\"\\\"\").toString()}\")\n")
    mon.append("\t\t\t\t\tteardown\n\t\t\t\t\tthrow new Exception(\"Error in test\")\n\t\t\t}\n")
    mon.append("\t\t\tl.log(f\"$rep $seed $passed ${sequence.toString().split('.').length} ${sequence.insert(0,\"\\\"\").append(\"\\\"\").toString()}\")\n")
    mon.append("\t\t\tpassed=true; seed=r.nextInt(); r=new scala.util.Random(seed)\n\t\t}\n")
    mon.append("\t\treportSummary()\n\t}\n")
  }

  /**
   * Generates the code for the external choice type consisting of a single branch: ?Label(payload)[assertion].S
   *
   * @param statement The current statement.
   * @param nextStatement The next statement in the session type.
   * @param isUnique A boolean indicating whether the label of the current statement is unique.
   */
  def handleSend(statement: SendStatement, nextStatement: Statement, isUnique: Boolean): Unit = {
    var reference = statement.label
    if(!isUnique){
      reference = statement.statementID
    }
    if(first){
      mon.replace(mon.indexOf("$firstCall"), mon.indexOf("$firstCall")+10, "receive"+statement.statementID+"(0, sequence, curlSequence).result")
      first = false
    }

    if(statement.types.isEmpty) {
      mon.append("\tdef receive"+statement.statementID+s"(responseCode: Int, body: Null, count: Int, sequence: StringBuilder, curlSequence: StringBuilder): TailRec[Unit] = {\n")
    } else {
      mon.append("\tdef receive"+statement.statementID+s"(responseCode: Int, ${statement.types.head._1}: ${statement.types.head._2}, count: Int, sequence: StringBuilder, curlSequence: StringBuilder): TailRec[Unit] = {\n")
    }

    mon.append("\t\tresponseCode match {\n")
    mon.append("\t\t\tcase code @ "+statement.responseCode+" =>\n")

    var body = ""
    if(statement.types.nonEmpty) body = "$"+statement.types.head._1
    mon.append("\t\t\tsequence.append(f\"?$code("+body+").\")\n")
    if(statement.condition != null){
      handleCondition(statement.condition, statement.statementID)
      handleSendNextCase(statement, nextStatement)
      mon.append("\t\t\t\t} else {\n")
      mon.append("\t\t\t\tpassed=false; throw new AssertionViolationException(\""+reference+"\", \"Violation in Assertion: "+statement.condition+"\");  }\n")
    } else {
      handleSendNextCase(statement, nextStatement)
    }
    mon.append("\t\t\tcase code @ _ => sequence.append(f\"?$code("+body+").\"); passed=false; throw new InvalidMessageException(\""+reference+"\", f\"Unknown message: $code\"); \n")
    mon.append("\t\t}\n\t}\n")
  }

  /**
   * Generates the code for the monitor to call the method representing the next statement in the session type after an external choice type.
   *
   * @param currentStatement The current statement.
   * @param nextStatement The next statement in the session type.
   */
  @scala.annotation.tailrec
  private def handleSendNextCase(currentStatement: SendStatement, nextStatement: Statement): Unit ={
    nextStatement match {
      case sendStatement: SendStatement =>
        updateFrequency(currentStatement.statementID, currentStatement.condition==null)
        storeReceivedValue(currentStatement.types, currentStatement.condition==null, currentStatement.statementID)
        var body = ""
        if(currentStatement.types.nonEmpty) body = "$"+currentStatement.types.head._1
        if(currentStatement.condition==null) {
          if(sendStatement.types.isEmpty) {
            mon.append("\t\t\t\tif (count < max) {\n\t\t\t\t\treceive"+sendStatement.statementID+"(response.code.toString.toInt, null, count+1, sequence, curlSequence)\n\t\t\t\t} else { tailcall(receive"+sendStatement.statementID+"(response.code.toString.toInt, null, 0, sequence, curlSequence)) }\n")
          } else {
            mon.append("\t\t\t\ttry {\n\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\treceive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}], count+1, sequence, curlSequence)\n\t\t\t\t\t} else { tailcall(receive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}].value.asInstanceOf[${sendStatement.types.head._2}], 0, sequence, curlSequence)) }\n")
            mon.append("\t\t\t\t} catch {\n\t\t\t\t\tcase e: java.lang.ClassCastException =>\n\t\t\t\t\t\tthrow new InvalidMessageException(\""+sendStatement.statementID+"\", f\"Unexpected response: $response\")\n\t\t\t\t}\n")
          }
        } else {
          if(sendStatement.types.isEmpty) {
            mon.append("\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\treceive"+sendStatement.statementID+"(response.code.toString.toInt, null, count+1, sequence, curlSequence)\n\t\t\t\t\t} else { tailcall(receive"+sendStatement.statementID+"(response.code.toString.toInt, null, 0, sequence, curlSequence)) }\n")
          } else {
            mon.append("\t\t\t\t\ttry{\n\t\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\t\treceive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}].value.asInstanceOf[${sendStatement.types.head._2}], count+1, sequence, curlSequence)\n\t\t\t\t\t\t} else { tailcall(receive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}].value.asInstanceOf[${sendStatement.types.head._2}], 0, sequence, curlSequence)) }\n")
            mon.append("\t\t\t\t\t} catch {\n\t\t\t\t\t\tcase e: java.lang.ClassCastException =>\n\t\t\t\t\t\t\tthrow new InvalidMessageException(\""+sendStatement.statementID+"\", f\"Unexpected response: $response\")\n\t\t\t\t\t}\n")
          }
        }

      case sendChoiceStatement: SendChoiceStatement =>
        updateFrequency(currentStatement.statementID, currentStatement.condition==null)
        storeReceivedValue(currentStatement.types, currentStatement.condition==null, currentStatement.statementID)
        var body = ""
        if(currentStatement.types.nonEmpty) body = "$"+currentStatement.types.head._1
        if(currentStatement.condition==null) {
          mon.append("\t\t\t\tif (count < max) {\n\t\t\t\t\treceive"+sendChoiceStatement.label+"(count+1, sequence, curlSequence)\n\t\t\t\t} else { tailcall(receive"+sendChoiceStatement.label+"(0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\treceive"+sendChoiceStatement.label+"(count+1, sequence, curlSequence)\n\t\t\t\t\t} else { tailcall(receive"+sendChoiceStatement.label+"(0, sequence, curlSequence)) }\n")
        }

      case receiveStatement: ReceiveStatement =>
        updateFrequency(currentStatement.statementID, currentStatement.condition==null)
        storeReceivedValue(currentStatement.types, currentStatement.condition==null, currentStatement.statementID)
        var body = ""
        if(currentStatement.types.nonEmpty) body = "$"+currentStatement.types.head._1
        if(currentStatement.condition==null) {
          mon.append("\t\t\t\tif (count < max) {\n\t\t\t\t\tsend" + receiveStatement.statementID + "(count+1, sequence, curlSequence)\n\t\t\t\t} else { tailcall(send" + receiveStatement.statementID + "(0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\tsend" + receiveStatement.statementID + "(count+1, sequence, curlSequence)\n\t\t\t\t\t} else { tailcall(send" + receiveStatement.statementID + "(0, sequence, curlSequence)) }\n")
        }

      case receiveChoiceStatement: ReceiveChoiceStatement =>
        updateFrequency(currentStatement.statementID, currentStatement.condition==null)
        storeReceivedValue(currentStatement.types, currentStatement.condition==null, currentStatement.statementID)
        var body = ""
        if(currentStatement.types.nonEmpty) body = "$"+currentStatement.types.head._1
        if(currentStatement.condition==null) {
          mon.append("\t\t\t\tif (count < max) {\n\t\t\t\t\tsend"+receiveChoiceStatement.label+"(count+1, sequence, curlSequence)\n\t\t\t\t} else { tailcall(send"+receiveChoiceStatement.label+"(0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\t\t\tif (count < max) {\n\t\t\t\t\t\tsend"+receiveChoiceStatement.label+"(count+1, sequence, curlSequence)\n\t\t\t\t\t} else { tailcall(send"+receiveChoiceStatement.label+"(0, sequence, curlSequence)) }\n")
        }

      case recursiveVar: RecursiveVar =>
        handleSendNextCase(currentStatement, sessionTypeInterpreter.getRecursiveVarScope(recursiveVar).recVariables(recursiveVar.name))

      case recursiveStatement: RecursiveStatement =>
        handleSendNextCase(currentStatement, recursiveStatement.body)

      case _ =>
        updateFrequency(currentStatement.statementID, currentStatement.condition==null)
        var body = ""
        if(currentStatement.types.nonEmpty) body = "$"+currentStatement.types.head._1
        if(currentStatement.condition==null) {
          mon.append("\t\t\t\tdone()\n")
        } else {
          mon.append("\t\t\t\t\tdone()\n")
        }
    }
  }

  /**
   * Generates the code for the internal choice type consisting of a single branch: !Label(payload)[assertion].S
   *
   * @param statement The current statement.
   * @param nextStatement The next statement in the session type.
   * @param isUnique A boolean indicating whether the label of the current statement is unique.
   */
  def handleReceive(statement: ReceiveStatement, nextStatement: Statement, isUnique: Boolean): Unit = {
    var reference = statement.label
    if(!isUnique){
      reference = statement.statementID
    }
    if(first) {
      mon.replace(mon.indexOf("$firstCall"), mon.indexOf("$firstCall")+10, "send" + statement.statementID + "(0, sequence, curlSequence).result")
      first = false
    }

    mon.append("\tdef send" + statement.statementID + "(count: Int, sequence: StringBuilder, curlSequence: StringBuilder): TailRec[Unit] = {\n")
    addParameters(statement.types, false)

    if(statement.condition != null){
      handleSendCondition(statement.condition, statement.statementID, statement.types)
      handleReceiveNextCase(statement, isUnique, nextStatement)
    } else {
      handleReceiveNextCase(statement, isUnique, nextStatement)
    }
    mon.append("\t}\n")
  }

  /**
   * Generates the code for the monitor to call the method representing the next statement in the session type after an internal choice type.
   *
   * @param currentStatement The current statement.
   * @param isUnique A boolean indicating whether the label of the current statement is unique.
   * @param nextStatement The next statement in the session type.
   */
  @scala.annotation.tailrec
  private def handleReceiveNextCase(currentStatement: ReceiveStatement, isUnique: Boolean, nextStatement: Statement): Unit ={
    nextStatement match {
      case sendStatement: SendStatement =>

        handleReceiveCases(currentStatement, isUnique, false)
        storeSentValue(sessionTypeInterpreter.pruneSendingTypes(currentStatement.types), currentStatement.condition==null, currentStatement.statementID)
        if(currentStatement.condition==null) {
          if(sendStatement.types.isEmpty) {
            mon.append("\t\tif (count < max) {\n\t\t\treceive" + sendStatement.statementID + "(response.code.toString.toInt, null, count+1, sequence, curlSequence)\n\t\t} else { tailcall(receive"+sendStatement.statementID+"(response.code.toString.toInt, null, 0, sequence, curlSequence)) }\n")
          } else {
            mon.append("\t\ttry {\n\t\t\tif (count < max) {\n\t\t\treceive" + sendStatement.statementID + s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}], count+1, sequence, curlSequence)\n\t\t} else { tailcall(receive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}], 0, sequence, curlSequence)) }\n")
            mon.append("\t\t} catch {\n\t\t\tcase e: java.lang.ClassCastException =>\n\t\t\t\tthrow new InvalidMessageException(\""+sendStatement.statementID+"\", f\"Unexpected response: $response\")\n\t\t\t}\n")
          }
        } else {
          if(sendStatement.types.isEmpty) {
            mon.append("\t\t\tif (count < max) {\n\t\t\t\treceive" + sendStatement.statementID + "(response.code.toString.toInt, null, count+1, sequence, curlSequence)\n\t\t\t} else { tailcall(receive"+sendStatement.statementID+"(response.code.toString.toInt, null, 0, sequence, curlSequence)) }\n")
          } else {
            mon.append("\t\t\ttry {\n\t\t\t\tif (count < max) {\n\t\t\t\t\treceive" + sendStatement.statementID + s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}], count+1, sequence, curlSequence)\n\t\t\t} else { tailcall(receive"+sendStatement.statementID+s"(response.code.toString.toInt, if(response.body.isInstanceOf[Right[Any, Any]]) response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}] else response.body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[${sendStatement.types.head._2}], 0, sequence, curlSequence)) }\n")
            mon.append("\t\t\t} catch {\n\t\t\t\tcase e: java.lang.ClassCastException =>\n\t\t\t\t\tthrow new InvalidMessageException(\""+sendStatement.statementID+"\", f\"Unexpected response: $response\")\n\t\t\t}\n")
          }
        }

      case sendChoiceStatement: SendChoiceStatement =>
        handleReceiveCases(currentStatement, isUnique, false)
        storeSentValue(sessionTypeInterpreter.pruneSendingTypes(currentStatement.types), currentStatement.condition==null, currentStatement.statementID)
        if(currentStatement.condition==null) {
          mon.append("\t\tif (count < max) {\n\t\t\treceive" + sendChoiceStatement.label + "(response.code.toString.toInt, response.body, count+1, sequence, curlSequence)\n\t\t} else { tailcall(receive"+sendChoiceStatement.label+"(response.code.toString.toInt, response.body, 0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\tif (count < max) {\n\t\t\t\treceive" + sendChoiceStatement.label + "(response.code.toString.toInt, response.body, count+1, sequence, curlSequence)\n\t\t\t} else { tailcall(receive" + sendChoiceStatement.label + "(response.code.toString.toInt, response.body, 0, sequence, curlSequence)) }\n")
        }

      case receiveStatement: ReceiveStatement =>
        handleReceiveCases(currentStatement, isUnique, false)
        storeSentValue(sessionTypeInterpreter.pruneSendingTypes(currentStatement.types), currentStatement.condition==null, currentStatement.statementID)
        if(currentStatement.condition==null) {
          mon.append("\t\t\tif (count < max) {\n\t\t\tsend" + receiveStatement.statementID + "(count+1, sequence, curlSequence)\n\t\t} else { tailcall(send"+ receiveStatement.statementID +"(0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\t\tif (count < max) {\n\t\t\t\tsend" + receiveStatement.statementID + "(count+1, sequence, curlSequence)\n\t\t\t} else { tailcall(send" + receiveStatement.statementID + "(0, sequence, curlSequence)) }\n")
        }

      case receiveChoiceStatement: ReceiveChoiceStatement =>
        handleReceiveCases(currentStatement, isUnique, false)
        storeSentValue(sessionTypeInterpreter.pruneSendingTypes(currentStatement.types), currentStatement.condition==null, currentStatement.statementID)
        if(currentStatement.condition==null) {
          mon.append("\t\t\tif (count < max) {\n\t\t\tsend" + receiveChoiceStatement.label + "(count+1, sequence, curlSequence)\n\t\t} else { tailcall(send"+ receiveChoiceStatement.label +"(0, sequence, curlSequence)) }\n")
        } else {
          mon.append("\t\t\t\tif (count < max) {\n\t\t\t\tsend" + receiveChoiceStatement.label + "(count+1, sequence, curlSequence)\n\t\t\t} else { tailcall(send" + receiveChoiceStatement.label + "(0, sequence, curlSequence)) }\n")
        }

      case recursiveVar: RecursiveVar =>
        handleReceiveNextCase(currentStatement, isUnique, sessionTypeInterpreter.getRecursiveVarScope(recursiveVar).recVariables(recursiveVar.name))

      case recursiveStatement: RecursiveStatement =>
        handleReceiveNextCase(currentStatement, isUnique, recursiveStatement.body)

      case _: End =>
        handleReceiveCases(currentStatement, isUnique, true)
        if(currentStatement.condition==null) {
          mon.append("\t\tdone()\n")
        } else {
          mon.append("\t\t\tdone()\n")
        }
    }
  }

  /**
   * Generates the code for forwarding a message over an lchannels channel.
   *
   * @param statement The current statement.
   * @param isUnique A boolean indicating whether the label of the current statement is unique.
   */
  private def handleReceiveCases(statement: ReceiveStatement, isUnique: Boolean, isEnd: Boolean): Unit = {
    var reference = statement.statementID
    val tmpStringParam = new StringBuilder
    val tmpParam = new StringBuilder
    if(isUnique){
      reference = statement.label
    }
    if(statement.condition!=null) {
      mon.append(f"\t\t\tval request = ${Character.toLowerCase(statement.api.charAt(0)) + statement.api.substring(1)}.${statement.label}(")
    } else {
      mon.append(f"\t\tval request = ${Character.toLowerCase(statement.api.charAt(0)) + statement.api.substring(1)}.${statement.label}(")
    }
    for ((k, v) <- statement.types) {
      if(v==null){
        val varScope = sessionTypeInterpreter.searchIdent(statement.statementID, k)
//        if(statement.statementID == varScope){
//        } else {
        if((k,v) == statement.types.last) {
          tmpParam.append("info."+varScope+"."+k)
          tmpStringParam.append("${info."+varScope+"."+k+"}")
        } else {
          tmpParam.append("info."+varScope+"."+k+", ")
          tmpStringParam.append("${info."+varScope+"."+k+"}, ")
        }
//        }
      } else {
        if ((k, v) == statement.types.last) {
          tmpParam.append(k)
          tmpStringParam.append("$"+k)
        } else {
          tmpParam.append(k + ", ")
          tmpStringParam.append("$"+k+", ")
        }
      }
    }
    mon.append(tmpParam+")\n")
    var tab = ""
    mon.append(tab+"val response = request.send(backend)\n")
    if(statement.condition==null) tab = "\t\t" else tab = "\t\t\t"
    if(tmpParam.isEmpty)mon.append(tab+"sequence.append(f\"!"+reference+"().\")\n")
    else mon.append(tab+"sequence.append(f\"!"+reference+"("+tmpStringParam+").\")\n")
    mon.append(tab+"curlSequence.append(request.toCurl+\"\\n\\n\")\n")
  }

  /**
   * Generates the code for the external choice type: &{?Label(payload)[assertion].S, ...}
   *
   * @param statement The current statement.
   */
  def handleSendChoice(statement: SendChoiceStatement): Unit ={
    if(first) {
      mon.replace(mon.indexOf("$firstCall"), mon.indexOf("$firstCall")+10, "receive" + statement.label + "(0, sequence, curlSequence).result")
      first = false
    }

    mon.append("\tdef receive" + statement.label + "(responseCode: Int, body: Any, count: Int, sequence: StringBuilder, curlSequence: StringBuilder): TailRec[Unit] = {\n")
    mon.append("\t\tresponseCode match {\n")

    for (choice <- statement.choices){
      var reference = choice.asInstanceOf[SendStatement].label
      if(!sessionTypeInterpreter.getScope(choice).isUnique){
        reference = choice.asInstanceOf[SendStatement].statementID
      }
      mon.append("\t\t\tcase code @ "+choice.asInstanceOf[SendStatement].responseCode+" =>\n")
//      addEmptyParameters(choice.asInstanceOf[SendStatement].types)
//      mon.append(") =>\n")
      if(choice.asInstanceOf[SendStatement].types.nonEmpty) {
        mon.append("\t\t\t\tval " + Character.toLowerCase(choice.asInstanceOf[SendStatement].types.head._1.charAt(0)) + choice.asInstanceOf[SendStatement].types.head._1.substring(1) + " = ")
        mon.append(" if(body.isInstanceOf[Left[Any, Any]]){body.asInstanceOf[Left[Any, Any]].value.asInstanceOf[" + choice.asInstanceOf[SendStatement].types.head._2.capitalize + "]} else{ body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[" + choice.asInstanceOf[SendStatement].types.head._2.capitalize + "] } \n")
      }
      var body = ""
      if(choice.asInstanceOf[SendStatement].types.nonEmpty) body = "$"+choice.asInstanceOf[SendStatement].types.head._1
      mon.append("\t\t\tsequence.append(f\"?$code("+body+").\")\n")
      if(choice.asInstanceOf[SendStatement].condition != null){
        handleCondition(choice.asInstanceOf[SendStatement].condition, choice.asInstanceOf[SendStatement].statementID)
        handleSendNextCase(choice.asInstanceOf[SendStatement], choice.asInstanceOf[SendStatement].continuation)
        mon.append("\t\t\t\t} else {\n")
        mon.append("\t\t\t\tpassed=false; throw new AssertionViolationException(\""+reference+"\", \"Violation in Assertion: "+choice.asInstanceOf[SendStatement].condition+"\");  }\n")
      } else {
        handleSendNextCase(choice.asInstanceOf[SendStatement], choice.asInstanceOf[SendStatement].continuation)
      }
    }
    mon.append("\t\t\tcase code @ _ => sequence.append(f\"?$code().\"); passed=false; throw new InvalidMessageException(\""+statement.label+"\", f\"Unknown message: $code\"); \n")
    mon.append("\t\t}\n\t}\n")
  }

  /**
   * Generates the code for the internal choice type: +{!Label(payload)[assertion].S, ...}
   *
   * @param statement The current statment.
   */
  def handleReceiveChoice(statement: ReceiveChoiceStatement): Unit = {
    if(first) {
      mon.replace(mon.indexOf("$firstCall"), mon.indexOf("$firstCall")+10, "send" + statement.label + "(0, sequence, curlSequence).result")
      first = false
    }

    mon.append("\tdef send" + statement.label + "(count: Int, sequence: StringBuilder, curlSequence: StringBuilder): TailRec[Unit] = {\n")
    mon.append("\t\tval rand = r.nextDouble()\n")
    var firstChoice = true
    var aggProb = 0.0
    for (choice <- statement.choices){
      if(firstChoice){
        mon.append("\t\tif (rand <= info."+choice.asInstanceOf[ReceiveStatement].statementID+".prob){\n")
        firstChoice=false
      } else {
        mon.append("\t\t} else if (rand <= info."+choice.asInstanceOf[ReceiveStatement].statementID+".prob+"+aggProb+"){\n")
      }
      var reference = choice.asInstanceOf[ReceiveStatement].label
      if(!sessionTypeInterpreter.getScope(choice).isUnique){
        reference = choice.asInstanceOf[ReceiveStatement].statementID
      }
      addParameters(choice.asInstanceOf[ReceiveStatement].types, true)
      if(choice.asInstanceOf[ReceiveStatement].condition != null){
        handleSendCondition(choice.asInstanceOf[ReceiveStatement].condition, choice.asInstanceOf[ReceiveStatement].statementID, choice.asInstanceOf[ReceiveStatement].types)
        handleReceiveNextCase(choice.asInstanceOf[ReceiveStatement], sessionTypeInterpreter.getScope(choice).isUnique, choice.asInstanceOf[ReceiveStatement].continuation)
      } else {
        handleReceiveNextCase(choice.asInstanceOf[ReceiveStatement], sessionTypeInterpreter.getScope(choice).isUnique, choice.asInstanceOf[ReceiveStatement].continuation)
      }
      aggProb+=choice.asInstanceOf[ReceiveStatement].probability
    }
    mon.append("\t\t} else { throw new Exception(\"[DRIVER] Error in test\") }\n")
    mon.append("\t}\n")
  }

  /**
   * Generates the parameters for the statements depending on the payload size.
   *
   * @param types A mapping from identifiers to: (type, (generator function, list of params))
   */
  private def addParameters(types: Map[String, (String, (String, List[String]))], isChoice: Boolean): Unit ={
    for (typ <- types) {
      typ._2 match {
        case ("String", null) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = r.alphanumeric.take(10).mkString\n")
          else mon.append("\t\tvar "+typ._1+" = r.alphanumeric.take(10).mkString\n")
        case ("String", (fun,param)) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
          else mon.append("\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
        case ("Int", null) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = r.nextInt()\n")
          else mon.append("\t\tvar "+typ._1+" = r.nextInt()\n")
        case ("Int", (fun,param)) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
          else mon.append("\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
        case ("Boolean", null) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = r.nextBoolean()\n")
          else mon.append("\t\tvar "+typ._1+" = r.nextBoolean()\n")
        case (customType, (fun,param)) =>
          if(isChoice) mon.append("\t\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
          else mon.append("\t\tvar "+typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r)\n")
        case _ =>
      }
    }
  }

  private def regenerateParameters(types: Map[String, (String, (String, List[String]))]): Unit ={
    for (typ <- types) {
      typ._2 match {
        case ("String", null) =>
          mon.append(typ._1+" = r.alphanumeric.take(r.nextInt().abs).mkString; ")
        case ("String", (fun,param)) =>
          mon.append(typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r); ")
        case ("Int", null) =>
          mon.append(typ._1+" = r.nextInt(); ")
        case ("Int", (fun,param)) =>
          mon.append(typ._1+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r); ")
        case ("Boolean", null) =>
          mon.append(typ._1+" = r.nextBoolean(); ")
        case (customType, (fun,param)) =>
          mon.append(typ._1+": "+customType+" = util."+fun+"("+param.map(p => "\""+p+"\",").mkString+"r); ")
        case _ =>
      }
    }
  }

  private def addEmptyParameters(types: Map[String, String]): Unit ={
    for (typ <- types) {
      if(typ == types.last){
        mon.append("_")
      } else {
        mon.append("_, ")
      }
    }
  }

  /**
   * Generates the code for storing a value in the respective identifier object within the monitor.
   *
   * @param types A mapping from identifiers to their type.
   * @param checkCondition A boolean indicating whether current statement has a condition.
   * @param curStatementScope The label of the current statement used to retrieve identifier
   *                          information from the interpreter.
   */
  private def storeSentValue(types: Map[String, String], checkCondition: Boolean, curStatementScope: String): Unit = {
    for((name, _) <- types) {
      val (varScope, (global, _)) = sessionTypeInterpreter.getVarInfo(name, curStatementScope)
      if(global && varScope==curStatementScope) {
        if(checkCondition){
          mon.append("\t\tinfo."+varScope+"."+name+" = "+name+"\n")
        } else {
          mon.append("\t\t\tinfo."+varScope+"."+name+" = "+name+"\n")
        }
      }
    }
  }

  private def storeReceivedValue(types: Map[String, String], checkCondition: Boolean, curStatementScope: String): Unit = {
    for((name, _) <- types) {
      val (varScope, (global, _)) = sessionTypeInterpreter.getVarInfo(name, curStatementScope)
      if(global) {
        if(checkCondition){
          mon.append("\t\t\t\tinfo."+varScope+"."+name+" = "+name+"\n")
        } else {
          mon.append("\t\t\t\t\tinfo."+varScope+"."+name+" = "+name+"\n")
        }
      }
    }
  }

  /**
   * Generates the code for conditions by identifying identifiers and changing them for the
   * respective variable within the monitor.
   *
   * @param condition Condition in String format.
   * @param label The label of the current statment.
   */
  private def handleCondition(condition: String, label: String): Unit ={
    mon.append("\t\t\t\tif(")
    var stringCondition = condition
    val identifierNames = sessionTypeInterpreter.getIdentifiersFromCondition(condition)
    for(identName <- identifierNames){
      val varScope = sessionTypeInterpreter.searchIdent(label, identName)
      val identPattern = ("\\b"+identName+"\\b").r
      if(label == varScope){
        stringCondition = identPattern.replaceAllIn(stringCondition, identName)
      } else {
        stringCondition = identPattern.replaceAllIn(stringCondition, "info."+varScope+"."+identName)
      }
    }
    mon.append(stringCondition+"){\n")
  }

  private def handleSendCondition(condition: String, label: String, varsInChoice: Map[String, (String, (String, List[String]))]): Unit ={
    mon.append("\t\t\t\twhile(!(")
    var stringCondition = condition
    val identifierNames = sessionTypeInterpreter.getIdentifiersFromCondition(condition)
    for(identName <- identifierNames){
      val varScope = sessionTypeInterpreter.searchIdent(label, identName)
      val identPattern = ("\\b"+identName+"\\b").r
      if(label != varScope){
        stringCondition = identPattern.replaceAllIn(stringCondition, "info."+varScope+"."+identName)
      }
    }
    mon.append(stringCondition+")){ ")
    regenerateParameters(varsInChoice)
    mon.append("}\n")
  }

  private def updateFrequency(statementID: String, checkCondition: Boolean): Unit ={
    if(checkCondition) mon.append("\t\t\t\tinfo."+statementID+".freq+=1\n")
    else mon.append("\t\t\t\t\tinfo."+statementID+".freq+=1\n")
  }

  def defineReportSummary(): Unit ={
    mon.append("\tdef reportSummary(): Unit = {\n")
    mon.append("\t\treport(\"[DRIVER] TESTS SUMMARY\\n\")\n\t\treport(\"Number of tests: \"+repetitions+\"\\n\")\n\t\treport(\"Passed (%): \"+(pass/repetitions.toDouble)*100+\"\\n\")\n\t\treport(\"Failed (%): \"+(fail/repetitions.toDouble)*100+\"\\n\")\n")
    mon.append("\t\treport(\"Invalid Message violations: \"+InvalidMessageExceptions.size+\"\\n\")\n\t\treport(\"Assertion violations: \"+AssertionViolationExceptions.size+\"\\n\")\n")
  }

  def handelSendChoiceInfo(sendChoiceStatement: SendChoiceStatement): Unit ={
    mon.append("\t\treport(\"+{\"")
    for(choice <- sendChoiceStatement.choices){
      mon.append("+\"\\t!"+choice.asInstanceOf[SendStatement].label+" \"+info."+choice.asInstanceOf[SendStatement].statementID+".freq+\"\\n\"")
    }
    mon.append("+\"}\")\n")
  }

  def endDefineReportSummary(): Unit ={
    mon.append("\t}\n")
  }

  def end(): Unit = {
    mon.append("}")
  }
}
