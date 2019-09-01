package co.id.kadaluarsa.gojekassignment.utils
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import co.id.kadaluarsa.gojekassignment.WeatherApp
import co.id.kadaluarsa.gojekassignment.base.BaseFragment
import co.id.kadaluarsa.gojekassignment.di.AppProvider
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class RxRetryCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        fun create(): CallAdapter.Factory = RxRetryCallAdapterFactory()
    }

    private val originalFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val adapter = originalFactory.get(returnType, annotations, retrofit) ?: return null
        return RxRetryCallAdapter(adapter)
    }
}

class RxRetryCallAdapter<R>(private val originalAdapter: CallAdapter<R, *>) : CallAdapter<R, Any> {
    override fun adapt(call: Call<R>): Any {
        return when (val adaptedValue = originalAdapter.adapt(call)) {
            is Completable -> {
                adaptedValue.doOnError(this::sendBroadcast)
                    .retryWhen {
                        AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                            .observeOn(Schedulers.io())
                    }
            }
            is Single<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                    .retryWhen {
                        AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                            .observeOn(Schedulers.io())
                    }
            }
            is Maybe<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                    .retryWhen {
                        AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                            .observeOn(Schedulers.io())
                    }
            }
            is Observable<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                    .retryWhen {
                        AppProvider.provideRetrySubject()
                            .observeOn(Schedulers.io())
                    }
            }
            is Flowable<*> -> {
                adaptedValue.doOnError(this::sendBroadcast)
                    .retryWhen {
                        AppProvider.provideRetrySubject().toFlowable(BackpressureStrategy.LATEST)
                            .observeOn(Schedulers.io())
                    }
            }
            else -> {
                adaptedValue
            }
        }
    }

    override fun responseType(): Type = originalAdapter.responseType()


    private fun sendBroadcast(throwable: Throwable) {
        LocalBroadcastManager.getInstance(WeatherApp.getAppContext()).sendBroadcast(Intent(BaseFragment.ERROR_ACTION))
    }
}