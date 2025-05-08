package rimma.mixeeva.kidsplay

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.data.IUserPreferencesRepository
import rimma.mixeeva.kidsplay.data.UserPreferencesKeys
import rimma.mixeeva.kidsplay.data.database.dao.AchievementsDao
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.server.models.LoginRequest
import rimma.mixeeva.kidsplay.screens.server.models.RegistrationRequest
import rimma.mixeeva.kidsplay.screens.server.RetrofitInstance
import rimma.mixeeva.kidsplay.screens.server.models.AddChildRequest
import rimma.mixeeva.kidsplay.screens.server.models.AddChildResponse
import rimma.mixeeva.kidsplay.screens.server.models.CreateAchievementRequest
import rimma.mixeeva.kidsplay.screens.server.models.CreateAtributesRequest
import rimma.mixeeva.kidsplay.screens.server.models.GetChildAttributesResponse
import rimma.mixeeva.kidsplay.screens.server.models.GetChildInfoResponse
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(
    val navigator: Navigator,
    var userPreferencesRepository: IUserPreferencesRepository,
    var achievementsDao: AchievementsDao,
    @ApplicationContext var context: Context
) : ViewModel() {
    val parentUsername = mutableStateOf("")
    val authState = mutableStateOf(AUTH_STATE.NONE)
    val registrationState = mutableStateOf(AUTH_STATE.NONE)
    var parentsTokenForAccessToServer: MutableState<String?> = mutableStateOf(null)

    var children = mutableStateMapOf<String, GetChildInfoResponse>()
    val currentChildTokenForAccessToServer: MutableState<AddChildResponse?> = mutableStateOf(null)
    val currentOpenedChildAttributes: MutableState<GetChildAttributesResponse?> = mutableStateOf(null)

    fun login(login: String, password: String) {
        viewModelScope.launch {
            authState.value = AUTH_STATE.LOADING
            try {
                val response = RetrofitInstance.instance.login(LoginRequest(login, password))
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
                    RetrofitInstance.instance.register(RegistrationRequest(login, email, password))
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
            val nickname = userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).first()
           // if (children.map { it.value.nickname }.firstOrNull { it == nickname } == null) {
                try {
                    val avatar =
                        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR)
                            .first()
                    Log.d("SERVER", "gonna send avatar: $avatar and nickname: $nickname")
                    if (avatar != null && nickname != null) {
                        val response =
                            RetrofitInstance.instance.addChild(
                                "Bearer " + parentsTokenForAccessToServer.value,
                                AddChildRequest(nickname, avatar)
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
//            } else {
//                Toast.makeText(
//                    context,
//                    "Текущий ребёнок уже добавлен",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }
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
                        CreateAchievementRequest(
                            nickname,
                            item.title,
                            item.condition,
                            item.description,
                            item.obtained
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

    fun savePlayerAccountInfo() {
        savePlayerAttributes()
        saveAchievement()
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
                            CreateAtributesRequest(
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

    suspend fun getChildInfo(childUsername: String): GetChildInfoResponse? {
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

    fun getChildAttributes(childUsername: String){
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
}

enum class AUTH_STATE {
    LOADING, SUCCESS, ERROR, NONE
}