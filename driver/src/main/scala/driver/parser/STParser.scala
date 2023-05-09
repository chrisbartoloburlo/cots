package driver.parser

import driver.model._

import scala.collection.immutable.ListMap
import scala.language.postfixOps
import scala.tools.nsc.interactive.Lexer.StringLit
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

class STParser extends StandardTokenParsers {
  lexical.reserved += ("rec", "end")
//  "String", "Int", "Boolean"

  lexical.delimiters += ("?", "!", "&", "+", "(", ")", "{", "}", ",", ":", "=", ".", "<", ">", "@", "C")

  private var sendChoiceCounter: Int = 0
  private var receiveChoiceCounter: Int = 0
  private var statementIDCounter: Int = 0

  def getAndIncrementIDCounter(): Int = {
    statementIDCounter+=1
    statementIDCounter
  }

  def sessionTypeVar: Parser[SessionType] = (ident <~ "=") ~ sessionType ^^ {
    case i ~ t =>
      new SessionType(i, t)
  }

  def sessionType: Parser[Statement] = positioned (choice | receive | send | recursive | recursiveVar | end) ^^ {a=>a}

  def choice: Parser[Statement] = positioned( receiveChoice | sendChoice ) ^^ {a=>a}

  def receive: Parser[SendStatement] = ("?" ~> responseCode) ~ ("(" ~> types <~ ")") ~ opt("<" ~> stringLit <~ ">") ~ opt("." ~> sessionType) ^^ {
    case rc ~ t ~ None ~ None =>
      SendStatement("C"+rc, rc, "C"+rc+"_"+getAndIncrementIDCounter, t, null, End())
    case rc ~ t ~ None ~ cT =>
      SendStatement("C"+rc, rc, "C"+rc+"_"+getAndIncrementIDCounter, t, null, cT.get)
    case rc ~ t ~ c ~ None =>
      SendStatement("C"+rc, rc, "C"+rc+"_"+getAndIncrementIDCounter, t, c.get, End())
    case rc ~ t ~ c ~ cT =>
      SendStatement("C"+rc, rc, "C"+rc+"_"+getAndIncrementIDCounter, t, c.get, cT.get)
  }

  def responseCode: Parser[Int] = ident ^^ {
    code =>
      if(!code.contains('C')) throw new Exception("external choices should have the form C<responseCode>")
      else code.drop(1).toInt
  }

  def receiveChoice: Parser[SendChoiceStatement] = "&" ~ "{" ~> (repsep(sessionType, ",") <~ "}") ^^ {
    cN =>
      for (s <- cN) {
        s match {
          case _: SendStatement =>
          case _ =>
            throw new Exception("& must be followed with ?")
        }
      }
      SendChoiceStatement(f"ExternalChoice${receiveChoiceCounter+=1;receiveChoiceCounter.toString}", cN)
  }

  def send: Parser[ReceiveStatement] = ("!" ~> ident ~ ("." ~> ident)) ~ ("(" ~> sendingTypes <~ ")") ~ opt("<" ~> probAndAssertion <~ ">") ~ opt("." ~> sessionType) ^^ {
    case a ~ l ~ t ~ None ~ None =>
      ReceiveStatement(a, l, l+"_"+getAndIncrementIDCounter, t, -1.0, null, End())
    case a ~ l ~ t ~ None ~ cT =>
      ReceiveStatement(a, l, l+"_"+getAndIncrementIDCounter, t, -1.0, null, cT.get)
    case a ~ l ~ t ~ pc ~ None =>
      ReceiveStatement(a, l, l+"_"+getAndIncrementIDCounter, t, pc.get._1, pc.get._2, End())
    case a ~ l ~ t ~ pc ~ cT =>
      ReceiveStatement(a, l, l+"_"+getAndIncrementIDCounter, t, pc.get._1, pc.get._2, cT.get)
  }

  var receiveChoicesSize = 1.0
  def sendChoice: Parser[ReceiveChoiceStatement] = "+" ~ "{" ~> (repsep(sessionType, ",") <~ "}") ^^ {
    cN =>
      receiveChoicesSize = cN.size.toDouble
      for (s <- cN) {
        s match {
          case rS: ReceiveStatement =>
            if(rS.probability== -1.0){
              rS.probability=1/receiveChoicesSize
            }
          case _ =>
            throw new Exception("+ must be followed with !")
        }
      }
      receiveChoicesSize = 1.0
      ReceiveChoiceStatement(f"InternalChoice${sendChoiceCounter+=1;sendChoiceCounter.toString}", cN)
  }

  def recursive: Parser[RecursiveStatement] = ("rec" ~> ident <~ ".") ~ ("(" ~> sessionType <~ ")") ^^ {
    case i ~ cT =>
      RecursiveStatement(i, cT)
  }

  def recursiveVar: Parser[RecursiveVar] = ident ~ opt("." ~> sessionType) ^^ {
    case i ~ None =>
      RecursiveVar(i, End())
    case i ~ cT =>
      RecursiveVar(i, cT.get)
  }

  def types: Parser[Map[String, String]] = repsep(typDef, ",") ^^ {
    _ toMap
  }

  def typDef: Parser[(String, String)] = ident ~ (":" ~> typ) ^^ {
    case a ~ b =>
      (a, b)
  }

  def sendingTypes: Parser[Map[String, (String, (String, List[String]))]] = repsep(sendingTypDef, ",") ^^ {
    ListMap(_:_*)
  }

  def sendingTypDef: Parser[(String, (String, (String, List[String])))] = ident ~ opt((":" ~> ident) ~ opt("(" ~> ident ~ stringLit.* <~ ")")) ^^ {
    case a ~ None =>
      (a, null)
    case a ~ b =>
      b.get match {
        case typ ~ None =>
          (a, (typ, null))
        case typ ~ gen =>
          (a, (typ, (gen.get._1, gen.get._2)))
      }
  }

  def typ: Parser[String] = ident | stringLit ^^ (t => t)

  def probAndAssertion: Parser[(Double, String)] = stringLit ^^ {
    s =>
      try {
        if (s.contains('@')) {
          (s.split('@')(0).toDouble, s.split('@')(1))
        } else {
          try {
            (s.toDouble, null)
          }
          catch {
            case _: Throwable => (1, s)
          }
        }
      } catch {
        case _: Throwable => throw new Exception("use @ symbol to separate probability from boolean assertions")
      }
  }

  def end: Parser[End] = ("" | "end") ^^ (_ => End())

  def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
    val assertionPattern = """\<(.*?)\>""".r
    phrase(p)(new lexical.Scanner(assertionPattern.replaceAllIn(in, "<\""+_.group(1).replace("\"", "\\\\\"")+"\">")))
  }
}