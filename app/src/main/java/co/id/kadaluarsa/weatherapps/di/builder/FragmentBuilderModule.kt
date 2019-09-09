package co.id.kadaluarsa.weatherapps.di.builder

import co.id.kadaluarsa.weatherapps.fragment.WeatherFrag
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragmet(): WeatherFrag


}
