package ca.dal.android.assignment2_weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

val WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q="
val API_KEY_URL = "&appid=e6b678d376b569e9637c39ce5dc40371"

//https://api.openweathermap.org/data/2.5/weather?q=halifax&appid=e6b678d376b569e9637c39ce5dc40371
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener{
            val city = findViewById<EditText>(R.id.city_text_field)
            //Toast.makeText(this, city.text, Toast.LENGTH_SHORT).show()

            // Hide the keyboard.
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val weatherInfo = WeatherInfo("halifax", "clear", "overcast", 274.15, 274.15, 274.15, 86, 90)

            //getWeatherInfo(WEATHER_URL + city.text + API_KEY_URL)

            findViewById<TextView>(R.id.city_name).text = weatherInfo.city
            findViewById<TextView>(R.id.main_weather).text = weatherInfo.main
            findViewById<TextView>(R.id.description).text = weatherInfo.description
            findViewById<TextView>(R.id.temp).text = (weatherInfo.temperature - 273.15).toInt().toString() + "°C"
            findViewById<TextView>(R.id.max_temp).text = "Max Temp:  " + (weatherInfo.maxtemp - 273.15).toString() + "°C"
            findViewById<TextView>(R.id.min_temp).text = "Min Temp:  " + (weatherInfo.mintemp - 273.15).toString() + "°C"
            findViewById<TextView>(R.id.humidity_pc).text = weatherInfo.humidity.toString() + "%"
            findViewById<TextView>(R.id.cloud_pc).text = weatherInfo.clouds.toString() + "%"
        }
    }

    private fun getWeatherInfo(url: String){
        //Toast.makeText(this, "getInfo", Toast.LENGTH_LONG).show()
        Volley.newRequestQueue(this).add(
            StringRequest(Request.Method.GET, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "no error", Toast.LENGTH_LONG).show()
                    //val weatherInfo = extractWeatherInfo(JSONObject(response))
                //TODO something with weatherInfo
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Error getting data", Toast.LENGTH_LONG).show() })
        )
    }

//    private fun extractWeatherInfo(response: JSONObject): WeatherInfo{
//        return with(response) {
////            WeatherInfo(
////                getString("name"),
////                getString("main"),
////                getString("description"),
//                //getString("temp"),
//                //getString("temp_max"),
//                //getString("temp_min"),
//                //getString("humidity"),
//                //getString("all")
////            //TODO I don't think this will work with the arrays
////            )
//        }
//    }
}

