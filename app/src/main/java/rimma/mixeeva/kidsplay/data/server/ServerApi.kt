package rimma.mixeeva.kidsplay.data.server

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import rimma.mixeeva.kidsplay.data.server.models.AddChildRequest
import rimma.mixeeva.kidsplay.data.server.models.AddChildResponse
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAchievementRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAchievementResponse
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAttributesRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateAttributesResponse
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateColorGameLevelsRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateColorGameLevelsResponse
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateGiftRequest
import rimma.mixeeva.kidsplay.data.server.models.CreateUpdateGiftResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildAchievementByIdResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildAchievementsResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildAttributesResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildColorLevelByIdResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildGiftByIdResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildGiftsResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildInfoResponse
import rimma.mixeeva.kidsplay.data.server.models.GetChildrenResponse
import rimma.mixeeva.kidsplay.data.server.models.GetColorLevelsResponse
import rimma.mixeeva.kidsplay.data.server.models.LoginRequest
import rimma.mixeeva.kidsplay.data.server.models.LoginResponse
import rimma.mixeeva.kidsplay.data.server.models.RegistrationRequest
import rimma.mixeeva.kidsplay.data.server.models.RegistrationResponse


interface ServerApi {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("addplayer")
    suspend fun addChild(
        @Header("Authorization") token: String,
        @Body addChildRequest: AddChildRequest
    ): Response<AddChildResponse>

    @POST("creatatributes") //сохранение данных о ребенке
    suspend fun createAttributes(
        @Header("Authorization") token: String,
        @Body createAttributesRequest: CreateUpdateAttributesRequest
    ): Response<CreateUpdateAttributesResponse>

    @POST("update/attribute") //обновление атрибутов
    suspend fun updateAttributes(
        @Header("Authorization") token: String,
        @Body createGiftRequest: CreateUpdateAttributesRequest
    ): Response<CreateUpdateAttributesResponse>



    @POST("createachievement") //сохранение данных ачивок
    suspend fun createAchievements(
        @Header("Authorization") token: String,
        @Body createAchievementRequest: CreateUpdateAchievementRequest
    ): Response<CreateUpdateAchievementResponse>


    @POST("update/achievement") //обновление данных ачивок
    suspend fun updateAchievements(
        @Header("Authorization") token: String,
        @Body updateAchievementRequest: CreateUpdateAchievementRequest
    ): Response<CreateUpdateAchievementResponse>

    @POST("creategift") //сохранение данных наград
    suspend fun createGifts(
        @Header("Authorization") token: String,
        @Body createGiftRequest: CreateUpdateGiftRequest
    ): Response<CreateUpdateGiftResponse>

    @POST("update/gift") //обновление данных наград
    suspend fun updateGifts(
        @Header("Authorization") token: String,
        @Body createGiftRequest: CreateUpdateGiftRequest
    ): Response<CreateUpdateGiftResponse>

    @POST("createcolorgamelevel") //сохранение данных игры в цвета
    suspend fun createColorGameLevels(
        @Header("Authorization") token: String,
        @Body createColorGameLevels: CreateUpdateColorGameLevelsRequest
    ): Response<CreateUpdateColorGameLevelsResponse>

    @POST("update/colorgamelevel") //обновление данных игры в цвета
    suspend fun updateColorGameLevels(
        @Header("Authorization") token: String,
        @Body createColorGameLevels: CreateUpdateColorGameLevelsRequest
    ): Response<CreateUpdateColorGameLevelsResponse>


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

    @GET("achivelist/{username}") //получить ачивки по ребёнку
    suspend fun getChildAchievements(
        @Path("username") username: String,
    ): Response<GetChildAchievementsResponse>

    @GET("giftlist/{username}") //получить подарки по ребёнку
    suspend fun getChildGifts(
        @Path("username") username: String,
    ): Response<GetChildGiftsResponse>

    @GET("colorlevellist/{username}") //получить уровни игры в цвета
    suspend fun getChildColorLevels(
        @Path("username") username: String,
    ): Response<GetColorLevelsResponse>

    @GET("achive/{achiveId}") //получить ачивку по айди
    suspend fun getChildAchievementById(
        @Path("achiveId") achiveId: String,
    ): Response<GetChildAchievementByIdResponse>

    @GET("gift/{giftId}") //получить ачивку по айди
    suspend fun getChildGiftById(
        @Path("giftId") giftId: String,
    ): Response<GetChildGiftByIdResponse>

    @GET("colorlevel/{colorId}") //получить уровни игры в цвета по айди
    suspend fun getChildColorById(
        @Path("colorId") colorId: String,
    ): Response<GetChildColorLevelByIdResponse>

    @POST("secure-action")
    suspend fun secureAction(
        @Header("Authorization") token: String,
        @Body something: String
    ): Response<String>

}