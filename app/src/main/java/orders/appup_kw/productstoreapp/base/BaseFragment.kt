package orders.appup_kw.productstoreapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {

    protected abstract val viewModel: VM

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
    }

    abstract fun getViewBinding(): VB
}
