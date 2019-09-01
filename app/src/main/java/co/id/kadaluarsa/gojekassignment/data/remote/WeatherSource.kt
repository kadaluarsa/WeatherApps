package co.id.kadaluarsa.gojekassignment.data.remote

import co.id.kadaluarsa.gojekassignment.BuildConfig
import co.id.kadaluarsa.gojekassignment.data.model.DataWeatherResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherSource @Inject constructor(var _api: ApiService) {
    fun getWeatherData(location: String, days: Int, language: String): Single<DataWeatherResponse> {
        return _api.getWeather(BuildConfig.ApiKey, location, days, language)
    }
}