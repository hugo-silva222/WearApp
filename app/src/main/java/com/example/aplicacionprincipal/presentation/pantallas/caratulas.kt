package com.example.aplicacionprincipal.presentation.pantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.tooling.preview.devices.WearDevices
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import com.example.aplicacionprincipal.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.rememberScalingLazyListState
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.concurrent.timer
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun CaratulaUno(navController: NavController){
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(12.dp)
    ){
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Power Yoga",
                    fontSize = 12.sp,
                    color = Color.Yellow,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            item {
                Chip(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text(text = "Start", modifier = Modifier
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center, color = Color.Black, fontSize = 15.sp,)},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.Yellow
                    )
                )
            }
            item {
                Text(
                    text = "Last Session 45m",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}

@Composable
fun CaratulaDos(navController: NavController){
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(12.dp)
    ){
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "1 run this week",
                    fontSize = 12.sp,
                    color = Color.Yellow,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            item {
                contenidoCaratula2(navController)
            }
            item {
                Chip(
                    onClick = {

                    },
                    modifier = Modifier.size(width = 50.dp, height = 30.dp).padding(top = 10.dp),
                    label = {Text(text = "More", modifier = Modifier
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center, color = Color.Black, fontSize = 8.sp,)},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = Color.Gray
                    )
                )
            }
        }
    }
}

@Composable
fun contenidoCaratula2(navController: NavController){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp
            , Alignment.CenterHorizontally)
    ){
        item {
            Card(
                onClick = {

                },
                modifier = Modifier.size(50.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.run),
                    contentDescription = "Correr",
                    modifier = Modifier.fillMaxSize()
                    //.clip(CircleShape).background(Color.Yellow)
                )
            }
        }
        item {
            Card(
                onClick = {

                },
                modifier = Modifier.size(50.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.stretch),
                    contentDescription = "Estirarse",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        item {
            Card(
                onClick = {},
                modifier = Modifier.size(50.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.bicycle),
                    contentDescription = "Bicicleta",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun CaratulaTres(navController: NavController){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 12.dp,
            indicatorColor = Color.Black
        )
        CircularProgressIndicator(
            progress = 0.82f,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 12.dp,
            indicatorColor = Color.Yellow
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Steps",
                fontSize = 12.sp,
                color = Color.Yellow,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "6562",
                fontSize = 32.sp,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "/8000",
                fontSize = 12.sp,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(10.dp)
            )
            Chip(
                onClick = {

                },
                modifier = Modifier.size(width = 50.dp, height = 30.dp).padding(top = 10.dp),
                label = {Text(text = "Back", modifier = Modifier
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center, color = Color.Black, fontSize = 8.sp,)},
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun CaratulaCuatro(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        RelojAnalogico()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(35.dp).align(Alignment.BottomCenter)
                .offset(y = (-40).dp)
        ) {
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 2.dp,
                indicatorColor = Color.Black
            )
            CircularProgressIndicator(
                progress = 0.82f,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 2.dp,
                indicatorColor = Color.Yellow
            )
            // Botón de zapato
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.walk),
                    contentDescription = "Pasos",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun RelojAnalogico() {
    val calendar = remember { Calendar.getInstance() }
    val timeState = remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            timeState.value = Calendar.getInstance()
            delay(1000L)
        }
    }

    Canvas(modifier = Modifier.size(200.dp)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val center = Offset(centerX, centerY)
        val radius = size.minDimension / 2

        val hour = timeState.value.get(Calendar.HOUR)
        val minute = timeState.value.get(Calendar.MINUTE)
        val second = timeState.value.get(Calendar.SECOND)

        // Fondo
        drawCircle(color = Color.Black)

        // ⏱️ DIBUJAR MARCAS DE LAS HORAS
        for (i in 0 until 12) {
            val angleDegrees = i * 30f - 90f
            val angleRadians = Math.toRadians(angleDegrees.toDouble())

            val outer = Offset(
                x = center.x + cos(angleRadians).toFloat() * radius,
                y = center.y + sin(angleRadians).toFloat() * radius
            )
            val inner = Offset(
                x = center.x + cos(angleRadians).toFloat() * (radius - 12),
                y = center.y + sin(angleRadians).toFloat() * (radius - 12)
            )

            drawLine(
                color = Color.White,
                start = inner,
                end = outer,
                strokeWidth = 2f
            )
        }

        // Manecilla de hora
        val hourAngle = (hour + minute / 60f) * 30f
        drawLine(
            color = Color.Yellow,
            start = Offset(centerX, centerY),
            end = Offset(
                x = centerX + 40 * cos(Math.toRadians(hourAngle - 90.0)).toFloat(),
                y = centerY + 40 * sin(Math.toRadians(hourAngle - 90.0)).toFloat()
            ),
            strokeWidth = 6f
        )

        // Manecilla de minutos
        val minuteAngle = (minute + second / 60f) * 6f
        drawLine(
            color = Color.White,
            start = Offset(centerX, centerY),
            end = Offset(
                x = centerX + 60 * cos(Math.toRadians(minuteAngle - 90.0)).toFloat(),
                y = centerY + 60 * sin(Math.toRadians(minuteAngle - 90.0)).toFloat()
            ),
            strokeWidth = 4f
        )

        // Manecilla de segundos
        val secondAngle = second * 6f
        drawLine(
            color = Color.Yellow,
            start = Offset(centerX, centerY),
            end = Offset(
                x = centerX + 70 * cos(Math.toRadians(secondAngle - 90.0)).toFloat(),
                y = centerY + 70 * sin(Math.toRadians(secondAngle - 90.0)).toFloat()
            ),
            strokeWidth = 2f
        )

        // Punto central
        drawCircle(color = Color.White, radius = 6f, center = Offset(centerX, centerY))
    }
}

@Composable
fun CaratulaCinco(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.run),
            contentDescription = "",
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "7:21 / mi",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = "Running",
            fontSize = 12.sp,
            color = Color.Yellow,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}

@Composable
fun CaratulaSeis(navController: NavController){
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
                    text = "Great job!",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            item {
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Active time",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.timer),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "45:23",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                }
            }
            item {
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Distance",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ruler),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "20.6km",
                            fontSize = 15.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CaratulaSiete(navController: NavController){
    Scaffold(
        timeText = { TimeText() },
        modifier = Modifier.fillMaxSize()
    ){
        Row(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Max Spd",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "46.5",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "mph",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Distance",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "21.8",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "mile",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun CaratulaOcho(navController: NavController){
    val listState = rememberScalingLazyListState()
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) },
        modifier =  Modifier.fillMaxSize()
    ){
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(Color.Black).padding(15.dp)
        ){
            item {
                Icon(
                    painter = painterResource(id = R.drawable.peloton),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }
            item {
                Text(
                    text = "Peloton App: \n Start a workout on your \n mobile device.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
            item {
                Text(
                    text = "Peloton equipment",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
            item {
                Text(
                    text = "✔ Connected",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CaratulaNueve(navController: NavController) {
    Scaffold(
        timeText = { TimeText() },
        modifier =  Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxSize()
                .background(
                    Brush.radialGradient( // fondo tipo degradado
                        colors = listOf(Color.Black, Color(0xFF800080)),
                        center = Offset(240f, 240f),
                        radius = 1000f
                    )),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                // Canción
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Chef Table Radio",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Eli Kulp",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }

                // Controles
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
                ) {
                    IconButton(onClick = { /* rewind */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(onClick = { /* play/pause */ }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(64.dp)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Color.Magenta, Color.Transparent),
                                        radius = 100f
                                    ),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    IconButton(onClick = { /* forward */ }) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                // Parte inferior: favorito y volumen
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { /* favorite */ }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* volume */ }) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Volume",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CaratulaDiez(navController: NavController){
    Scaffold(
        timeText = { TimeText() },
        modifier = Modifier.fillMaxSize()
    ){
       ScalingLazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .background(Color.Black)
               .padding(top = 5.dp),
           horizontalAlignment = Alignment.CenterHorizontally
       ){
           item {
               Row(
                   horizontalArrangement = Arrangement.Center,
                   //verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
               ){
                   Box(
                       modifier = Modifier.size(50.dp)
                   ){
                       Image(
                           painter = painterResource(id = R.drawable.perfil), // tu imagen de avatar
                           contentDescription = "Profile Picture",
                           modifier = Modifier
                               .size(50.dp)
                               .clip(CircleShape)
                       )
                       Box(
                           modifier = Modifier
                               .size(10.dp)
                               .align(Alignment.BottomEnd)
                               .offset(x = 2.dp, y = 2.dp)
                               .clip(CircleShape)
                               .background(Color(0xFF9C27B0)) // morado
                       )
                   }
               }
           }
           item {
               Text(
                   text = "Jessica Roberts",
                   color = Color.White,
                   fontSize = 14.sp,
                   fontWeight = FontWeight.Bold
               )
           }
           item {
               Text(
                   text = "Tuesday • Now",
                   color = Color.LightGray,
                   fontSize = 10.sp
               )
           }
           item {
               Row(
                   modifier = Modifier.fillMaxSize()
                       .padding(start = 48.dp, end = 23.dp, top = 8.dp),
                   horizontalArrangement = Arrangement.End
               ){
                   Box(
                       modifier = Modifier
                           .widthIn(max = 250.dp)
                           .background(
                               color = Color(0xFF6A1B9A),
                               shape = RoundedCornerShape(16.dp)
                           )
                           .padding(horizontal = 12.dp, vertical = 8.dp)
                   ) {
                       Text(
                           text = "Hey! Hope it’s \n going well!",
                           color = Color.White,
                           fontSize = 14.sp
                       )
                   }
               }
           }
           item {
               Row(
                   modifier = Modifier.fillMaxSize()
                       .padding(start = 48.dp, end = 23.dp, top = 3.dp),
                   horizontalArrangement = Arrangement.End
               ){
                   Box(
                       modifier = Modifier
                           .widthIn(max = 250.dp)
                           .background(
                               color = Color(0xFF6A1B9A),
                               shape = RoundedCornerShape(16.dp)
                           )
                           .padding(horizontal = 12.dp, vertical = 8.dp)
                   ) {
                       Text(
                           text = "However",
                           color = Color.White,
                           fontSize = 14.sp
                       )
                   }
               }
           }
       }
    }
}

