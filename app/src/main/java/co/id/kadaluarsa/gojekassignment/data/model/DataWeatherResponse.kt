package co.id.kadaluarsa.gojekassignment.data.model

data class DataWeatherResponse(
    val alert: Alert,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class Forecast(
    val forecastday: List<Forecastday>
)

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day
)

data class Astro(
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

data class Day(
    val avghumidity: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val condition: ConditionX,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val uv: Double
)

data class ConditionX(
    val code: Int,
    val icon: String,
    val text: String
)

class Alert(
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)