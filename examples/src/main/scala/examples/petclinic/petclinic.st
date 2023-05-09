S_pc=rec X.(
    +{!OwnerApi.addOwner(ownerFields: OwnerFields(getRandomOwnerFields "firstname" "lastname" "address 1" "city 1" "6085551023")).
        &{  ?C201(owner: Owner)<util.setOwnerId(owner) && util.assertCorrect(ownerFields)>.
                !OwnerApi.getOwner(ownerId: Int(getOwnerId)).?C200(retrievedOwner: Owner)<util.getOwnerId(retrievedOwner)==ownerId>.
                !OwnerApi.updateOwner(ownerId, ownerFields: OwnerFields(getUpdatedOwnerFields)).?C204().
                !OwnerApi.getOwner(ownerId).?C200(retrievedOwner: Owner).
                !OwnerApi.listOwners().?C200(owners: "Seq[Owner]")<owners.contains(retrievedOwner)>.
                !OwnerApi.listOwners(lastName: String(getLastName)).?C200(owners: "Seq[Owner]")<owners.contains(retrievedOwner)>.
                !OwnerApi.deleteOwner(ownerId).?C204().
                !OwnerApi.getOwner(ownerId).?C404().
                !OwnerApi.updateOwner(ownerId, ownerFields: OwnerFields(getUpdatedOwnerFields)).?C404().
                !OwnerApi.deleteOwner(ownerId).?C404().X,
            ?C400(restError: "HttpError[RestError]")<util.assertIncorrect(ownerFields)>.X},
    !PetApi.listPets().?C200(pets: "Seq[Pet]")<util.setRandomPet(pets)>.
        +{!PetApi.addPet(pet: Pet(getPet)).?C200(pet: Pet).X,
        !PetApi.getPet(petId: Int(getRandomPetId)).?C200(pet: Pet).
            !OwnerApi.addOwner(ownerFields: OwnerFields(getOwnerFields "firstname" "lastname" "address 1" "city 1" "6085551023")).?C201(owner: Owner)<util.setOwnerId(owner)>.
            !PetApi.addPetToOwner(ownerId: Int(getOwnerId), petFields: PetFields(getPetFields)).?C201(createdPet: Pet)<util.setRandomPet(pets)>.
            +{!PetApi.getOwnersPet(ownerId, petId: Int(getRandomPetId)).?C200(pet: Pet)<util.checkPets(createdPet, pet)>.X,
            !PetApi.updatePet(petId: Int(getRandomPetId), pet: Pet(getUpdatedRandomPet)).?C204().
                !PetApi.getPet(petId).?C200(updatedPet: Pet)<util.checkPets(updatedPet, pet)>.
                !PetApi.deletePet(petId).?C204().
                !PetApi.getPet(petId).?C404().
                !PetApi.updatePet(petId, pet: Pet(getUpdatedRandomPet)).?C404().
                !PetApi.deletePet(petId).?C404().
                !OwnerApi.deleteOwner(ownerId).?C204().X,
            !VisitApi.addVisitToOwner(ownerId, petId: Int(getRandomPetId), visitFields: VisitFields(getVisitFields)).?C201(visit: Visit)<util.setVisit(visit)>.
                !VisitApi.getVisit(visitId: Int(getVisitId)).?C200(visit: Visit)<util.testVisit(visit)>.
                !VisitApi.updateVisit(visitId, updatedVisit: Visit(getUpdatedVisit)).?C204().
                !VisitApi.listVisits().?C200(visits: "Seq[Visit]")<visits.contains(updatedVisit)>.
                !VisitApi.deleteVisit(visitId).?C204().
                !VisitApi.getVisit(visitId).?C404().
                !VisitApi.updateVisit(visitId, updatedVisit: Visit(getUpdatedVisit)).?C404().
                !VisitApi.deleteVisit(visitId).?C404().
                !OwnerApi.deleteOwner(ownerId).?C204().X},
        !VisitApi.addVisit(visit: Visit(getVisit)).?C201(visit: Visit)<util.testVisit(visit)>.
            !VisitApi.getVisit(visitId: Int(getVisitId)).?C200(visit: Visit)<util.testVisit(visit)>.X},
    !VetApi.addVet(vet: Vet(getVet)).?C201(vet: Vet)<util.setVetId(vet)>.
        +{!VetApi.listVets().?C200(vets: "Seq[Vet]")<vets.contains(vet)>.X,
        !VetApi.getVet(vetId: Int(getVetId)).?C200(vet: Vet).
            !VetApi.updateVet(vetId: Int(getVetId), updatedVet: Vet(getUpdatedVet)).?C204().
            !VetApi.listVets().?C200(vets: "Seq[Vet]")<vets.contains(updatedVet)>.
            !VetsApi.deleteVet(vetId).?C204().
            !VetApi.getVet(vetId).?C404().
            !VetApi.updateVet(vetId, updatedVet: Vet(getUpdatedVet)).?C404().
            !VetsApi.deleteVet(vetId).?C404().X},
    !PettypesApi.addPetType(petType: PetType(getPetType)).?C201(petType: PetType)<util.setPetType(petType)>.
        !PettypesApi.listPetTypes().?C200(petTypes: "Seq[PetType]").
        !PettypesApi.getPetType(petTypeId: Int(getPetTypeId)).?C200(retrievedPetType: PetType)<util.checkPetType(retrievedPetType)>.
        !PettypesApi.updatePetType(petTypeId, petType: PetType(getUpdatedPetType)).?C204().
        !PettypesApi.deletePetType(petTypeId).?C204().
        !PettypesApi.getPetType(petTypeId).?C404().
        !PettypesApi.updatePetType(petTypeId, petType: PetType(getUpdatedPetType)).?C404().
        !PettypesApi.deletePetType(petTypeId).?C404().X,
    !SpecialtyApi.addSpecialty(specialty: Specialty(getSpecialty)).?C201(specialty: Specialty)<util.setSpecialty(specialty)>.
        !SpecialtyApi.listSpecialties().?C200(specialties: "Seq[Specialty]")<specialties.contains(specialty)>.
        !SpecialtyApi.updateSpecialty(specialtyId: Int(getSpecialtyId), specialty: Specialty(getUpdatedSpecialty)).?C204().
        !SpecialtyApi.getSpecialty(specialtyId).?C200(retrievedSpecialty: Specialty).
        !SpecialtyApi.deleteSpecialty(specialtyId).?C204().
        !SpecialtyApi.getSpecialty(specialtyId).?C404().
        !SpecialtyApi.updateSpecialty(specialtyId, specialty: Specialty(getUpdatedSpecialty)).?C404().
        !SpecialtyApi.deleteSpecialty(specialtyId).?C404().X,
    !FailingApi.failingRequest().?C404(),
    !UserApi.addUser(user: User(getUser)).?C201(user: User).X
    })
