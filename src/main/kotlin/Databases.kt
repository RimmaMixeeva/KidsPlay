package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*


fun Application.configureDatabases() {
    /*
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )

     */
    val database = Database.connect(
        "jdbc:mariadb://localhost:3306/game_db",
        driver = "org.mariadb.jdbc.Driver",
        user = "root",
        password = "raa0480235"
    )
    val userService = UserService(database)

    install(ContentNegotiation) {
        json()
    }
    routing {
        // authenticate("auth-jwt")
        authenticate("auth-jwt") {

            post("/addplayer") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val parentname = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }
                val player = call.receive<AddPlayer>()
                val username = player.username + "@#@" + parentname
                val playerexposed = ExposedPlayer(username, player.username, parentname, player.avatarId)
                val id = userService.createPlayer(playerexposed)
                //val id = 1
                if (id > 0) {
                    val access = generateToken(username, false)
                    call.respond(HttpStatusCode.Created, message = AuthResponse(token = access))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Username already exists"))
                }
            }

            post("/creatatributes") {

                    // Проверка аутентификации
                val principal = call.principal<JWTPrincipal>() ?: run {
                        call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                        return@post
                    }

                    // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                        return@post
                    }
                    /*
                    // Проверка срока действия токена
                    val currentTime = System.currentTimeMillis()
                    val expiresAt = principal.payload.expiresAt?.time ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Токен без срока действия")
                        return@post
                    }

                    if (currentTime > expiresAt) {
                        call.respond(HttpStatusCode.Unauthorized, "Токен просрочен")
                        return@post
                    }

                     */
                val attributes = call.receive<PlayerAtributes>()
                val exposed = PlayerAtributes(username,attributes.intelligence,attributes.attentiveness,attributes.reaction,attributes.logic,attributes.coins)
                val id = userService.createAtributes(exposed)
              // val id = 1.
               if(id>0){
                   call.respond(HttpStatusCode.OK, message = Response(response="Add player atribute"))
               } else {
                   call.respond(HttpStatusCode.Conflict, message = Response(response="Username already exists"))
               }

            }

            post("/createachievement") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val achieve = call.receive<AchievementsPlayer>()
                val exposed = AchievementsPlayer(
                    username,
                    achieve.descriptionId,
                    achieve.obtained
                )
                val id = userService.createAchievement(exposed)
                //val id = 1
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Add Achievements"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error in DB"))
                }
            }
            post("/update/attribute") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val gift = call.receive<PlayerAtributes>()
                val exposed = PlayerAtributes(
                    username,
                    gift.intelligence,
                    gift.attentiveness,
                    gift.reaction,
                    gift.logic,
                    gift.coins
                )
                val id = userService.updatePlayerAttribute(exposed)
                //val id = 1
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Update Attribute"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error Update Attribute"))
                }
            }
            post("/update/achievement") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val achieve = call.receive<AchievementsPlayer>()
                val exposed = AchievementsPlayer(
                    username,
                    achieve.descriptionId,
                    achieve.obtained
                )
                val id = userService.updateAchievement(exposed)
                //val id = 1
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Update Achievements"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error Update Achievements "))
                }
            }
            post("/update/gift") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val gift = call.receive<PlayerGift>()
                val exposed = PlayerGift(
                    username,
                    gift.descriptionId,
                    gift.obtained,
                    gift.opened,
                    gift.used
                )
                val id = userService.updateGift(exposed)
                //val id = 1
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Update Gift"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error Update Gift"))
                }
            }
            post("/update/colorgamelevel") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val colorGameLevel = call.receive<PlayerColorGameLevel>()
                val exposed = PlayerColorGameLevel(
                    username,
                    colorGameLevel.levelNumber,
                    colorGameLevel.starsAchieved,
                    colorGameLevel.descriptionId,
                    colorGameLevel.isLevelOpened

                )
                val id = userService.updatePlayerColorGameLevel(exposed)
                //val id = 1
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Update Color Game level"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error update Color Game level"))
                }
            }



            post("/creategift") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val gift = call.receive<PlayerGift>()

                val exposed = PlayerGift(
                    username,
                    gift.descriptionId,
                    gift.obtained,
                    gift.opened,
                    gift.used
                )
                val id = userService.createGift(exposed)
                //val id = 1.
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Add Gift"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error in DB"))
                }
            }
            post("/createcolorgamelevel") {
                // Обработка защищённого POST-запроса
                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val username = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }

                val gift = call.receive<PlayerColorGameLevel>()

                val exposed = PlayerColorGameLevel(
                    username,
                    gift.levelNumber,
                    gift.starsAchieved,
                    gift.descriptionId,
                    gift.isLevelOpened,


                )
                val id = userService.createPlayerColorGameLevel(exposed)
                //val id = 1.
                if (id > 0) {
                    call.respond(HttpStatusCode.OK, message = Response(response = "Add ColorGameLevel"))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Error in DB"))
                }
            }





            post("/updateplayer") {
                // Обработка защищённого POST-запроса

                val principal = call.principal<JWTPrincipal>() ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Требуется авторизация")
                    return@post
                }

                // Получение username из токена
                val parentname = principal.payload.getClaim("username").asString() ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Неверный формат токена: отсутствует username")
                    return@post
                }
                val player = call.receive<AddPlayer>()
                val playername = player.username + "@#@" + parentname
                val isValid = userService.isValidPlayer(playername)
                if (isValid) {
                    val access = generateToken(playername, false)
                    call.respond(HttpStatusCode.OK, message = AuthResponse(token = access))
                } else {
                    call.respond(HttpStatusCode.Conflict, message = Response(response = "Username not exists"))
                }

            }

        }

        // Create user
        post("/auth/register") {

            val user = call.receive<ExposedUser>()
            val id = userService.create(user)
            if(id>0){
                val access = generateToken(user.username, true)
                call.respond(HttpStatusCode.Created, message = AuthResponse(token = access))
            } else {
                call.respond(HttpStatusCode.Conflict, ("Username already exists"))
            }

        }
        post("/auth/login") {

                val request = try {
                call.receive<LoginUser>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request body")
                return@post
            }
            val name = request.username
            val password = request.password
            val auth = userService.authenticated(name, password)
            if (auth) {
                val access = generateToken(name, true)
                call.respond(HttpStatusCode.OK, message = AuthResponse(token = access))
                return@post
            } else {
                call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                return@post
            }

        }
        // Read user FOR TEST !!!
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = userService.getUserById(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get("/players/{username}") {
            val username = call.parameters["username"] ?: throw IllegalArgumentException("Invalid username")
            val user = userService.getPlayerByUsername(username)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get("/attributes/{username}") {
            val username = call.parameters["username"]?: throw IllegalArgumentException("Invalid username")
            val attribute = userService.getAttributeByUsername(username)
            if (attribute != null) {
                call.respond(HttpStatusCode.OK, attribute)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/achivelist/{username}") {
            val username = call.parameters["username"]?: throw IllegalArgumentException("Invalid username")
            val achieve = userService.getAchievementsIdByUsername(username)
            val response= ListIdResponse (achieve)
            call.respond(HttpStatusCode.OK, response)

        }



         get("/achive/{achiveId}") {
        val id = call.parameters["achiveId"]?.toInt() ?: throw IllegalArgumentException("Invalid Id")
        val achieve = userService.getAchievementsById(id)
        if (achieve != null) {
            call.respond(HttpStatusCode.OK, achieve)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

        get("/giftlist/{username}") {
            val username = call.parameters["username"]?: throw IllegalArgumentException("Invalid username")
            val giftId = userService.getGiftIdByUsername(username)
            val response= ListIdResponse (giftId)
            call.respond(HttpStatusCode.OK, response)

        }
        get("/gift/{giftId}") {
            val id = call.parameters["giftId"]?.toInt() ?: throw IllegalArgumentException("Invalid Id")
            val gift = userService.getGiftById(id)
            if (gift != null) {
                call.respond(HttpStatusCode.OK, gift)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        get("/colorlevellist/{username}") {
            val username = call.parameters["username"]?: throw IllegalArgumentException("Invalid username")
            val levellist= userService.getColorLevelIdByUsername(username)
            val response= ListIdResponse(levellist)
            call.respond(HttpStatusCode.OK, response)

        }
        get("/colorlevel/{colorId}") {
            val id = call.parameters["colorId"]?.toInt() ?: throw IllegalArgumentException("Invalid Id")
            val colorlevel = userService.getColorLevelById(id)
            if (colorlevel != null) {
                call.respond(HttpStatusCode.OK, colorlevel)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/getchild/{username}") {
            val username = call.parameters["username"]?: throw IllegalArgumentException("Invalid username")
            val childs = userService.getPlayerByParent(username)
            val response = ListResponse(childs)
            call.respond(HttpStatusCode.OK, response)

        }
        post("/top10") {

            val top = userService.getTopPlayersByExperience()
            call.respond(HttpStatusCode.OK, ListResponse(top))

        }



    }
}
