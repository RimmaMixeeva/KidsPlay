package rimma.mixeeva.kidsplay.data.server


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
   private const val BASE_URL = "http://45.159.181.96:8080/"
   //private const val BASE_URL = "http://192.168.0.100:8080/"
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val instance: rimma.mixeeva.kidsplay.data.server.ServerApi by lazy {
        Retrofit.Builder()
            .baseUrl(rimma.mixeeva.kidsplay.data.server.RetrofitInstance.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(rimma.mixeeva.kidsplay.data.server.RetrofitInstance.gson)
            )
            .build()
            .create(rimma.mixeeva.kidsplay.data.server.ServerApi::class.java)

    }
}