package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.Date

val JWT_SECRET = "rm4944585" // В продакшене храните в безопасном месте!
val JWT_ISSUER = "your-app-name"
val JWT_AUDIENCE = "your-app-audience"
val JWT_REALM = "your-app-realm"
val JWT_EXPIRE_MS = 36000000 // 10 часов
fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain

    install(Authentication) {
        jwt("auth-jwt") { // Это имя должно совпадать с тем, что используется в authenticate()
            this.realm = JWT_REALM
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWT_SECRET))
                    .withAudience(JWT_AUDIENCE)
                    .withIssuer(JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(JWT_AUDIENCE)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
fun generateToken(username: String,isAdmin: Boolean): String {
    return JWT.create()
        .withIssuer(JWT_ISSUER)
        .withAudience(JWT_AUDIENCE)
        .withClaim("username", username)
        .withClaim("isAdmin", isAdmin)
        .withExpiresAt(Date(System.currentTimeMillis() + JWT_EXPIRE_MS))
        .sign(Algorithm.HMAC256(JWT_SECRET))
}
fun verifyToken(token: String, issuer: String, audience: String, secret: String): Boolean {
    return try {
        val verifier = JWT.require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
        verifier.verify(token)
        true
    } catch (e: JWTVerificationException) {
        false
    }
}
