S_sockshop=rec X.(
  +{!DefaultApi.getCustomers(apiKey: String(getApiKey)).
      ?C200(customers: Getcustomersresponse),
    !DefaultApi.setUser(apiKey: String(getApiKey), register: Register(rndRegister)).
      ?C200(resp: Statusresponse)<util.setUserId(resp)>.
      +{!DefaultApi.getCustomer(apiKey, userId: String(getUserId)).
          ?C200(cust: Customer)<cust.username == register.username>,
        !DefaultApi.getCustomers(apiKey).
          ?C200(customers: Getcustomersresponse)<util.checkCustomerExists(customers, register.username)>,
        !DefaultApi.deleteCustomer(apiKey, userId: String(getUserId)).
          ?C200(delResp: Deleteresponse)<delResp.status>.
          !DefaultApi.getCustomers(apiKey: String(getApiKey)).
          ?C200(customers: Getcustomersresponse)<util.checkCustomerNotExists(customers, register.username)>,
        !DefaultApi.getCustomerCards(apiKey, userId: String(getUserId)).
          ?C200(cardResp: Getcardsresponse)<util.checkCardsEmpty(cardResp)>
       }
  }
)
