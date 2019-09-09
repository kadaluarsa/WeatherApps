package co.id.kadaluarsa.weatherapps.adapter

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kadaluarsa.weatherapps.data.model.Forecastday
import co.id.kadaluarsa.weatherapps.databinding.RowChildWeatherBinding
import co.id.kadaluarsa.weatherapps.databinding.RowHeaderWeatherBinding
import java.text.SimpleDateFormat
import kotlin.math.ceil

class WeatherAdapter(var city: String, var list: MutableList<Forecastday>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val ITEM_HEADER = 0
        private const val ITEM_CHILD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_HEADER) {
            val head =
                RowHeaderWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return WeatherHeaderHolder(head)
        } else {
            val child =
                RowChildWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return WeatherChildHolder(child)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            ITEM_HEADER
        } else {
            ITEM_CHILD
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherHeaderHolder -> {
                holder.bindData(list[position])
            }
            is WeatherChildHolder -> {
                holder.bindData(list[position])
            }
        }
    }

    inner class WeatherHeaderHolder(var _binding: RowHeaderWeatherBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(forecast: Forecastday) {
            _binding.txtCity.text = city
            _binding.txtDegree.text = "${ceil(forecast.day.avgtemp_c).toInt()}${0x00B0.toChar()}"
        }
    }


    inner class WeatherChildHolder(var _binding: RowChildWeatherBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bindData(forecast: Forecastday) {
            val day = DateFormat.format("EEEE", SimpleDateFormat("yyyy-mm-dd").parse(forecast.date))
            _binding.txtDay.text = day
            _binding.txtDegree.text = "${ceil(forecast.day.avgtemp_c).toInt()}C"
        }
    }
}