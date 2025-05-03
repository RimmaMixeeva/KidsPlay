package rimma.mixeeva.kidsplay

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.screens.server.LoginRequest
import rimma.mixeeva.kidsplay.screens.server.RegistrationRequest
import rimma.mixeeva.kidsplay.screens.server.RetrofitInstance
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ParentViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel() {
    val authState = mutableStateOf(AUTH_STATE.NONE)
    val registrationState = mutableStateOf(AUTH_STATE.NONE)

    fun login(login: String, password: String) {
        viewModelScope.launch {
            authState.value = AUTH_STATE.LOADING
            try {
                val response = RetrofitInstance.instance.login(LoginRequest(login, password))
                if (response.isSuccessful) {
                    authState.value = AUTH_STATE.SUCCESS
                } else {
                    authState.value = AUTH_STATE.ERROR
                }
            } catch (e: Exception) {
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
                    registrationState.value = AUTH_STATE.SUCCESS
                } else {
                    registrationState.value = AUTH_STATE.ERROR
                }
            } catch (e: Exception) {
                registrationState.value = AUTH_STATE.ERROR
            }
        }
    }
}

enum class AUTH_STATE {
    LOADING, SUCCESS, ERROR, NONE
}