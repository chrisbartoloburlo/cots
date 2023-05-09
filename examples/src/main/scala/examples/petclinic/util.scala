package examples.petclinic

import examples.petclinic.model.{Owner, OwnerFields, Pet, PetFields, PetType, Role, Specialty, User, Vet, Visit, VisitFields}

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}
import scala.util.Random

object util {
  var id=20
  def getNextId(rand: Random): Int = {
    val tmpid = id
    id+=1
    tmpid
  }

  def configName(rand: Random): String = {
    rand.alphanumeric.take(20).mkString
  }
  def featureName(rand: Random): String = {
    rand.alphanumeric.take(20).mkString
  }
  def randDesc(rand: Random): String = {
    rand.alphanumeric.take(20).mkString
  }
  def const(string: String, rand: Random): String ={
    string
  }
  def optDescr(rand: Random): Option[String] = {
    Option(rand.alphanumeric.take(20).mkString)
  }
  def rand(min: String, max: String, rand: Random): Int ={
    min.toInt + rand.nextInt( (max.toInt - min.toInt) + 1 )
  }
  def getRandomOwnerFields (firstName: String, lastName: String, address: String, city: String, telephone: String, rand: Random): OwnerFields = {
    if (rand.between(0.0,1.0)>0.5) {
      OwnerFields.apply(firstName, lastName, address, city, telephone)
    } else {
      OwnerFields.apply("123", "456", "789", "10", "11")
    }
  }
  def getOwnerFields (firstName: String, lastName: String, address: String, city: String, telephone: String, rand: Random): OwnerFields = {
    OwnerFields.apply(firstName, lastName, address, city, telephone)
  }
  def assertCorrect(ownerFields: OwnerFields): Boolean = {
    ownerFields.firstName != "123"
  }
  def assertIncorrect(ownerFields: OwnerFields): Boolean = {
    ownerFields.firstName == "123"
  }
  def getUpdatedOwnerFields (rand: Random): OwnerFields = {
    OwnerFields.apply("updatedName", "updatedLastName", "Updated Address", "Updated City", "12345678")
  }
  var petType: PetType = PetType("testPetType", 7)
  def getPetType(rand: Random): PetType = {
    petType=PetType.apply("testPetType", 7)
    petType
  }
  def setPetType(petType: PetType): Boolean = {
    this.petType=petType
    true
  }
  def getPetTypeId(rand: Random): Int = {
   petType.id
  }
  def getUpdatedPetType(rand: Random): PetType = {
    PetType.apply("updatedTestPetType", petType.id)
  }

  var specialty: Specialty = Specialty(4, "testSpecialty")
  def getSpecialty(rand: Random): Specialty = {
    specialty=Specialty.apply(4, "testSpeciality")
    specialty
  }
  def setSpecialty(specialty: Specialty): Boolean = {
    this.specialty=specialty
    true
  }
  def getSpecialtyId(rand: Random): Int ={
    specialty.id
  }
  def getUpdatedSpecialty(rand: Random): Specialty = {
    specialty=Specialty.apply(4, "updatedTestSpeciality")
    specialty
  }

  private var ownerId: Int = 0
  def setOwnerId(owner: Owner): Boolean = {
    ownerId = owner.id.get
    true
  }
  def getOwnerId(rand: Random):Int = {
    ownerId
  }
  def getOwnerId(owner: Owner):Int = {
    owner.id.get
  }
  def getLastName(rand: Random): Some[String] ={
    Some("updatedLastName")
  }

  private var vetId: Int = 0
  def setVetId(vet: Vet): Boolean = {
    vetId = vet.id
    true
  }
  def getVetId(rand: Random):Int = {
    vetId
  }
  def getVetId(vet: Vet):Int = {
    vet.id
  }

  def getPet(rand: Random): Pet = {
    val df = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    Pet.apply("petName", LocalDate.parse("2020/12/10", df), PetType("cat", 1), 14, Some(getOwnerId(new scala.util.Random(1))), Seq())
  }
  def getVet(rand: Random): Vet = {
    Vet.apply("VetName", "VetLastName", Seq(), 1)
  }
  def getUpdatedVet(rand: Random): Vet = {
    Vet.apply("UpdatedVetName", "UpdatedVetLastName", Seq(Specialty(2,"surgery")), 1)
  }

  def checkPetType(petType: PetType): Boolean = {
    this.petType.id==petType.id && this.petType.name==petType.name
  }
  val df = DateTimeFormatter.ofPattern("yyyy/MM/dd")
  var pet: Pet = Pet.apply("petName", LocalDate.parse("2020/12/10", df), PetType("cat", 1), 14, Some(getOwnerId(new scala.util.Random(1))), Seq())
  def setRandomPet(pets: Seq[Pet]): Boolean = {
    if (pets.nonEmpty) {
      pet = pets.head
      true
    } else false
  }
  def getRandomPetId(rand: Random): Int = {
    pet.id
  }
  def getUpdatedRandomPet(rand: Random): Pet = {
    pet = Pet.apply("UpdatedPetName", LocalDate.parse("2022/12/10", df), PetType("cat", 1), pet.id, pet.ownerId, Seq())
    pet
  }

  def getPetFields(rand: Random): PetFields = {
    PetFields.apply(pet.name, pet.birthDate, pet.`type`)
  }
  def checkPets(pet1: Pet, pet2: Pet): Boolean = {
    pet1.name==pet2.name && pet1.id==pet2.id && pet1.birthDate==pet2.birthDate && pet1.ownerId==pet2.ownerId && pet1.visits==pet2.visits
  }

  var visit: Visit = Visit.apply(Some(LocalDate.parse("2022/12/10", df)), "Test Description", 500, Some(getRandomPetId(new scala.util.Random(1))))
  def getVisit(rand: Random): Visit = {
    visit
  }
  def testVisit(visit: Visit): Boolean = {
    this.visit=visit
    this.visit.id==visit.id && this.visit.date==visit.date && this.visit.description==visit.description && this.visit.petId==visit.petId
  }
  def getVisitId(rand: Random): Int = {
    this.visit.id
  }
  def getVisitFields(rand: Random): VisitFields ={
    VisitFields.apply(Some(LocalDate.parse("2022/12/10", df)), "test description")
  }
  def setVisit(visit: Visit): Boolean = {
    this.visit = visit
    visit.petId.get == getRandomPetId(new Random(1)) && visit.description == "test description" && visit.date.get==LocalDate.parse("2022/12/10", df)
  }
  def getUpdatedVisit(rand: Random): Visit = {
    this.visit = Visit.apply(Some(LocalDate.parse("2019/10/01", df)), "Updated Test Description", this.visit.id, this.visit.petId)
    this.visit
  }
  def getUser(rand: Random): User = {
    User.apply("testUsername", Some("testPassword"), Some(true), Some(Seq(Role.apply("OWNER_ADMIN"))))
  }
}
