package co.id.kadaluarsa.weatherapps.data.remote

import co.id.kadaluarsa.weatherapps.data.model.DataWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    fun getWeather(
        @Query("key") apikey : String,
        @Query("q") location: String,
        @Query("days") days: Int?,
        @Query("lang") languageCode: String
    ): Single<DataWeatherResponse>

}