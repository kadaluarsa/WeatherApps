package co.id.kadaluarsa.gojekassignment

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.id.kadaluarsa.gojekassignment.base.BaseActivity
import co.id.kadaluarsa.gojekassignment.databinding.ActivityMainBinding
import co.id.kadaluarsa.gojekassignment.fragment.WeatherFrag
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentAndroidInjector
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onReady() {
        setFragmentToActivity(supportFragmentManager,WeatherFrag())
    }

    private fun setFragmentToActivity(
        @NonNull fragmentManager: FragmentManager,
        @NonNull fragment: Fragment
    ) {
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}