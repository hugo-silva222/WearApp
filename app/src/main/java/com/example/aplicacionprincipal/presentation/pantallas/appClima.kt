package com.example.aplicacionprincipal.presentation.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.aplicacionprincipal.presentation.models.WeatherResponse

import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.aplicacionprincipal.R


interface WeatherApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 10,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherResponse
}


object WeatherService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: WeatherApi = retrofit.create(WeatherApi::class.java)
}


@Composable
fun WearApp(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var weather by remember { mutableStateOf<WeatherResponse?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                weather = WeatherService.api.getForecast(
                    apiKey = "37524039c866405f80f20338250607", // Tu API key de WeatherAPI.com
                    location = "Mexico City"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                weather = null
            }
        }
    }

    if (weather == null) {
        Text(
            text = "Cargando...",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    } else {
        WeatherScreen(weather!!, navController)
    }
}

@Composable
fun WeatherScreen(weather: WeatherResponse, navController: NavHostController) {
    val forecastList = weather.forecast.forecastday.take(10)
    val today = forecastList.first()
    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = { TimeText() },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier =  Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.cloud),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            item {
                Text(
                    text = "Clima en ${weather.location.name}, \n ${weather.location.country}",
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
            // ☀️ Clima de hoy (primer día del pronóstico)
            item {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(120.dp),
                    onClick = {}
                ){
                    Text(
                        text = "Clima hoy: ${today.day.mintemp_c}° / ${today.day.maxtemp_c}° - ${today.day.condition.text}",
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
            }
            item {
                Text(
                    text = "Pronostico de 10 dias",
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                )
            }
            // 📅 LazyRow con el pronóstico de los próximos días (incluye hoy también si quieres)
            item {
                LazyRow(
                    modifier = Modifier.fillMaxSize().padding(5.dp),
                    horizontalArrangement = Arrangement.Center
                    ) {
                    items(forecastList) { day ->
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(110.dp),
                            onClick = {}
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = day.date,
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace,
                                )
                                Text(
                                    text = "${day.day.mintemp_c}° / ${day.day.maxtemp_c}°",
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace,
                                )
                                Text(
                                    text = day.day.condition.text,
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
