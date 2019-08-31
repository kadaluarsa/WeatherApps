package co.id.kadaluarsa.gojekassignment.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import co.id.kadaluarsa.gojekassignment.BuildConfig
import co.id.kadaluarsa.gojekassignment.di.AppProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<V : ViewModel, D : ViewDataBinding> : Fragment() {


    companion object {
        const val ERROR_ACTION = "${BuildConfig.APPLICATION_ID}.action.ERROR"
    }

    abstract fun showError(message: String)

    protected lateinit var dataBinding: D

    @LayoutRes
    protected abstract fun getLayoutRes(): Int


    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V
    protected abstract fun getViewModel(): Class<V>

    private var onRetry : Boolean = false

    private val chainErrorReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ERROR_ACTION) {
                if(!onRetry){
                    onRetry = true
                    showError("Something went wrong at our end!")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ERROR_ACTION)
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(chainErrorReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(chainErrorReceiver)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(getViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return dataBinding.root
    }


    fun retryNetwork() {
        onRetry = false
        AppProvider.provideRetrySubject().onNext(Unit)
    }


}