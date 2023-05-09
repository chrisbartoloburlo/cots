package examples.languagetool

import examples.languagetool.model.LanguagesGet200ResponseInner
import examples.openverse.model._

import scala.util.Random

object util {
  def constant(string: String, random: Random): String = {
    string
  }
  def constantO(string: String, random: Random): Option[String] = {
    Some(string)
  }
  var languages: Seq[LanguagesGet200ResponseInner] = Seq()
  def setLanguages(languages: Seq[LanguagesGet200ResponseInner]): Boolean = {
    this.languages=languages
    true
  }
  def getLanguage(random: Random): String = {
//    languages.map(_.code)(random.between(0, languages.size-1))
    List("fr", "en-US")(random.between(0, 1))
  }
  val text: Seq[String] = Seq("I walk to the store and I bought milk).", "I will eat fish for dinner and drank.milk", "we all eat the fish and then made dessert.", "!what said the elephanT")
  def genRandomText(random: Random): Option[String] = {
//    Some(random.alphanumeric.take(random.between(10,100)).mkString)
    Some(text(random.between(0, text.size-1)))
  }
  def getNone(random: Random): Option[String] = {
    None
  }
  def getBoooleanNone(random: Random): Option[Boolean] = {
    None
  }
  def genRandomData(random: Random): Option[String] = {
    Some("{\"annotation\":[\n {\"text\": \"A \"},\n {\"markup\": \"<b>\"},\n {\"text\": \"test\"},\n {\"markup\": \"</b>\"}\n]}")
  }
  def getDicts(random: Random): Option[String] = {
    Some("en-US,de")
  }
  def getMotherTongue(random: Random): Option[String] = {
    Some("en-US")
  }
  def getVariants(random: Random): Option[String] = {
    Some("en-GB")
  }
  def getAuto(random: Random): String = {
    "auto"
  }
  def getEnabled(random: Random): Option[Boolean] = {
    Some(true)
  }
  def getDisabled(random: Random): Option[String] = {
    Some("EN_A_VS_AN")
  }
  def getLevel(random: Random): Option[String] = {
    Some("picky")
  }
}
