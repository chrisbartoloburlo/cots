S_petstore=!UserApi.loginUser(username: String(getUsername), password: String(getPassword)).?C200().
            +{!PetApi.addPet(pet: Pet(createPet)).?C200().
                !PetApi.getPetById(apikey: String(getApiKey), petId: Long(getPetId)).?C200().
                !PetApi.findPetsByTags(petTag: String(getPetTag)).?C200().
                !PetApi.findPetsByStatus(petStatus: String(getPetStatus)).?C200().
                !PetApi.updatePet(pet).?C200().
                !PetApi.updatePetWithForm(petId: Long(getPetId), petName: String(getPetName), petStatus: String(getPetStatusOption)).
                !StoreApi.placeOrder(order: Order(getOrder)).?C200().
                !StoreApi.getOrderById(orderId: Long(getOrderId)).?C200().
                !StoreApi.getInventory(apikey).?C200().
                !PetApi.deletePet(apikey, petId).?C200(),
            !PetApi.addPet(pet: Pet(createPet)).?C200().
                !PetApi.deletePet(apikey: String(getApiKey), petId: Long(getPetId)).?C200(),
            !StoreApi.placeOrder(order: Order(getOrder)).?C200().
                !StoreApi.getOrderById(orderId: Long(getOrderId)).?C200().
                !StoreApi.deleteOrder(orderId).?C200().
                !StoreApi.getInventory(apikey: String(getApiKey)).?C404(),
            !UserApi.createUser(user: User(createUser)).?C200().
                !UserApi.createUsersWithArrayInput(users: User(createUsers)).?C200().
                !UserApi.createUsersWithListInput(users: User(createUsers)).?C200().
                !UserApi.getUserByName(username: String(getTestUsername)).?C200().
                !UserApi.updateUser(username, user).?C200().
                !UserApi.logoutUser().?C200()
            }