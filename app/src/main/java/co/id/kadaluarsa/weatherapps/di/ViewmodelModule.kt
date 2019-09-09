package co.id.kadaluarsa.weatherapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.kadaluarsa.weatherapps.fragment.WeatherViewmodel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewmodelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewmodel::class)
    abstract fun bindViewModelWeather(weatherViewmodel: WeatherViewmodel):ViewModel

    @Binds
    @SuppressWarnings("unused")
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}