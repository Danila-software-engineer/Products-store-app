package orders.appup_kw.productstoreapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    val successLiveData = MutableLiveData<Any>()
    val errorLiveData = MutableLiveData<Throwable>()

    val compositeDisposable = CompositeDisposable()

    fun destroy(){
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}