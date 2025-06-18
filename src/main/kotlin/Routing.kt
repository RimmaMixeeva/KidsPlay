package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting() {


    routing {
        get("/") {
            call.respondText("KidsPlay")
        }
        authenticate("auth-jwt") {
            get("/protected") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("username")?.asString()
                val expiresAt = principal?.expiresAt?.time?.let { Date(it) }

                call.respond(HttpStatusCode.OK,message = AuthResponse("Protected endpoint, user ID: $username, token expires at: $expiresAt"))
            }

            post("/secure-action") {
                // Обработка защищённого POST-запроса
                val requestBody = call.receive<String>()
                call.respond(HttpStatusCode.OK,message = AuthResponse("Secure action performed with body: $requestBody"))
            }


        }
    }
}
