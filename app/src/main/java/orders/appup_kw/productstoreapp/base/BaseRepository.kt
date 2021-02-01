package orders.appup_kw.productstoreapp.base

import orders.appup_kw.productstoreapp.applifecycle.App
import orders.appup_kw.productstoreapp.network.Api
import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var api: Api

    init {
        App.getAppComponent().inject(this)
    }
}