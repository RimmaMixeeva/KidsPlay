package rimma.mixeeva.kidsplay.screens.server

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ServerApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>
}