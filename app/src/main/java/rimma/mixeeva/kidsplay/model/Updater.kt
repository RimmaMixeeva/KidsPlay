package rimma.mixeeva.kidsplay.model

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import rimma.mixeeva.kidsplay.data.database.dao.AchievementDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.AchievementsDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDescriptionDao
import rimma.mixeeva.kidsplay.data.preferences.UserPreferencesKeys
import rimma.mixeeva.kidsplay.data.preferences.UserPreferencesRepository
import rimma.mixeeva.kidsplay.data.server.RetrofitInstance
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAchievementRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateColorGameLevelsRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateGiftRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Updater @Inject constructor(
    var giftDao: GiftDao,
    var achievementsDao: AchievementsDao,
    var colorGameLevelDao: ColorGameLevelDao,
    var achievementDescriptionDao: AchievementDescriptionDao,
    var colorGameDescriptionDao: ColorGameDescriptionDao,
    var userPreferencesRepository: UserPreferencesRepository
) {
    suspend fun observeGifts() {
        giftDao.getAll().collect { gifts ->
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            gifts.forEach { item ->
                if (childToken != null && nickname != null) {
                    RetrofitInstance.instance.updateGifts(
                        "Bearer $childToken",
                        CreateUpdateGiftRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            obtained = item.obtained,
                            opened = item.opened,
                            used = item.used
                        )
                    )

                }
            }
        }
    }

    suspend fun observeAchievements() {
        achievementsDao.getAll().collect { achievements ->
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            achievements.forEach { item ->
                if (childToken != null && nickname != null) {
                    RetrofitInstance.instance.updateAchievements(
                        "Bearer $childToken",
                        CreateUpdateAchievementRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            obtained = item.obtained
                        )
                    )
                }
            }
        }
    }

    suspend fun observeColorLevels() {
        colorGameLevelDao.getAll().collect { levels ->
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val colorGameLevels = colorGameLevelDao.getAll().first()
            colorGameLevels.forEach { item ->
                if (childToken != null && nickname != null) {
                    RetrofitInstance.instance.updateColorGameLevels(
                        "Bearer $childToken",
                        CreateUpdateColorGameLevelsRequest(
                            username = nickname,
                            levelNumber = item.levelNumber,
                            starsAchieved = item.starsAchieved,
                            descriptionId = item.descriptionId,
                            isLevelOpened = item.isLevelOpened
                        )
                    )
                }
            }
        }
    }

    suspend fun observeIntelligence() {
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_INTELLIGENCE).collect{
            observeAttributes()
        }
    }

    suspend fun observeReaction() {
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_REACTION).collect{
            observeAttributes()
        }
    }
    suspend fun observeLogic() {
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_LOGIC).collect{
            observeAttributes()
        }
    }
    suspend fun observeAttentiveness () {
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_ATTENTIVENESS).collect{
            observeAttributes()
        }
    }


    suspend fun observeAttributes() {
        val nickname =
            userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                .first()
        var childToken =
            userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                .first()
        val intelligence =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_INTELLIGENCE)
                .first()
        val attentiveness =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_ATTENTIVENESS)
                .first()
        val reaction =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_REACTION)
                .first()
        val logic =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_LOGIC)
                .first()
        val coins =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_COINS)
                .first()

        if (childToken != null && nickname != null && intelligence != null && attentiveness != null && reaction != null && logic != null && coins != null) {
            var response = RetrofitInstance.instance.updateAttributes(
                "Bearer $childToken",
                rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAttributesRequest(
                    nickname,
                    intelligence,
                    attentiveness,
                    reaction,
                    logic,
                    coins
                )
            )
            if (response.isSuccessful) {
                Log.d("SERVER UPDATE", "Success while updating player attributes")
                Log.d("SERVER UPDATE", "Returned result: " + response.body()?.response)
            } else {
                Log.d(
                    "SERVER UPDATE",
                    "Error happened while updating player attributes: " + response.code() + " " + response.message() + response.errorBody()
                )
            }
        }
    }

}