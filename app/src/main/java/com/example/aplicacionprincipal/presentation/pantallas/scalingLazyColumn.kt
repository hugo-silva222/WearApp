package com.example.aplicacionprincipal.presentation.pantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.tooling.preview.devices.WearDevices
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Switch
import androidx.wear.compose.material.ToggleChip
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun faceWatch(navController: NavController){
    val listState = rememberScalingLazyListState()
    Scaffold(
        timeText = {TimeText()},
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        },

        modifier = Modifier.fillMaxSize().background(Color.Black),
    ){
        ScalingLazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ){
            item {
                ToggleChip(
                    checked = false,
                    onCheckedChange = {},
                    label = {Text(text = "Modo Oscuro")},
                    toggleControl = {
                        Switch(checked = true, onCheckedChange = null)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            item {
                Card(
                    onClick = {},
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).fillMaxSize()
                ) {
                    Text(
                        text = "Mensaje nuevo",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = "Mas",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
            item {
                Chip(
                    onClick = {},
                    label = {Text( text = "Chip", fontSize = 10.sp, color = Color.White,
                        fontFamily = FontFamily.Monospace)},
                    modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp)
                )
            }
        }
    }
}