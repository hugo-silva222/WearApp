package com.example.aplicacionprincipal.presentation.pantallas


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.*
import kotlin.math.sqrt

@Composable
fun Calculadora(navController: NavController){
    var caja by remember{ mutableStateOf("0") }
    var resultado by remember{ mutableStateOf(0.0) }
    var operador by remember{ mutableStateOf("") }
    var dato1 by remember{ mutableStateOf(0.0) }

    fun PrecionarBoton(button: String){
        when(button){
            in "0"  .. "9", "." -> {
                if (caja == "0") caja = button else caja += button
            }
            in listOf("/", "*", "-", "+") -> {
                operador = button
                dato1 = caja.toDouble()
                caja = "0"
            }
            "=" -> {
                resultado = when (operador){
                    "/" -> dato1 / caja.toDouble()
                    "*" -> dato1 * caja.toDouble()
                    "-" -> dato1 - caja.toDouble()
                    "+" -> dato1 + caja.toDouble()
                    else -> 0.0
                }
                caja = resultado.toString()
            }
            "c" -> {
                resultado = 0.0
                caja = "0"
            }
            "+/-" -> { resultado = -1.0 * caja.toDouble()
                caja = resultado.toString()
            }
            "X²" -> { resultado = caja.toDouble() * caja.toDouble()
                caja = resultado.toString()
            }
            "√" -> { resultado = sqrt(caja.toDouble())
                caja = resultado.toString()
            }
        }
    }

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "Calculadora", modifier = Modifier.padding(top = 10.dp))
        }
        item {
            Text(text = caja, style = MaterialTheme.typography.display3)
        }
        item {
            Renglones("7", "8", "9", "/", "c") { PrecionarBoton(it) }
        }
        item {
            Renglones("4", "5", "6", "*", "+/-") { PrecionarBoton(it) }
        }
        item {
            Renglones("1", "2", "3", "-", "X²") { PrecionarBoton(it) }
        }
        item {
            Renglones("0", ".", "=", "+", "√") { PrecionarBoton(it) }
        }
    }
}

@Composable
fun Renglones(vararg buttons: String, onClick: (String) -> Unit) {
    Row {
        buttons.forEach { button ->
            Button(
                modifier = Modifier
                    .padding(all = 1.dp)
                    .size(width = 28.dp, height = 28.dp),
                onClick = { onClick(button) }
            ) {
                Text(text = button)
            }
        }
    }
}