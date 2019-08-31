package co.id.kadaluarsa.gojekassignment.di

import co.id.kadaluarsa.gojekassignment.data.remote.ApiService
import co.id.kadaluarsa.gojekassignment.utils.RxRetryCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewmodelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.addInterceptor { chain ->
            val request = chain.request()
            chain.proceed(request)
        }
        return okHttpClient.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okhttpclient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.apixu.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxRetryCallAdapterFactory.create())
            .client(okhttpclient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

}