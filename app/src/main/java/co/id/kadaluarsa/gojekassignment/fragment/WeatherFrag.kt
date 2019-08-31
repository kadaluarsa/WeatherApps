package co.id.kadaluarsa.gojekassignment.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import co.id.kadaluarsa.gojekassignment.R
import co.id.kadaluarsa.gojekassignment.base.BaseFragment
import co.id.kadaluarsa.gojekassignment.databinding.FragmentWeatherBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.view.*
import kotlinx.android.synthetic.main.view_error.view.*

class WeatherFrag : BaseFragment<WeatherViewmodel, FragmentWeatherBinding>() {
    override fun showError(message: String) {
        onError(message)
        dataBinding.viewError.btnRetry.setOnClickListener {
            onLoading()
            retryNetwork()
        }
    }

    private fun onError(message: String) {
        with(dataBinding) {
            viewError.viewError.visibility = View.VISIBLE
            viewError.viewError.txtErrorMsg.text = message
            viewLoading.viewLoading.visibility = View.GONE
            viewLoading.imgLoading.clearAnimation()
            viewData.visibility = View.GONE
        }
    }

    private fun onLoading() {
        with(dataBinding) {
            viewError.viewError.visibility = View.GONE
            viewLoading.viewLoading.visibility = View.VISIBLE
            val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            rotation.repeatCount = Animation.INFINITE
            viewLoading.imgLoading.startAnimation(rotation)
            viewData.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        with(dataBinding) {
            viewError.viewError.visibility = View.GONE
            viewLoading.viewLoading.visibility = View.GONE
            viewLoading.imgLoading.clearAnimation()
            viewData.visibility = View.VISIBLE
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_weather
    }

    override fun getViewModel(): Class<WeatherViewmodel> {
        return WeatherViewmodel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeather("-6.8973254%2C107.6260937", 4, "en")
        viewModel.weatherData.observe(this, Observer {
            onSuccess()
        })
    }
}