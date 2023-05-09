S_ur=!AuthenticationApi.create(tokenRequest: TokenRequest(getTokenRequest)).?C200(token: Token)<util.setToken(token)>.
    rec Y.(!UsersApi.save(createUserProps: CreateUserProps(getUserProps)).?C201(user: User)<util.testAndSetUser(user)>.
        rec X.(+{!UsersApi.index(tok: String(getAccessToken))<0.3>.?C200(pageUser: PageUser)<util.checkIndex(pageUser)>.X,
            !UsersApi.save(createUserProps: CreateUserProps(getUserProps))<0.1>.?C400().X,
            !UsersApi.update(tok: String(getAccessToken), id: Long(getUserId), updateUserProps: UpdateUserProps(getUpdateUserProps))<0.2>.?C200(user: User)<util.testAndSetUser(user)>.X,
            !UsersApi.destroy(tok: String(getAccessToken), id: Long(getUserId))<0.4>.?C204().
                +{!UsersApi.show(tok, id)<0.3>.?C404().Y,
                !UsersApi.update(tok, id, updateUserProps: UpdateUserProps(getUpdateUserProps))<0.3>.?C404().Y,
                !UsersApi.destroy(tok, id)<0.4>.?C404()}}))