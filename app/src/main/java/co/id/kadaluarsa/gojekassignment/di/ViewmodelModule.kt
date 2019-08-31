package co.id.kadaluarsa.gojekassignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.id.kadaluarsa.gojekassignment.MainViewModel
import co.id.kadaluarsa.gojekassignment.fragment.WeatherViewmodel
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
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModelMain(mainViewModel: MainViewModel):ViewModel


    @Binds
    @SuppressWarnings("unused")
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}