package co.id.kadaluarsa.weatherapps.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.kadaluarsa.weatherapps.data.model.DataWeatherResponse
import co.id.kadaluarsa.weatherapps.data.remote.WeatherSource
import co.id.kadaluarsa.weatherapps.utils.Resource
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewmodel @Inject constructor(var source: WeatherSource) : ViewModel() {


    var weatherData: MutableLiveData<Resource<DataWeatherResponse>> = MutableLiveData()
    var dispos: Disposable? = null

    fun getWeather(location: String, days: Int, language: String) {
        dispos = source.getWeatherData(location, days, language)
            .subscribeOn(Schedulers.io())
            .subscribe({
                weatherData.postValue(Resource.Success(it))
            }, {
                weatherData.postValue(Resource.Failure(it))
                it.printStackTrace()
            })
    }

}