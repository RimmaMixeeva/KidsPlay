package rimma.mixeeva.kidsplay

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.data.database.dao.AchievementDescriptionDao
import rimma.mixeeva.kidsplay.data.preferences.IUserPreferencesRepository
import rimma.mixeeva.kidsplay.data.preferences.UserPreferencesKeys
import rimma.mixeeva.kidsplay.data.database.dao.AchievementsDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDescriptionDao
import rimma.mixeeva.kidsplay.data.server.RetrofitInstance
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAchievementRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateColorGameLevelsRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateGiftRequest
import rimma.mixeeva.kidsplay.data.server.models.GetChildAchievementByIdResponse
import rimma.mixeeva.kidsplay.data.server.models.LoginRequest
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(
    val navigator: Navigator,
    var userPreferencesRepository: IUserPreferencesRepository,
    var achievementsDao: AchievementsDao,
    var giftsDao: GiftDao,
    var colorGameLevelDao: ColorGameLevelDao,
    var colorGameDescriptionDao: ColorGameDescriptionDao,
    var achievementDescriptionDao: AchievementDescriptionDao,
    var giftDescriptionDao: GiftDescriptionDao,
    @ApplicationContext var context: Context
) : ViewModel() {
    val parentUsername = mutableStateOf("")
    val authState = mutableStateOf(AUTH_STATE.NONE)
    val registrationState = mutableStateOf(AUTH_STATE.NONE)
    var parentsTokenForAccessToServer: MutableState<String?> = mutableStateOf(null)

    var children =
        mutableStateMapOf<String, rimma.mixeeva.kidsplay.data.server.models.GetChildInfoResponse>()
    val currentChildTokenForAccessToServer: MutableState<rimma.mixeeva.kidsplay.data.server.models.AddChildResponse?> =
        mutableStateOf(null)
    val currentOpenedChildAttributes: MutableState<rimma.mixeeva.kidsplay.data.server.models.GetChildAttributesResponse?> =
        mutableStateOf(null)
    var currentChildAchievementsList =
        mutableStateListOf<GetChildAchievementByIdResponse>()
    var currentChildGiftsList =
        mutableStateListOf<rimma.mixeeva.kidsplay.data.server.models.GetChildGiftByIdResponse>()
    var currentChildColorLevelsList =
        mutableStateListOf<rimma.mixeeva.kidsplay.data.server.models.GetChildColorLevelByIdResponse>()

    val achievementsDescription = achievementDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val colorGameDescription = colorGameDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val giftsDescription = giftDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun login(login: String, password: String) {
        viewModelScope.launch {
            authState.value = AUTH_STATE.LOADING
            try {
                val response = RetrofitInstance.instance.login(
                    LoginRequest(login, password)
                )
                if (response.isSuccessful) {
                    parentUsername.value = login
                    Log.d("SERVER", "Login was a success")
                    navigator.navigateToNextAndDeletePreviousScreen(
                        Screen.LoginScreen,
                        Screen.ChildrenScreen
                    )
                    Log.d("SERVER", "Token obtained: " + response.body()?.token)
                    parentsTokenForAccessToServer.value = response.body()?.token
                    authState.value = AUTH_STATE.SUCCESS
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while logging: " + response.code() + " " + response.message() + response.errorBody()
                    )
                    authState.value = AUTH_STATE.ERROR
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
                authState.value = AUTH_STATE.ERROR
            }
        }
    }

    fun register(login: String, email: String, password: String) {
        viewModelScope.launch {
            registrationState.value = AUTH_STATE.LOADING
            try {
                val response =
                    RetrofitInstance.instance.register(
                        rimma.mixeeva.kidsplay.data.server.models.RegistrationRequest(
                            login,
                            email,
                            password
                        )
                    )
                if (response.isSuccessful) {
                    Log.d("SERVER", "Registration was a success")
                    navigator.navigateToNextAndDeletePreviousScreen(
                        Screen.RegistrationScreen,
                        Screen.LoginScreen
                    )
                    registrationState.value = AUTH_STATE.SUCCESS
                } else {
                    Log.d("SERVER", "Error happened while registration")
                    registrationState.value = AUTH_STATE.ERROR
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
                registrationState.value = AUTH_STATE.ERROR
            }
        }
    }

    fun addChild() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).first()
            if (children.map { it.value.nickname }.firstOrNull { it == nickname } == null) {
                try {
                    val avatar =
                        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR)
                            .first()
                    Log.d("SERVER", "gonna send avatar: $avatar and nickname: $nickname")
                    if (avatar != null && nickname != null) {
                        val response =
                            RetrofitInstance.instance.addChild(
                                "Bearer " + parentsTokenForAccessToServer.value,
                                rimma.mixeeva.kidsplay.data.server.models.AddChildRequest(
                                    nickname,
                                    avatar
                                )
                            )
                        if (response.isSuccessful) {
                            Log.d("SERVER", "Adding child is a success")
                            Log.d("SERVER", "New child token: " + response.body()?.token)
                            if (response.body()?.token != null) {
                                userPreferencesRepository.setStringValue(
                                    stringPreferencesKey(nickname),
                                    response.body()!!.token
                                )//токен ребёнка
                            }
                        } else {
                            Log.d(
                                "SERVER",
                                "Error happened adding children: " + response.code() + " " + response.message() + response.errorBody()
                            )
                        }
                        currentChildTokenForAccessToServer.value = response.body()
                    } else {
                        Log.d("SERVER", "Avatar or Nickname is null")
                    }
                } catch (e: Exception) {
                    Log.d("SERVER", e.message.toString())
                }
                savePlayerAccountInfo()
                getAllParentChildren()
            } else {
                Toast.makeText(
                    context,
                    "Текущий ребёнок уже добавлен",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun savePlayerAccountInfo() {
        savePlayerAttributes()
        saveAchievement()
        saveGifts()
        saveColorGameLevels()
    }

    fun updateAllPlayerInfo() {
        updatePlayerAttributes()
        updateAllPlayerAchievements()
        updateAllPlayerGifts()
        updateAllPlayerColorGameLevels()
    }

    fun saveAchievement() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val achievements = achievementsDao.getAll().first()
            achievements.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.createAchievements(
                        "Bearer $childToken",
                        CreateUpdateAchievementRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            obtained = item.obtained
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Saving achievement is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened saving achievement: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }

    fun updateAllPlayerAchievements() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val achievements = achievementsDao.getAll().first()
            achievements.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.updateAchievements(
                        "Bearer $childToken",
                        CreateUpdateAchievementRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            obtained = item.obtained
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Updating achievement is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened updating achievement: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }

    fun saveColorGameLevels() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val colorGameLevels = colorGameLevelDao.getAll().first()
            colorGameLevels.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.createColorGameLevels(
                        "Bearer $childToken",
                        CreateUpdateColorGameLevelsRequest(
                            username = nickname,
                            levelNumber = item.levelNumber,
                            starsAchieved = item.starsAchieved,
                            isLevelOpened = item.isLevelOpened,
                            descriptionId = item.descriptionId
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Saving color game levels is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened saving color game levels: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }

    fun updateAllPlayerColorGameLevels() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val colorGameLevels = colorGameLevelDao.getAll().first()
            colorGameLevels.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.updateColorGameLevels(
                        "Bearer $childToken",
                        CreateUpdateColorGameLevelsRequest(
                            username = nickname,
                            levelNumber = item.levelNumber,
                            starsAchieved = item.starsAchieved,
                            descriptionId = item.descriptionId,
                            isLevelOpened = item.isLevelOpened,
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Updating color levels is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened updating color levels: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }

    fun saveGifts() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val gifts = giftsDao.getAll().first()
            gifts.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.createGifts(
                        "Bearer $childToken",
                        CreateUpdateGiftRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            obtained = item.obtained,
                            opened = item.opened,
                            used = item.used
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Saving gift is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened saving gift: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }

    fun updateAllPlayerGifts() {
        viewModelScope.launch {
            val nickname =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK)
                    .first()
            val childToken =
                userPreferencesRepository.getStringFlowValue(stringPreferencesKey(nickname ?: ""))
                    .first()
            val gifts = giftsDao.getAll().first()
            gifts.forEach { item ->
                if (childToken != null && nickname != null) {
                    val response = RetrofitInstance.instance.updateGifts(
                        "Bearer $childToken",
                        CreateUpdateGiftRequest(
                            username = nickname,
                            descriptionId = item.descriptionId,
                            opened = item.opened,
                            used = item.used,
                            obtained = item.obtained
                        )
                    )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Updating gifts is a success")
                        Log.d("SERVER", "Result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened updating gifts: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            }
        }
    }


    fun getAllParentChildren() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.instance.getAllChildren(parentUsername.value)
                if (response.isSuccessful) {
                    Log.d("SERVER", "Success while getting all parent children")
                    Log.d("SERVER", "Returned result: " + response.body()?.list)
                    children.clear()
                    response.body()?.list?.forEach { childUsername ->
                        val getChildResponse = getChildInfo(childUsername)
                        if (getChildResponse != null) {
                            children[childUsername] = getChildResponse
                        }
                    }
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while getting all parent children: " + response.code() + " " + response.message() + response.errorBody()
                    )
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }

        }
    }

    fun savePlayerAttributes() {
        viewModelScope.launch {
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
            try {
                if (childToken != null && nickname != null && intelligence != null && attentiveness != null && reaction != null && logic != null && coins != null) {
                    val response =
                        RetrofitInstance.instance.createAttributes(
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
                        Log.d("SERVER", "Success while saving player attributes")
                        Log.d("SERVER", "Returned result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened while saving player attributes: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    fun updatePlayerAttributes() {
        viewModelScope.launch {
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
            try {
                if (childToken != null && nickname != null && intelligence != null && attentiveness != null && reaction != null && logic != null && coins != null) {
                    val response =
                        RetrofitInstance.instance.updateAttributes(
                            "Bearer $childToken",
                            rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAttributesRequest(
                                username = nickname,
                                intelligence = intelligence,
                                attentiveness = attentiveness,
                                reaction = reaction,
                                logic = logic,
                                coins = coins
                            )
                        )
                    if (response.isSuccessful) {
                        Log.d("SERVER", "Success while updating player attributes")
                        Log.d("SERVER", "Returned result: " + response.body()?.response)
                    } else {
                        Log.d(
                            "SERVER",
                            "Error happened while updating player attributes: " + response.code() + " " + response.message() + response.errorBody()
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    suspend fun getChildInfo(childUsername: String): rimma.mixeeva.kidsplay.data.server.models.GetChildInfoResponse? {
        try {
            val response =
                RetrofitInstance.instance.getChildInfo(
                    childUsername
                )
            if (response.isSuccessful) {
                Log.d("SERVER", "Success while getting child Info")
                Log.d("SERVER", "Returned result: " + response.body())
                return response.body()
            } else {
                Log.d(
                    "SERVER",
                    "Error happened while getting child info: " + response.code() + " " + response.message() + response.errorBody()
                )
            }
        } catch (e: Exception) {
            Log.d("SERVER", e.message.toString())
        }
        return null
    }

    fun getChildAttributes(childUsername: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.instance.getChildAttributes(childUsername)
                if (response.isSuccessful) {
                    Log.d("SERVER", "Success while getting child attributes")
                    Log.d("SERVER", "Returned result: " + response.body())
                    currentOpenedChildAttributes.value = response.body()
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while getting child attributes: " + response.code() + " " + response.message() + response.errorBody()
                    )
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    fun getChildAchievements(childUsername: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.instance.getChildAchievements(childUsername)
                if (response.isSuccessful) {
                    Log.d("SERVER", "Success while getting child achievements")
                    Log.d("SERVER", "Returned result: " + response.body())
                    currentChildAchievementsList.clear()
                    response.body()?.listId?.forEach { id ->
                        getAchievementOfUserById(id)
                    }
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while getting child achievements: " + response.code() + " " + response.message() + response.errorBody()
                    )
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    suspend fun getAchievementOfUserById(id: String) {
        try {
            val response =
                RetrofitInstance.instance.getChildAchievementById(id)
            if (response.isSuccessful) {
                Log.d("SERVER", "Success while getting child achievements with ID: $id")
                Log.d("SERVER", "Returned result: " + response.body())
                response.body()?.let {
                    currentChildAchievementsList.add(it)
                }
            } else {
                Log.d(
                    "SERVER",
                    "Error happened while getting child achievement by $id: " + response.code() + " " + response.message() + response.errorBody()
                )
            }
        } catch (e: Exception) {
            Log.d("SERVER", e.message.toString())
        }
    }

    fun getChildGifts(childUsername: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.instance.getChildGifts(childUsername)
                if (response.isSuccessful) {
                    Log.d("SERVER", "Success while getting child gifts")
                    Log.d("SERVER", "Returned result: " + response.body())
                    currentChildGiftsList.clear()
                    response.body()?.listId?.forEach { id ->
                        getGiftOfUserById(id)
                    }
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while getting child gifts: " + response.code() + " " + response.message() + response.errorBody()
                    )
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    suspend fun getGiftOfUserById(id: String) {
        try {
            val response =
                RetrofitInstance.instance.getChildGiftById(id)
            if (response.isSuccessful) {
                Log.d("SERVER", "Success while getting child gift with ID: $id")
                Log.d("SERVER", "Returned result: " + response.body())
                response.body()?.let {
                    currentChildGiftsList.add(it)
                }
            } else {
                Log.d(
                    "SERVER",
                    "Error happened while getting child gift by $id: " + response.code() + " " + response.message() + response.errorBody()
                )
            }
        } catch (e: Exception) {
            Log.d("SERVER", e.message.toString())
        }
    }

    fun getChildColorGameLevels(childUsername: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.instance.getChildColorLevels(childUsername)
                if (response.isSuccessful) {
                    Log.d("SERVER", "Success while getting color game levels")
                    Log.d("SERVER", "Returned result: " + response.body())
                    currentChildColorLevelsList.clear()
                    response.body()?.listId?.forEach { id ->
                        getColorLevelOfUserById(id)
                    }
                } else {
                    Log.d(
                        "SERVER",
                        "Error happened while getting child color game levels: " + response.code() + " " + response.message() + response.errorBody()
                    )
                }
            } catch (e: Exception) {
                Log.d("SERVER", e.message.toString())
            }
        }
    }

    suspend fun getColorLevelOfUserById(id: String) {
        try {
            val response =
                RetrofitInstance.instance.getChildColorById(id)
            if (response.isSuccessful) {
                Log.d("SERVER", "Success while getting child color game level with ID: $id")
                Log.d("SERVER", "Returned result: " + response.body())
                response.body()?.let {
                    currentChildColorLevelsList.add(it)
                }
            } else {
                Log.d(
                    "SERVER",
                    "Error happened while getting child color level by $id: " + response.code() + " " + response.message() + response.errorBody()
                )
            }
        } catch (e: Exception) {
            Log.d("SERVER", e.message.toString())
        }
    }


}

enum class AUTH_STATE {
    LOADING, SUCCESS, ERROR, NONE
}