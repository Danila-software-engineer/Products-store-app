package orders.appup_kw.productstoreapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import orders.appup_kw.productstoreapp.network.Api
import orders.appup_kw.productstoreapp.repository.ProductsRepository
import orders.appup_kw.productstoreapp.utils.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
    }


    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient.Builder): Api {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
            .create(Api::class.java)
    }


    @Provides
    @Singleton
    fun provideProductsRepository(): ProductsRepository {
        return ProductsRepository()
    }
}