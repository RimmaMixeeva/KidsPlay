package rimma.mixeeva.kidsplay.screens.server

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import rimma.mixeeva.kidsplay.screens.server.models.AddChildRequest
import rimma.mixeeva.kidsplay.screens.server.models.AddChildResponse
import rimma.mixeeva.kidsplay.screens.server.models.CreateAchievementRequest
import rimma.mixeeva.kidsplay.screens.server.models.CreateAchievementResponse
import rimma.mixeeva.kidsplay.screens.server.models.CreateAtributesRequest
import rimma.mixeeva.kidsplay.screens.server.models.CreateAtributesResponse
import rimma.mixeeva.kidsplay.screens.server.models.GetChildAttributesResponse
import rimma.mixeeva.kidsplay.screens.server.models.GetChildInfoResponse
import rimma.mixeeva.kidsplay.screens.server.models.GetChildrenResponse
import rimma.mixeeva.kidsplay.screens.server.models.LoginRequest
import rimma.mixeeva.kidsplay.screens.server.models.LoginResponse
import rimma.mixeeva.kidsplay.screens.server.models.RegistrationRequest
import rimma.mixeeva.kidsplay.screens.server.models.RegistrationResponse


interface ServerApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("addplayer")
    suspend fun addChild(@Header("Authorization") token: String,
                         @Body addChildRequest: AddChildRequest): Response<AddChildResponse>

    @POST("creatatributes") //сохранение данных о ребенке
    suspend fun createAttributes(@Header("Authorization") token: String,
                         @Body createAttributesRequest: CreateAtributesRequest): Response<CreateAtributesResponse>

    @POST("createachievement") //сохранение данных ачивок
    suspend fun createAchievements(@Header("Authorization") token: String,
                                 @Body createAchievementRequest: CreateAchievementRequest): Response<CreateAchievementResponse>

    @GET("getchild/{username}") //получить всех детей
    suspend fun getAllChildren(
        @Path("username") username: String,
    ): Response<GetChildrenResponse>

    @GET("players/{username}") //получить инфо по ребёнку
    suspend fun getChildInfo(
        @Path("username") username: String,
    ): Response<GetChildInfoResponse>

    @GET("attributes/{username}") //получить аттрибуты по ребёнку
    suspend fun getChildAttributes(
        @Path("username") username: String,
    ): Response<GetChildAttributesResponse>

    @POST("secure-action")
    suspend fun secureAction(@Header("Authorization") token: String,
                             @Body something: String): Response<String>

}