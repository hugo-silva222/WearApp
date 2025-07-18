package com.example.aplicacionprincipal.presentation.alarma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.wear.compose.material.Picker
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.wear.compose.material.rememberPickerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.wear.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

import com.example.aplicacionprincipal.presentation.models.*

// --------- CREAR CANAL DE NOTIFICACIÓN ---------
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "alarma_channel",
            "Canal de Alarmas",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de alarmas activadas"
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

// --------- FUNCIÓN PARA MOSTRAR NOTIFICACIONES ---------
fun mostrarNotificacion(context: Context, mensaje: String) {
    val builder = NotificationCompat.Builder(context, "alarma_channel")
        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
        .setContentTitle("Alarma activada")
        .setContentText(mensaje)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
}

// --------- PANTALLA AGREGAR ---------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AgregarAlarma(navController: NavController, onAgregar: (String, List<String>) -> Unit) {
    var horaSeleccionada by remember { mutableStateOf(8) }
    var minutoSeleccionado by remember { mutableStateOf(0) }
    var diasSeleccionados by remember { mutableStateOf(listOf<String>()) }
    val dias = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFC1CC), Color(0xFFE91E63))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(8.dp)
    ) {
        ScalingLazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Selecciona la hora:", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            item {
                val horaState = rememberPickerState(24, horaSeleccionada)
                val minutoState = rememberPickerState(60, minutoSeleccionado)

                LaunchedEffect(horaState.selectedOption) {
                    horaSeleccionada = horaState.selectedOption
                }
                LaunchedEffect(minutoState.selectedOption) {
                    minutoSeleccionado = minutoState.selectedOption
                }

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Hora", color = Color.White)
                        Picker(horaState, Modifier.height(100.dp).width(60.dp)) {
                            Text("%02d".format(it), color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(":", color = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Min", color = Color.White)
                        Picker(minutoState, Modifier.height(100.dp).width(60.dp)) {
                            Text("%02d".format(it), color = Color.White)
                        }
                    }
                }
            }

            item {
                Text("Días para repetir:", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            item {
                FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    dias.forEach { dia ->
                        val seleccionado = dia in diasSeleccionados
                        FilterChip(
                            selected = seleccionado,
                            onClick = {
                                diasSeleccionados = if (seleccionado)
                                    diasSeleccionados - dia else diasSeleccionados + dia
                            },
                            label = {
                                Text(dia, color = if (seleccionado) Color.White else Color.Black)
                            },
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val horaFormateada = "%02d:%02d".format(horaSeleccionada, minutoSeleccionado)
                        if (diasSeleccionados.isNotEmpty()) {
                            onAgregar(horaFormateada, diasSeleccionados)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE91E63)),
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Guardar alarma", color = Color.White)
                }
            }
        }
    }
}

// --------- PANTALLA PRINCIPAL ---------
@Composable
fun PantallaAlarma(navController: NavController) {
    var alarmaActiva by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.Black, Color.DarkGray)))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        ScalingLazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Text("Alarma diaria", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            item {
                ToggleChip(
                    checked = alarmaActiva,
                    onCheckedChange = { alarmaActiva = it },
                    label = { Text("Activar alarma", color = Color.White) },
                    toggleControl = {
                        Icon(
                            imageVector = if (alarmaActiva) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                )
            }

            item {
                Button(onClick = { navController.navigate("Lista") }) { Text("Lista") }
            }

            item {
                Button(
                    onClick = { navController.navigate("AgregarAlarma") },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text("+", fontSize = 20.sp, color = Color(0xFFE91E63))
                }
            }
        }
    }
}

// --------- PANTALLA LISTA CON NOTIFICACIÓN ---------
@Composable
fun PantallaLista(navController: NavController, listaAlarmas: SnapshotStateList<Alarma>) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFE4EC))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Alarmas guardadas:", fontSize = 16.sp, color = Color(0xFF880E4F))
            Spacer(modifier = Modifier.height(8.dp))

            ScalingLazyColumn(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                items(listaAlarmas.size) { index ->
                    val alarma = listaAlarmas[index]
                    var activa by remember { mutableStateOf(alarma.activa) }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(alarma.hora, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Días: ${alarma.dias.joinToString(", ")}", fontSize = 14.sp)
                        }

                        ToggleChip(
                            checked = activa,
                            onCheckedChange = { checked ->
                                activa = checked
                                listaAlarmas[index] = alarma.copy(activa = checked)

                                if (checked) {
                                    createNotificationChannel(context)
                                    mostrarNotificacion(
                                        context,
                                        "Alarma activada a las ${alarma.hora} para ${alarma.dias.joinToString(", ")}"
                                    )
                                }
                            },
                            label = { Text(if (activa) "Activada" else "Desactivada") },
                            toggleControl = {
                                Icon(
                                    imageVector = if (activa) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigateUp() }) {
                Text("Regresar")
            }
        }
    }
}

@Composable
fun VerificarAlarmas(listaAlarmas: SnapshotStateList<Alarma>) {
    val context = LocalContext.current

    LaunchedEffect(listaAlarmas) {
        while (true) {
            val ahora = Calendar.getInstance()

            // Obtener hora y minuto actuales en formato "HH:mm"
            val horaActual = "%02d:%02d".format(ahora.get(Calendar.HOUR_OF_DAY), ahora.get(Calendar.MINUTE))

            // Obtener día actual en formato abreviado igual que en la listaAlarmas.dias
            val diasSemana = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
            val diaActual = diasSemana[ahora.get(Calendar.DAY_OF_WEEK) - 1]

            // Recorremos las alarmas activas para verificar si alguna debe sonar
            listaAlarmas.forEach { alarma ->
                if (alarma.activa && alarma.hora == horaActual && alarma.dias.contains(diaActual)) {
                    mostrarNotificacion(
                        context,
                        "¡Es hora de tu alarma programada a las ${alarma.hora}!"
                    )
                }
            }

            delay(60 * 1000L) // Esperar 1 minuto antes de verificar nuevamente
        }
    }
}

