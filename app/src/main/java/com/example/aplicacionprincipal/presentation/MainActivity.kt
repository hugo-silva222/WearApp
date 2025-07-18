/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.aplicacionprincipal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.aplicacionprincipal.R
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

import com.example.aplicacionprincipal.presentation.pantallas.*
import com.example.aplicacionprincipal.presentation.models.*
import com.example.aplicacionprincipal.presentation.oswaapp.*
import com.example.aplicacionprincipal.presentation.descanso.*
import com.example.aplicacionprincipal.presentation.musica.*

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.wear.compose.material.*
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            val viewModel: ExerciseReminderViewModel = viewModel()
            val canciones = listOf(
                TrackInfo("1", R.raw.badland, "BadLand", "Boom Kitty", R.drawable.android),
                TrackInfo("2", R.raw.speed, "At The Speed of Light", "Dimrain47", R.drawable.android),
                // Más canciones aquí
            )
            NavegacionApp(viewModel, canciones)
        }
    }
}

@Composable
fun NavegacionApp(viewModel: ExerciseReminderViewModel, songList: List<TrackInfo>){
    val navController = rememberNavController()
    var currentIndex by remember { mutableStateOf(0) }

    NavHost(navController, startDestination = "contenedor"){
        composable(route = "contenedor"){ ContenedorApps(navController)}
        composable(route = "aplicaciones"){ Aplicaciones(navController) }
        composable(route = "calculadora"){ Calculadora(navController) }
        composable(route = "caratulas"){ Caratulas(navController) }
        composable(route="caratula1"){ CaratulaUno(navController) }
        composable(route= "caratula2"){CaratulaDos(navController)}
        composable(route = "caratula3") { CaratulaTres(navController) }
        composable(route = "caratula4"){ CaratulaCuatro(navController) }
        composable(route = "caratula5"){ CaratulaCinco(navController) }
        composable(route = "caratula6"){ CaratulaSeis(navController) }
        composable(route = "caratula7"){ CaratulaSiete(navController) }
        composable(route = "caratula8"){ CaratulaOcho(navController) }
        composable(route = "caratula9"){ CaratulaNueve(navController) }
        composable(route = "caratula10"){ CaratulaDiez(navController) }
        composable( route = "scaling"){ faceWatch(navController) }
        composable( route = "nav1"){ PaginaUno(navController) }
        composable( route = "nav2"){ PaginaDos(navController) }
        composable( route = "clima"){ WearApp(navController) }
        composable("oswaApp") { Recordatorios(navController, viewModel) }
        composable("editReminder/{day}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day") ?: ""
            EditReminderScreen(navController, day, viewModel)
        }
        composable("descanso") { EyeRestScreen(navController) }
        composable("musica") {
            MusicPlayerScreen(
                initialSongIndex = currentIndex,
                songList = songList,
                onNavigateToPlaylist = { index, _ ->
                    currentIndex = index
                    navController.navigate("playlist")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("playlist") {
            PlaylistScreen(
                songList = songList,
                currentPlayingIndex = currentIndex,
                onSongSelected = { selectedIndex ->
                    currentIndex = selectedIndex
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun ContenedorApps(navController: NavController){
    var fechaHoraActual by remember { mutableStateOf(LocalDateTime.now()) }
    val formatoFecha = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss")

    // Se actualiza cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            fechaHoraActual = LocalDateTime.now()
            delay(1000)
        }
    }

    val listState = rememberScalingLazyListState()
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier =  Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ){
            item {
                Text(
                    text = fechaHoraActual.format(formatoFecha),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
            item {
                Text(
                    text = fechaHoraActual.format(formatoHora),
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate(route = "aplicaciones")
                    },
                    label = {Text(
                        text = "Aplicaciones",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.application),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            item {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "",
                    modifier = Modifier.size(size = 20.dp).padding(top = 5.dp)
                )
            }
            item {
                Text(
                    text = "© VHCS",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

@Composable
fun Aplicaciones(navController: NavController){
    val listState = rememberScalingLazyListState()
    Scaffold(
        timeText = { TimeText() },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier =  Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ){
            item {
                Text(
                    text = "Aplicaciones",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("calculadora")
                    },
                    label = {Text(
                        text = "Calculadora",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.calculator),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item{
                Chip(
                    onClick = {
                        navController.navigate("caratulas")
                    },
                    label = {Text(
                        text = "Caratulas",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("nav1")
                    },
                    label = {Text(
                        text = "nav",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.compass),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("scaling")
                    },
                    label = {Text(
                        text = "ScalingLazyColumn",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.column),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("clima")
                    },
                    label = {Text(
                        text = "WeatherApp",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.clouds_weather_cloud_4496),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("oswaApp")
                    },
                    label = {Text(
                        text = "Repeticiones",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("descanso")
                    },
                    label = {Text(
                        text = "Descanso Visual",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.view),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("musica")
                    },
                    label = {Text(
                        text = "Musica",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.musical),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("alarma")
                    },
                    label = {Text(
                        text = "Alarma",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.alarm),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Caratulas(navController: NavController){
    val listState = rememberScalingLazyListState()
    Scaffold(
        timeText = { TimeText() },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier =  Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(Color.Black)
        ){
            item {
                Text(
                    text = "Caratulas",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula1")
                    },
                    label = {Text(
                        text = "Caratula 1",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item{
                Chip(
                    onClick = {
                        navController.navigate("caratula2")
                    },
                    label = {Text(
                        text = "Caratula 2",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula3")
                    },
                    label = {Text(
                        text = "Caratula 3",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula4")
                    },
                    label = {Text(
                        text = "Caratula 4",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula5")
                    },
                    label = {Text(
                        text = "Caratula 5",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula6")
                    },
                    label = {Text(
                        text = "Caratula 6",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula7")
                    },
                    label = {Text(
                        text = "Caratula 7",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula8")
                    },
                    label = {Text(
                        text = "Caratula 8",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula9")
                    },
                    label = {Text(
                        text = "Caratula 9",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Chip(
                    onClick = {
                        navController.navigate("caratula10")
                    },
                    label = {Text(
                        text = "Caratula 10",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                    )},
                    icon = {Icon(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "",
                        modifier = Modifier.size(size = 20.dp)
                    )},
                    colors = ChipDefaults.primaryChipColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    //NavegacionApp()
}