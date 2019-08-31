package co.id.kadaluarsa.gojekassignment.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import co.id.kadaluarsa.gojekassignment.base.BaseFragment.Companion.ERROR_ACTION
import co.id.kadaluarsa.gojekassignment.di.AppProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<V : ViewModel, D : ViewDataBinding> : AppCompatActivity() {


    private var snackbar: Snackbar? = null
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: V

    protected abstract fun getViewModel(): Class<V>

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    lateinit var dataBinding: D

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        onReady()
    }

    abstract fun onReady()




}