package co.id.kadaluarsa.weatherapps.di

import android.app.Application
import co.id.kadaluarsa.weatherapps.WeatherApp
import co.id.kadaluarsa.weatherapps.di.builder.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * The main application component which initializes all the dependent modules
 * Some class will generated after build > make project ^__^
 * */

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class]
)

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(weatherapp: WeatherApp)
}