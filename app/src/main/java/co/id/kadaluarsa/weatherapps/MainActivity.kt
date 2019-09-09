package co.id.kadaluarsa.weatherapps

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.id.kadaluarsa.weatherapps.base.BaseActivity
import co.id.kadaluarsa.weatherapps.databinding.ActivityMainBinding
import co.id.kadaluarsa.weatherapps.fragment.WeatherFrag
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentAndroidInjector
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onReady() {
        Dexter.withActivity(this@MainActivity)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                @Override
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    try {
                        if (report.areAllPermissionsGranted()) {
                            setFragmentToActivity(supportFragmentManager, WeatherFrag())
                        }
                    } catch (ex: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.request_permission_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        ex.printStackTrace()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.request_permission_mandatory), Toast.LENGTH_SHORT
                        ).show()
                        openSetting()
                    }
                }
            }).check()

    }

    private fun openSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
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