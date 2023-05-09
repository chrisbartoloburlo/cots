S_openverse=!AuthTokensApi.registerApiOauth2(format: String(getFormat), data: RegisterApiOauth2Request(getRegisterApiOauth2Request)).
            ?C201(registration: OAuth2RegistrationSuccessful)<util.setRegistration(registration)>.
            !AuthTokensApi.authorizeApiOauth2(data: AuthorizeApiOauth2Request(getAuthorizeApiOauth2Request)).
            ?C201(authentication: OAuth2AuthenticationSuccessful)<util.setToken(authentication)>.
            !ImagesApi.imageSearch(token: String(getToken), format, q: String(constant "test")).
            ?C200(imageSearch: ImageSearch)<util.setImages(imageSearch)>.
            !ImagesApi.imageDetail(identifier: String(getIdentifier), format).
            ?C200(image: Image)

