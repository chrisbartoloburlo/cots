package examples.gestaohospital

import examples.featuresservice.model.Product
import examples.gestaohospital.model.ProductDTOEnums.ProductType
import examples.gestaohospital.model.ProductDTOEnums.ProductType.COMMON
import examples.gestaohospital.model.{HospitalDTO, Patient, ProductDTO}

import java.time.{OffsetDateTime, ZoneOffset}
import scala.util.Random

object util {
  var hospitals: Seq[HospitalDTO] = Seq()
  def setHospitals(hospitals: Seq[HospitalDTO]): Boolean = {
    this.hospitals=hospitals
    true
  }
  def getRandomHospitalId(random: Random): String = {
    hospitals(random.between(0, hospitals.size-1)).id.getOrElse("")
  }
  def getWrongHospitalId(random: Random): String = {
    var wrongId = random.nextInt().toString
    while (hospitals.map(_.id.get).contains(wrongId) || wrongId==hospital.id) wrongId = random.nextInt().toString
    wrongId
  }
  var hospital: HospitalDTO = HospitalDTO()
  def getHospitalDTO(random: Random): HospitalDTO = {
    hospital=HospitalDTO(address=Some("Test Address"), availableBeds=Some(50), beds=Some(100), latitude=Some(random.between(-90.0, 90.0).toString), longitude=Some(random.between(-180.0,180.0).toString), name=Some("Test Name"))
    hospital
  }
  def setHospital(hospital: HospitalDTO): Boolean = {
    if(this.hospital.name==hospital.name) {
      this.hospital = hospital
      true
    } else false
  }
  def getLat(random: Random): String = {
    (hospital.latitude.get.toDouble-(hospital.latitude.get.toDouble % 0.01)).toString
  }
  def getLon(random: Random): String = {
    (hospital.longitude.get.toDouble-(hospital.longitude.get.toDouble % 0.01)).toString
  }
  def getRadius(random: Random): String = {
    (0.0).toString
  }
  def assertCorrect(hospitalDTO: HospitalDTO): Boolean = {
    this.hospital==hospitalDTO
  }
  def getHospitalId(random: Random): String = {
    this.hospital.id.get
  }
  def getPatient(random: Random): Patient = {
    Patient(active=Some(true), birthDate = Some(OffsetDateTime.of(1998, 7, 22, 20, 15, 45, 345875000, ZoneOffset.of("+01:00"))), gender=Some("Male"), name=Some("test"))
  }
  var patient: Patient = Patient()
  def setPatient(patient: Patient): Boolean={
    this.patient=patient
    true
  }
  def getPatientId(random: Random): String = {
    this.patient.id.get
  }
  def assertCorrectPatient(patient: Patient): Boolean = {
    if(this.patient.name==patient.name) {
      this.patient = patient
      true
    } else false
  }
  def getProduct(random: Random): ProductDTO = {
    ProductDTO(description=Some("test product"), name=Some("test name"), productName=Some("test product name"), quantity = Some(20))
  }
  var productDTO: ProductDTO = ProductDTO()
  def setProduct(productDTO: ProductDTO): Boolean = {
    this.productDTO=productDTO
    true
  }
  def getProductId(random: Random): String = {
    this.productDTO.id.get
  }
  def getAmount(random: Random): String = {
    10.toString
  }
  def getRadiusDouble(random: Random): Double = {
    100.0
  }
  def getWrongPatientId(random: Random): String = {
    random.nextInt().toString
  }
  def getWrongProductId(random: Random): String = {
    random.nextInt().toString
  }
  def constant(string: String, random: Random): String = {
    string
  }
  var transferProduct: ProductDTO = ProductDTO()
  def setProductTransfer(productDTO: ProductDTO): Boolean = {
    transferProduct = productDTO
    true
  }
  def getProductTransferId(random: Random): String = {
    transferProduct.id.get
  }
}
