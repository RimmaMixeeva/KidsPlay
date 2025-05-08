package rimma.mixeeva.kidsplay.screens.server


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
   private const val BASE_URL = "http://45.159.181.96:8080/"
   //private const val BASE_URL = "http://192.168.0.100:8080/"
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val instance: ServerApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .build()
            .create(ServerApi::class.java)

    }
}