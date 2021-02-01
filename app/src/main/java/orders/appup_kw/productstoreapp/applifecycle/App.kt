package orders.appup_kw.productstoreapp.applifecycle

import android.app.Application
import orders.appup_kw.productstoreapp.di.AppComponent
import orders.appup_kw.productstoreapp.di.DaggerAppComponent
import orders.appup_kw.productstoreapp.di.NetworkModule

public class App : Application() {
    companion object {
        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }
    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
    }



    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .networkModule(NetworkModule)
            .build()
    }
}