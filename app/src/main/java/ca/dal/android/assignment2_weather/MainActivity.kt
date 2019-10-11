package ca.dal.android.assignment2_weather

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject
//TODO readme file, shared preferences, block horizontal view
//TODO take care of layout warnings
//TODO easier city input
//TODO error handling

const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q="
const val API_KEY_URL = "&appid=e6b678d376b569e9637c39ce5dc40371"
const val PREV_CITY = "previous_city"
const val SHAREDPREF_NAME = "weatherAppPref"

//https://api.openweathermap.org/data/2.5/weather?q=halifax&appid=e6b678d376b569e9637c39ce5dc40371
class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)
        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener{
            var city = findViewById<EditText>(R.id.city_text_field).text
            //var city2 = city.toString().replace(" ", "+")
            //Toast.makeText(this, city2, Toast.LENGTH_LONG).show()
            //TODO clean this city text somehow
            // san+francisco

            // Hide the keyboard.
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            getWeatherInfo(WEATHER_URL + city + API_KEY_URL)
        }
    }

    private fun getWeatherInfo(url: String){
        Volley.newRequestQueue(this).add(
            StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    val weatherInfo = extractWeatherInfo(JSONObject(response))
                    assignWeatherInfo(weatherInfo)
                },
                Response.ErrorListener {
                    findViewById<FrameLayout>(R.id.weather_frame).visibility = View.INVISIBLE
                    findViewById<FrameLayout>(R.id.error_frame).visibility = View.VISIBLE
                })
        )
    }

    private fun extractWeatherInfo(response: JSONObject): WeatherInfo{
        return with(response) {
            WeatherInfo(
                getString("name"),
                getJSONArray("weather").getJSONObject(0).getString("main"),
                getJSONArray("weather").getJSONObject(0).getString("description"),
                getJSONObject("main").getString("temp").toDouble(),
                getJSONObject("main").getString("temp_max").toDouble(),
                getJSONObject("main").getString("temp_min").toDouble(),
                getJSONObject("main").getString("humidity").toInt(),
                getJSONObject("clouds").getString("all").toInt()
            )
        }
    }

    private fun assignWeatherInfo(weatherInfo: WeatherInfo){
        findViewById<TextView>(R.id.city_name).text = weatherInfo.city
        findViewById<TextView>(R.id.main_weather).text = weatherInfo.main
        findViewById<TextView>(R.id.description).text = weatherInfo.description
        findViewById<TextView>(R.id.temp).text = (weatherInfo.temperature - 273.15).toInt().toString() + "°C"
        findViewById<TextView>(R.id.max_temp).text = "Max Temp:  " + (weatherInfo.maxtemp - 273.15).toInt().toString() + "°C"
        findViewById<TextView>(R.id.min_temp).text = "Min Temp:  " + (weatherInfo.mintemp - 273.15).toInt().toString() + "°C"
        findViewById<TextView>(R.id.humidity_pc).text = weatherInfo.humidity.toString() + "%"
        findViewById<TextView>(R.id.cloud_pc).text = weatherInfo.clouds.toString() + "%"
        findViewById<FrameLayout>(R.id.weather_frame).visibility = View.VISIBLE
        findViewById<FrameLayout>(R.id.error_frame).visibility = View.INVISIBLE
    }

    override fun onStop() {
        super.onStop()
        with(sharedPreferences.edit()){
            putString(PREV_CITY, findViewById<TextView>(R.id.city_name).text.toString())
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        if(sharedPreferences.getString(PREV_CITY, "") != "")
        getWeatherInfo(WEATHER_URL + sharedPreferences.getString(PREV_CITY, "") + API_KEY_URL)
    }
}

