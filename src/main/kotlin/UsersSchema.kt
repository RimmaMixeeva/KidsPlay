package com.example

import com.example.UserService.atributes
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt


@Serializable
data class ExposedUser(val username: String, val email: String, val password : String)
@Serializable
data class LoginUser(val username: String,  val password : String)
@Serializable
data class AuthResponse( val token: String )
@Serializable
data class ExposedPlayer(val username: String,val nickname: String, val parentname: String, val avatarId : Long)
@Serializable
//data class AchievementsPlayer(val username: String,val title:String,val condition: String, val description: String,val obtained: Boolean)
data class AchievementsPlayer(val username: String, val descriptionId : Int,val obtained: Boolean)
@Serializable
//data class PlayerGift(val username: String,val title:String,val condition: String, val description : String,val obtained: Boolean,val opened: Boolean,val used: Boolean)
data class PlayerGift(val username: String, val descriptionId : Int,val obtained: Boolean,val opened: Boolean,val used: Boolean)
@Serializable
data class PlayerAtributes(val username: String,val intelligence: Int,val attentiveness: Int, val reaction: Int,val logic: Int,val coins: Int)
@Serializable
//data class PlayerColorGameLevel(val username: String,val levelNumber: Int,val starsAchieved: Int,val timer: Int,val subLevels: Int,val isColorPhrased: Boolean,
                               // val hasVoiceActing: Boolean,val numOfColors: Int,var isLevelOpened: Boolean,val gift: Int)
data class PlayerColorGameLevel(val username: String,val levelNumber: Int,val starsAchieved: Int,val descriptionId : Int,var isLevelOpened: Boolean)
@Serializable
data class AddPlayer(val username: String,  val avatarId: Long)
@Serializable
data class Response( val response: String )
@Serializable
data class ListResponse( val list: List<String> )
@Serializable
data class ListIdResponse( val listId: List<Int> )

class UserService(database: Database) {
    object account : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", 50).uniqueIndex()
        val email = varchar("email", 100)
        val password = varchar("password", 100)
        override val primaryKey = PrimaryKey(id)
    }

    object players : Table() {

        val id = integer("id").autoIncrement()
        val username = varchar("username", 50).uniqueIndex()
        val nickname = varchar("nickname", 50)
        val parentname = varchar("parentname", 50)
        val avatarId = long("avatarId").default(0L)
        override val primaryKey = PrimaryKey(id)
    }

    object achievements : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", 50)
        val descriptionId = integer("descriptionId")
        val obtained = bool("obtained")
        override val primaryKey = PrimaryKey(id)
    }

    object gifts : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", 50)
        val descriptionId = integer("descriptionId")
        val obtained = bool("obtained")
        val opened = bool("opened")
        val used = bool("used")
        override val primaryKey = PrimaryKey(id)
    }

    object atributes : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", 50).uniqueIndex()
        val intelligence = integer("intelligence")
        val attentiveness = integer("attentiveness")
        val reaction = integer("reaction")
        val logic = integer("logic")
        val coins = integer("coins")
        override val primaryKey = PrimaryKey(id)
    }

    object colorgamelevel : Table() {
        val id = integer("id").autoIncrement()
        val username = varchar("username", 50)
        val levelNumber = integer("levelNumber")
        val starsAchieved = integer("starsAchieved")
        val descriptionId = integer("descriptionId")
        val isLevelOpened = bool("isLevelOpened")
        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(account, players, achievements, gifts, atributes, colorgamelevel)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


    // Генерация хеша
    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    // Проверка пароля
    private fun verifyPassword(password: String, hash: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }

    suspend fun create(user: ExposedUser): Int {
        val isUsernameUnique = dbQuery {
            account.selectAll().where { account.username eq user.username }.empty()
        }
        if (!isUsernameUnique) return 0
        return dbQuery {
            val hash = hashPassword(user.password)
            account.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = hash
            }
        }[account.id]

    }

    suspend fun createPlayer(player: ExposedPlayer): Int {
        val isUsernameUnique = dbQuery {
            players.selectAll().where { players.username eq player.username }.empty()
        }
        if (!isUsernameUnique) return 0

        return dbQuery {
            players.insert {
                it[username] = player.username
                it[nickname] = player.nickname
                it[parentname] = player.parentname
                it[avatarId] = player.avatarId
            }
        }[players.id]
    }

    suspend fun updatePlayerAttribute(atr: PlayerAtributes): Int {

        return dbQuery {
            atributes.update({ (atributes.username eq atr.username) }) {
                it[intelligence] = atr.intelligence
                it[attentiveness] = atr.attentiveness
                it[reaction] = atr.reaction
                it[logic] = atr.logic
                it[coins] = atr.coins
            }
        }
    }

    suspend fun createAchievement(achievementsPlayer: AchievementsPlayer): Int {

        return dbQuery {
            achievements.insert {
                it[username] = achievementsPlayer.username
                it[descriptionId] = achievementsPlayer.descriptionId
                it[obtained] = achievementsPlayer.obtained
            }
        }[achievements.id]
    }

    suspend fun updateAchievement(achievementsPlayer: AchievementsPlayer): Int {

        return dbQuery {
            achievements.update({ (achievements.username eq achievementsPlayer.username) and (achievements.descriptionId eq achievementsPlayer.descriptionId) }) {

                it[obtained] = achievementsPlayer.obtained
            }
        }
    }

    suspend fun updateGift(gift: PlayerGift): Int {

        return dbQuery {
            gifts.update({ (gifts.username eq gift.username) and (gifts.descriptionId eq gift.descriptionId) }) {

                it[obtained] = gift.obtained
                it[opened] = gift.opened
                it[used] = gift.used
            }
        }
    }

    suspend fun updatePlayerColorGameLevel(gameLevel: PlayerColorGameLevel): Int {
        return dbQuery {
            colorgamelevel.update({ (colorgamelevel.username eq gameLevel.username) and (colorgamelevel.levelNumber eq gameLevel.levelNumber) }) {

                it[starsAchieved] = gameLevel.starsAchieved
                it[isLevelOpened] = gameLevel.isLevelOpened

            }
        }
    }

    suspend fun createGift(gift: PlayerGift): Int {


        return dbQuery {
            gifts.insert {
                it[username] = gift.username
                it[descriptionId] = gift.descriptionId
                it[obtained] = gift.obtained
                it[opened] = gift.opened
                it[used] = gift.used
            }
        }[gifts.id]
    }

    suspend fun createAtributes(atribute: PlayerAtributes): Int {
        val isUsernameUnique = dbQuery {
            atributes.selectAll().where { atributes.username eq atribute.username }.empty()
        }
        if (!isUsernameUnique) return 0
        return dbQuery {
            atributes.insert {
                it[username] = atribute.username
                it[intelligence] = atribute.intelligence
                it[attentiveness] = atribute.attentiveness
                it[reaction] = atribute.reaction
                it[logic] = atribute.logic
                it[coins] = atribute.coins

            }
        }[atributes.id]

    }

    suspend fun createPlayerColorGameLevel(gameLevel: PlayerColorGameLevel): Int {
        return dbQuery {
            colorgamelevel.insert {
                it[username] = gameLevel.username
                it[levelNumber] = gameLevel.levelNumber
                it[starsAchieved] = gameLevel.starsAchieved
                it[descriptionId] = gameLevel.descriptionId
                it[isLevelOpened] = gameLevel.isLevelOpened

            }
        }[colorgamelevel.id]

    }

    suspend fun isValidPlayer(username: String): Boolean {
        val isUsernameUnique = dbQuery {
            players.selectAll().where { players.username eq username }.empty()
        }
        return !isUsernameUnique
    }

    suspend fun getUserById(id: Int): ExposedUser? {
        return dbQuery {
            account.selectAll()
                .where { account.id eq id }
                .map { ExposedUser(it[account.username], it[account.email], it[account.password]) }
                .singleOrNull()
        }
    }


    suspend fun getPlayerByUsername(username: String): ExposedPlayer? {
        return dbQuery {
            players.selectAll()
                .where { players.username eq username }
                .map {
                    ExposedPlayer(
                        it[players.username],
                        it[players.nickname],
                        it[players.parentname],
                        it[players.avatarId]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getPlayerByParent(parent: String): List<String> {
        // val childList = transaction

        return dbQuery {
            players.select(players.username)
                .where { players.parentname eq parent }
                .map { it[players.username] }

        }

    }


    suspend fun getAttributeByUsername(username: String): PlayerAtributes? {
        return dbQuery {
            atributes.selectAll()
                .where { atributes.username eq username }
                .map {
                    PlayerAtributes(
                        it[atributes.username],
                        it[atributes.intelligence],
                        it[atributes.attentiveness],
                        it[atributes.reaction],
                        it[atributes.logic],
                        it[atributes.coins]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getAchievementsIdByUsername(username: String): List<Int> {
        return dbQuery {
            achievements.select(achievements.id)
                .where { achievements.username eq username }
                .map { it[achievements.id] }
        }

    }

    suspend fun getGiftIdByUsername(username: String): List<Int> {
        return dbQuery {
            gifts.select(gifts.id)
                .where { gifts.username eq username }
                .map { it[gifts.id] }
        }

    }

    suspend fun getColorLevelIdByUsername(username: String): List<Int> {
        return dbQuery {
            colorgamelevel.select(colorgamelevel.id)
                .where { colorgamelevel.username eq username }
                .map { it[colorgamelevel.id] }
        }
    }

    suspend fun getGiftById(id: Int): PlayerGift? {
        return dbQuery {
            gifts.selectAll()
                .where { gifts.id eq id }
                .map {
                    PlayerGift(
                        it[gifts.username],
                        it[gifts.descriptionId],
                        it[gifts.obtained],
                        it[gifts.opened],
                        it[gifts.used]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getColorLevelById(id: Int): PlayerColorGameLevel? {
        return dbQuery {
            colorgamelevel.selectAll()
                .where { colorgamelevel.id eq id }
                .map {
                    PlayerColorGameLevel(
                        it[colorgamelevel.username],
                        it[colorgamelevel.levelNumber],
                        it[colorgamelevel.starsAchieved],
                        it[colorgamelevel.descriptionId],
                        it[colorgamelevel.isLevelOpened]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getAchievementsById(id: Int): AchievementsPlayer? {
        return dbQuery {
            achievements.selectAll()
                .where { achievements.id eq id }
                .map {
                    AchievementsPlayer(
                        it[achievements.username],
                        it[achievements.descriptionId],
                        it[achievements.obtained]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun authenticated(name: String, password: String): Boolean {
        val hashPassword = try {
            dbQuery {
                account.selectAll()
                    .where { account.username eq name }
                    .map { it[account.password] }
                    .singleOrNull()
            }
        } catch (e: Exception) {
            //logger.error("Database query failed: ${e.message}")
            return false
        }
        if (hashPassword == null) return false
        return verifyPassword(password, hashPassword)
    }

    suspend fun getTopPlayersByExperience(): List<String> {
        return dbQuery {
            atributes.selectAll()
                .orderBy(atributes.intelligence, SortOrder.DESC)
                .limit(10)
                .map {  it[atributes.username] }

        }
    }


    }





