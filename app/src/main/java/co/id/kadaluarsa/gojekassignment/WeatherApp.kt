package co.id.kadaluarsa.gojekassignment

import android.app.Activity
import android.app.Application
import co.id.kadaluarsa.gojekassignment.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApp : Application(),HasActivityInjector {
    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector
    }

    companion object {
        lateinit var sInstance: WeatherApp
        fun getAppContext(): WeatherApp {
            return sInstance
        }

        @Synchronized
        private fun setInstance(app: WeatherApp) {
            sInstance = app
        }
    }

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}