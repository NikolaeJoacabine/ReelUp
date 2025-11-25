package com.nikol.auth_impl.data.remote.service.route

import io.ktor.resources.Resource

@Resource("authentication")
class Auth {

    @Resource("guest_session")
    class GuestSession(val parent: Auth = Auth()) {

        @Resource("new")
        class New(val parent: GuestSession = GuestSession())
    }

    @Resource("token")
    class Token(val parent: Auth = Auth()) {

        @Resource("new")
        class New(val parent: Token = Token())

        @Resource("validate_with_login")
        class ValidateWithLogin(val parent: Token = Token())
    }

    @Resource("session")
    class Session(val parent: Auth = Auth()) {
        @Resource("new")
        class New(val parent: Session = Session())
    }
}