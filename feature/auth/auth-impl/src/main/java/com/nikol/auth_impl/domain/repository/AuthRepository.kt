package com.nikol.auth_impl.domain.repository

import arrow.core.Either
import com.nikol.auth_impl.domain.errors.CheckUserError
import com.nikol.auth_impl.domain.errors.CreateSessionError
import com.nikol.auth_impl.domain.model.CreateSessionRequest
import com.nikol.auth_impl.domain.model.RequestToken
import com.nikol.auth_impl.domain.model.SessionGuestToken
import com.nikol.auth_impl.domain.model.SessionToken
import com.nikol.auth_impl.domain.model.UserType
import com.nikol.domainutil.BaseResponseError

typealias ResponseRequestToken = Either<BaseResponseError, RequestToken>
typealias ResponseSessionToken = Either<CreateSessionError, SessionToken>
typealias ResponseSessionGuestToken = Either<BaseResponseError, SessionGuestToken>
typealias ResponseIfUserIsLogin = Either<CheckUserError, UserType>

interface AuthRepository {
    suspend fun getRequestToken(): ResponseRequestToken
    suspend fun createSession(createSessionRequest: CreateSessionRequest): ResponseSessionToken
    suspend fun createGuestSession(): ResponseSessionGuestToken
    suspend fun checkIfUserIsLogIn(): ResponseIfUserIsLogin
}