package examples.restcountries

//import examples.restcountries.model.{Currencies, GetAll, Languages}

import examples.restcountries.model.{CountryV1, CountryV2, CurrenciesV2, LanguagesV2}

import scala.util.Random

object util {
  var countriesV1: Seq[CountryV1] = Seq()
  def setCountriesV1(countries: Seq[CountryV1]): Boolean = {
    this.countriesV1=countries
    true
  }
  var countryV1: CountryV1 = null
  def getAlpha2code(rand: Random): String = {
    countryV1=countriesV1(rand.between(0, countriesV1.size-1))
    countryV1.alpha2Code.getOrElse("")
  }
  def getWrongAlpha2Code(rand: Random): String = {
    var alpha2codes: Seq[String] = Seq()
    for(c <- countriesV1) alpha2codes = alpha2codes :+ c.alpha2Code.getOrElse("")
    var wrongAlpha2code = Random.alphanumeric.filter(_.isLetter).take(2).mkString.toUpperCase
    while(alpha2codes.contains(wrongAlpha2code)) wrongAlpha2code = rand.alphanumeric.filter(_.isLetter).take(2).mkString.toUpperCase
    wrongAlpha2code
  }
  def getBadAlpha2Code(rand: Random): String = {
    Random.alphanumeric.filter(_.isLetter).take(5).mkString.toUpperCase
  }
  def getAlpha2codes(rand: Random): String = {
    var alpha2codes: Seq[String] = Seq()
    for(c <- countriesV1) alpha2codes = alpha2codes :+ c.alpha2Code.getOrElse("")
    rand.shuffle(alpha2codes).take(rand.between(1, alpha2codes.size)).mkString(";")
  }
  def getCallingCode(rand: Random): String = {
    countryV1=countriesV1(rand.between(0, countriesV1.size-1))
    if(countryV1.callingCodes.head.isEmpty) ""
    else countryV1.callingCodes.head.head
  }
  def getWrongCallingCode(rand: Random): String = {
    rand.nextDouble().toString
  }
  def getCapital(rand: Random): String = {
    countryV1=countriesV1(rand.between(0, countriesV1.size-1))
    countryV1.capital.getOrElse("")
  }
  def getWrongCapital(rand: Random): String = {
    var capitals: Seq[String] = Seq()
    for(c <- countriesV1) capitals = capitals :+ c.alpha2Code.getOrElse("")
    var wrongCapital = Random.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
    while(capitals.contains(wrongCapital)) wrongCapital = rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
    wrongCapital
  }
  def getName(rand: Random): String = {
    countriesV1(rand.between(0, countriesV1.size-1)).name.getOrElse("")
  }
  def getCurrency(rand: Random): String = {
    countriesV1(rand.between(0, countriesV1.size-1)).currencies.getOrElse(Seq("EUR")).head
  }
  def getBadCurrency(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongCurrency(rand: Random): String = {
    var currencies: Seq[String] = Seq()
    for(c <- countriesV1) currencies = currencies :+ c.currencies.getOrElse(Seq("EUR")).head
    var wrongCurrency = Random.alphanumeric.filter(_.isLetter).take(3).mkString.toUpperCase
    while(currencies.contains(wrongCurrency)) wrongCurrency = rand.alphanumeric.filter(_.isLetter).take(3).mkString.toUpperCase
    wrongCurrency
  }
  def getLang(rand: Random): String = {
    countryV1 = countriesV1(rand.between(0, countriesV1.size-1))
    if(countryV1.languages.get.isEmpty) ""
    else if (rand.between(0.0, 1.0)<=0.5) ""
    else countryV1.languages.head.head
  }
  def getRegion(rand: Random): String = {
    countryV1=countriesV1(rand.between(0, countriesV1.size-1))
    countryV1.region.getOrElse("").mkString
  }
  def assertEmpty(string: String): Boolean = {
    string==""
  }
  def getSubregion(rand: Random): String = {
    countryV1 = countriesV1(rand.between(0, countriesV1.size-1))
    countryV1.subregion.getOrElse("").mkString
  }


  var countriesV2: Seq[CountryV2] = Seq()
  def setCountriesV2(countries: Seq[CountryV2]): Boolean = {
    this.countriesV2=countries
    true
  }
  var countryV2: CountryV2 = null
  def getAlpha2codeV2(rand: Random): String = {
    countryV2=countriesV2(rand.between(0, countriesV2.size-1))
    countryV2.alpha2Code.getOrElse("")
  }
  def getWrongAlpha2CodeV2(rand: Random): String = {
    var alpha2codes: Seq[String] = Seq()
    for(c <- countriesV2) alpha2codes = alpha2codes :+ c.alpha2Code.getOrElse("")
    var wrongAlpha2code = rand.alphanumeric.filter(_.isLetter).take(2).mkString.toUpperCase
    while(alpha2codes.contains(wrongAlpha2code)) wrongAlpha2code = rand.alphanumeric.filter(_.isLetter).take(2).mkString.toUpperCase
    wrongAlpha2code
  }
  def getBadAlpha2CodeV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(5).mkString.toUpperCase
  }
  def getAlpha2codesV2(rand: Random): String = {
    var alpha2codes: Seq[String] = Seq()
    for(c <- countriesV2) alpha2codes = alpha2codes :+ c.alpha2Code.getOrElse("")
    rand.shuffle(alpha2codes).take(rand.between(1, alpha2codes.size)).mkString(";")
  }
  def getCallingCodeV2(rand: Random): String = {
    var callingCodes: Seq[String] = Seq()
    for(c <- countriesV2) callingCodes = callingCodes :+ c.callingCodes.head.mkString
    callingCodes(rand.between(0, callingCodes.size-1))
  }
  def getWrongCallingCodeV2(rand: Random): String = {
    rand.nextDouble().toString
  }
  def getCapitalV2(rand: Random): String = {
    countryV2=countriesV2(rand.between(0, countriesV2.size-1))
    countryV2.capital.getOrElse("")
  }
  def getWrongCapitalV2(rand: Random): String = {
    var capitals: Seq[String] = Seq()
    for(c <- countriesV2) capitals = capitals :+ c.alpha2Code.getOrElse("")
    var wrongCapital = rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
    while(capitals.contains(wrongCapital)) wrongCapital = rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
    wrongCapital
  }
  def getNameV2(rand: Random): String = {
    countriesV2(rand.between(0, countriesV2.size-1)).name.getOrElse("")
  }
  def getCurrencyV2(rand: Random): String = {
    countriesV2(rand.between(0, countriesV2.size-1)).currencies.getOrElse(Seq(CurrenciesV2())).head.code.getOrElse("EUR")
  }
  def getBadCurrencyV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongCurrencyV2(rand: Random): String = {
    var currencies: Seq[String] = Seq()
    for(c <- countriesV2) currencies = currencies :+ c.currencies.getOrElse(Seq(CurrenciesV2())).head.code.getOrElse("EUR")
    var wrongCurrency = rand.alphanumeric.filter(_.isLetter).take(3).mkString.toUpperCase
    while(currencies.contains(wrongCurrency)) wrongCurrency = rand.alphanumeric.filter(_.isLetter).take(3).mkString.toUpperCase
    wrongCurrency
  }
  def getLangV2(rand: Random): String = {
    countryV2 = countriesV2(rand.between(0, countriesV2.size-1))
    if(countryV2.languages.head.isEmpty) ""
    else if (rand.between(0.0, 1.0)<=0.5) ""
    else countryV2.languages.head.head.iso639_1.get
  }
  def getWrongLangV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getRegionV2(rand: Random): String = {
    countryV2=countriesV2(rand.between(0, countriesV2.size-1))
    countryV2.region.getOrElse("")
  }
  def assertEmptyV2(string: String): Boolean = {
    string==""
  }
  def getSubregionV2(rand: Random): String = {
    countryV2 = countriesV2(rand.between(0, countriesV2.size-1))
    countryV2.subregion.getOrElse("")
  }
  def getDemonym(rand: Random): String = {
    countryV2 = countriesV2(rand.between(0, countriesV2.size-1))
    if(rand.between(0.0,1.0)<=0.5) countryV2.demonym.getOrElse("")
    else ""
  }
  def getRegionalBloc(rand: Random): String = {
    countryV2 = countriesV2(rand.between(0, countriesV2.size-1))
    if(rand.between(0.0,1.0)<=0.5)
      if(countryV2.regionalBlocs.get.isEmpty) ""
      else countryV2.regionalBlocs.get.head.acronym.get
    else ""
  }
  def getWrongRegionalBloc(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongDemonym(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongSubregionV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongRegionV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
  def getWrongNameV2(rand: Random): String = {
    rand.alphanumeric.filter(_.isLetter).take(20).mkString.toUpperCase
  }
//  REPLIES
//  def constant(string: String, rand: Random): String = {
//    string
//  }
//  var countries: Seq[GetAll] = null
//  var country:GetAll = null
//  def setCountries(countries: Seq[GetAll]): Boolean = {
//    this.countries = countries
//    true
//  }
//  var randomAlphaCode = false
//  def getAlphaCode(rand: Random): String ={
//    randomAlphaCode = false
//    val tmp = rand.between(0.0,1.1)
//    if(tmp < 0.33) {
//      country = this.countries(rand.between(0, this.countries.size - 1))
//      country.alpha2Code
//    } else if (tmp < 0.66) {
//      country = this.countries(rand.between(0, this.countries.size-1))
//      if(country.alpha3Code.get.isEmpty) randomAlphaCode = true
//      country.alpha3Code.getOrElse("")
//    } else {
//      randomAlphaCode = true
//      Random.alphanumeric.filter(_.isLetter).take(3).mkString.toUpperCase
//    }
//  }
//  def assertRandomAlphaCode(): Boolean = {
//    randomAlphaCode
//  }
//  def getRegion(rand: Random): String = {
//    country = this.countries(rand.between(0, this.countries.size-1))
//    country.region.getOrElse("")
//  }
//  def assertEmptyRegion(): Boolean = {
//    country.region.get.isEmpty
//  }
//  def getCurrency(rand: Random): String = {
//    country = this.countries(rand.between(0, this.countries.size-1))
//    if(country.currencies.getOrElse(Seq(Currencies(""))).head.code == null){
//      "(none)"
//    } else {
//      country.currencies.getOrElse(Seq(Currencies(""))).head.code
//    }
//  }
//  def assertEmptyCurrency(): Boolean = {
//    country.currencies.get.isEmpty
//  }
//  def getLang(rand: Random): String = {
//    country = this.countries(rand.between(0, this.countries.size-1))
//    country.languages.getOrElse(Seq(Languages(""))).head.iso639_1
//  }
//  def assertEmptyLang(): Boolean = {
//    country.languages.get.isEmpty
//  }
//  def getCapital(rand: Random): String = {
//    country = this.countries(rand.between(0, this.countries.size-1))
//    country.capital.getOrElse("")
//  }
//  def assertEmptyCapital(): Boolean = {
//    country.capital.get.isEmpty
//  }
//  def getCountryName(rand: Random): String = {
//    country = this.countries(rand.between(0, this.countries.size-1))
//    country.name.getOrElse("")
//  }
//  def assertEmptyCountryName():Boolean = {
//    country.name.isEmpty
//  }
}
