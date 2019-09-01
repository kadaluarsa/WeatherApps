package co.id.kadaluarsa.gojekassignment.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.kadaluarsa.gojekassignment.R
import co.id.kadaluarsa.gojekassignment.adapter.WeatherAdapter
import co.id.kadaluarsa.gojekassignment.base.BaseFragment
import co.id.kadaluarsa.gojekassignment.data.model.Forecastday
import co.id.kadaluarsa.gojekassignment.databinding.FragmentWeatherBinding
import co.id.kadaluarsa.gojekassignment.utils.Resource
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
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
        return co.id.kadaluarsa.gojekassignment.R.layout.fragment_weather
    }

    override fun getViewModel(): Class<WeatherViewmodel> {
        return WeatherViewmodel::class.java
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rcanimation =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_anim_frombottom)
        val rxLocation = RxLocation(requireContext())
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(300000)

        rxLocation.location().updates(locationRequest)
            .flatMap { location -> rxLocation.geocoding().fromLocation(location).toObservable() }
            .subscribe({ address ->
                viewModel.getWeather(address.locality, 5, "en")
            }, {
                it.printStackTrace()
            })

        viewModel.weatherData.observe(this, Observer { data ->
            when (data) {
                is Resource.Success -> {
                    onSuccess()
                    dataBinding.listWeather.apply {
                        layoutManager =
                            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        data.data?.let {
                            adapter = WeatherAdapter(
                                it.location.country,
                                it.forecast.forecastday as MutableList<Forecastday>
                            )
                        }
                        layoutAnimation = rcanimation
                    }
                }
                is Resource.Loading -> {
                    onLoading()
                }
            }
        })
    }


}