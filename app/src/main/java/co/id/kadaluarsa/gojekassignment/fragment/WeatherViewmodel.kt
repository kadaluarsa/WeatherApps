package co.id.kadaluarsa.gojekassignment.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.kadaluarsa.gojekassignment.data.model.DataWeatherResponse
import co.id.kadaluarsa.gojekassignment.data.remote.WeatherSource
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewmodel @Inject constructor(var source: WeatherSource) : ViewModel() {


    var weatherData: MutableLiveData<DataWeatherResponse> = MutableLiveData()
    var dispos: Disposable? = null

    fun getWeather(location: String, days: Int, language: String) {
        dispos = source.getWeatherData(location, days, language)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.isSuccessful) {
                    weatherData.postValue(it.body())
                }
            }, {
                it.printStackTrace()
            })
    }

}