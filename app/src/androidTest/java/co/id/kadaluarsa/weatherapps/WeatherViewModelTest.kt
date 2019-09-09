package co.id.kadaluarsa.weatherapps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.id.kadaluarsa.weatherapps.data.model.DataWeatherResponse
import co.id.kadaluarsa.weatherapps.data.remote.ApiService
import co.id.kadaluarsa.weatherapps.data.remote.WeatherSource
import co.id.kadaluarsa.weatherapps.fragment.WeatherViewmodel
import co.id.kadaluarsa.weatherapps.utils.Resource
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoJUnit


@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var verificationCollector = MockitoJUnit.collector()

    private lateinit var weatherRepository: WeatherSource
    @Mock
    private lateinit var apiservice: ApiService
    private lateinit var weatherViewModel: WeatherViewmodel
    @Mock
    private lateinit var observer: Observer<Resource<DataWeatherResponse>>

    val days = 5
    val language = "en"
    val searchedCityName = "Bangalore"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherRepository = WeatherSource(apiservice)
        weatherViewModel = WeatherViewmodel(weatherRepository)
        weatherViewModel.weatherData.observeForever(observer)
    }

    /**
     *
     */
    @Test
    fun getResponseSuccess() {
        val data = mock(DataWeatherResponse::class.java)
        `when`(
            weatherRepository.getWeatherData(
                searchedCityName,
                days,
                language
            )
        ).thenReturn(Single.just(data))
        weatherViewModel.getWeather(searchedCityName, days, language)
        assert(weatherViewModel.weatherData.value == Resource.Success(data))
    }

    @Test
    fun getResponse_whenthrow() {
        val throwable = Throwable("Api Error")
        `when`(weatherRepository.getWeatherData(searchedCityName, days, language)).thenReturn(
            Single.error(throwable)
        )
        weatherViewModel.getWeather(searchedCityName, days, language)
        verify(observer).onChanged(Resource.Failure(throwable))
    }

}