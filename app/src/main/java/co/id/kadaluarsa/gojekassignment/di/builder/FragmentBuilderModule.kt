package co.id.kadaluarsa.gojekassignment.di.builder

import co.id.kadaluarsa.gojekassignment.fragment.WeatherFrag
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragmet(): WeatherFrag


}
