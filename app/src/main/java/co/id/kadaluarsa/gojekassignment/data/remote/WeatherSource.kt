package co.id.kadaluarsa.gojekassignment.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.id.kadaluarsa.gojekassignment.BuildConfig
import co.id.kadaluarsa.gojekassignment.data.model.DataWeatherResponse
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class WeatherSource @Inject constructor(var _api: ApiService) {
    private var apiService: ApiService = _api
    fun getWeatherData(location: String, days: Int, language: String): Maybe<Response<DataWeatherResponse>> {
        return _api.getWeather(BuildConfig.ApiKey, location, days, language)
    }
}