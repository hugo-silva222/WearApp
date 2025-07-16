package com.example.aplicacionprincipal.presentation.oswaapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Card
import androidx.wear.compose.material3.EdgeButton
import androidx.wear.compose.material3.EdgeButtonSize
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.tooling.preview.devices.WearDevices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.*
import com.example.aplicacionprincipal.R

// Pantalla principal de recordatorios
@Composable
fun Recordatorios(
    navController: NavController,
    viewModel: ExerciseReminderViewModel
) {
    val reminders by viewModel.reminders

    AppScaffold(
        modifier = Modifier.background(Color.Black)
    ) {
        val listState = rememberTransformingLazyColumnState()
        val transformationSpec = rememberTransformationSpec()

        ScreenScaffold(
            scrollState = listState,
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 32.dp,
                start = 10.dp,
                end = 10.dp
            ),
        ) { contentPadding ->
            ScalingLazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp) // más espacio para no tapar los botones
            ) {
                item {
                    Text(
                        text = "Recordatorios de ejercicios",
                        color = Color(0xFFB4FF67),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                itemsIndexed(reminders) { index, reminder ->
                    ExerciseReminderCard(
                        reminder = reminder,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.transformedHeight(this, transformationSpec)
                            .clickable {
                                navController.navigate("editReminder/${reminder.day}")
                            },
                        //transformation = SurfaceTransformation(transformationSpec),
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

//                item {
//                    InfoRecordatorio(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .transformedHeight(this, transformationSpec),
//                        transformation = SurfaceTransformation(transformationSpec),
//                    )
//                }
            }
        }
    }
}

@Composable
fun InfoRecordatorio(
    modifier: Modifier = Modifier,
    transformation: SurfaceTransformation,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            Text(
                text = "Active time",
                color = Color(0xFFB4FF67),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_timer_24),
                    contentDescription = "Tiempo",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "45:23",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

// Modelo de datos
data class ExerciseReminder(
    val day: String,
    val exercise: String = "Toca para configurar",
    val repetitions: Int = 30,
    val hour: Int = 8,
    val minute: Int = 0,
    val isActive: Boolean = false
)

// ViewModel para manejar el estado
class ExerciseReminderViewModel : ViewModel() {
    private val _reminders = mutableStateOf(
        listOf(
            ExerciseReminder("Lunes"),
            ExerciseReminder("Martes"),
            ExerciseReminder("Miércoles"),
            ExerciseReminder("Jueves"),
            ExerciseReminder("Viernes"),
            ExerciseReminder("Sábado"),
            ExerciseReminder("Domingo")
        )
    )
    val reminders: State<List<ExerciseReminder>> = _reminders

    fun updateReminder(index: Int, reminder: ExerciseReminder) {
        val currentList = _reminders.value.toMutableList()
        currentList[index] = reminder
        _reminders.value = currentList
    }

    fun getReminderByDay(day: String): ExerciseReminder? {
        return _reminders.value.find { it.day == day }
    }
}

// Card para cada recordatorio
@Composable
fun ExerciseReminderCard(
    reminder: ExerciseReminder,
    modifier: Modifier = Modifier,
    //transformation: SurfaceTransformation,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            Text(
                text = reminder.day,
                color = Color(0xFFB4FF67),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = reminder.exercise,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${reminder.repetitions} repeticiones",
                    color = Color.Gray,
                    fontSize = 12.sp,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = String.format("%02d:%02d", reminder.hour, reminder.minute),
                    color = Color(0xFFB4FF67),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Pantalla de edición de recordatorio
@Composable
fun EditReminderScreen(
    navController: NavController,
    day: String,
    viewModel: ExerciseReminderViewModel // <- sin valor por defecto
    //viewModel: ExerciseReminderViewModel = viewModel()
) {
    val reminder = viewModel.getReminderByDay(day)
    if (reminder == null) {
        Text("Recordatorio no encontrado.") // o mostrar algo mejor
        return
    }

    var selectedExercise by remember { mutableStateOf(reminder.exercise) }
    var selectedRepetitions by remember { mutableStateOf(reminder.repetitions) }
    var selectedHour by remember { mutableStateOf(reminder.hour) }
    var selectedMinute by remember { mutableStateOf(reminder.minute) }

    val exercises = listOf(
        "Sentadillas", "Flexiones", "Plancha", "Zancadas",
        "Abdominales", "Fondos en pared", "Saltos de tijera"
    )

    val repetitionsList = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)

    AppScaffold(
        modifier = Modifier.background(Color.Black)
    ) {
        val listState = rememberTransformingLazyColumnState()
        val transformationSpec = rememberTransformationSpec()

        ScreenScaffold(
            scrollState = listState,
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 32.dp,
                start = 10.dp,
                end = 10.dp
            ),
        ) { contentPadding ->
            TransformingLazyColumn(
                state = listState,
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Editar $day",
                        color = Color(0xFFB4FF67),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // Selector de ejercicio
                item {
                    SelectorCard(
                        title = "Ejercicio",
                        selectedValue = selectedExercise,
                        options = exercises,
                        onSelectionChange = { selectedExercise = it }
                    )
                }

                // Selector de repeticiones
                item {
                    SelectorCard(
                        title = "Repeticiones",
                        selectedValue = selectedRepetitions.toString(),
                        options = repetitionsList.map { it.toString() },
                        onSelectionChange = { selectedRepetitions = it.toInt() }
                    )
                }

                // Selector de hora
                item {
                    TimeSelector(
                        selectedHour = selectedHour,
                        selectedMinute = selectedMinute,
                        onHourChange = { selectedHour = it },
                        onMinuteChange = { selectedMinute = it }
                    )
                }

                // Botón guardar
                item {
                    androidx.wear.compose.material3.Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        onClick = {
                            val reminderIndex = viewModel.reminders.value.indexOfFirst { it.day == day }
                            if (reminderIndex != -1) {
                                viewModel.updateReminder(
                                    reminderIndex,
                                    reminder.copy(
                                        exercise = selectedExercise,
                                        repetitions = selectedRepetitions,
                                        hour = selectedHour,
                                        minute = selectedMinute,
                                        isActive = true
                                    )
                                )
                                // Programar notificación
                                try {
                                    scheduleNotification(
                                        context = navController.context,
                                        day = day,
                                        exercise = selectedExercise,
                                        repetitions = selectedRepetitions,
                                        hour = selectedHour,
                                        minute = selectedMinute
                                    )
                                } catch (e: Exception) {
                                    // Manejo de errores si no se puede programar la notificación
                                    Log.e("Notification", "Error scheduling notification: ${e.message}")
                                }
                            }
                            navController.popBackStack()
                        },
                        colors = androidx.wear.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB4FF67),
                            contentColor = Color.Black,
                        ),
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// Selector genérico para opciones
@Composable
fun SelectorCard(
    title: String,
    selectedValue: String,
    options: List<String>,
    onSelectionChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            Text(
                text = title,
                color = Color(0xFFB4FF67),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = selectedValue,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (expanded) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 120.dp)
                ) {
                    items(options) { option ->
                        Text(
                            text = option,
                            color = if (option == selectedValue) Color(0xFFB4FF67) else Color.Gray,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectionChange(option)
                                    expanded = false
                                }
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Selector de hora
@Composable
fun TimeSelector(
    selectedHour: Int,
    selectedMinute: Int,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Column {
            Text(
                text = "Hora",
                color = Color(0xFFB4FF67),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Selector de hora
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    androidx.wear.compose.material3.Button(
                        onClick = { onHourChange(if (selectedHour < 23) selectedHour + 1 else 0) },
                        colors = androidx.wear.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("+", fontSize = 12.sp)
                    }

                    Text(
                        text = String.format("%02d", selectedHour),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    androidx.wear.compose.material3.Button(
                        onClick = { onHourChange(if (selectedHour > 0) selectedHour - 1 else 23) },
                        colors = androidx.wear.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("-", fontSize = 12.sp)
                    }
                }

                Text(
                    text = ":",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Selector de minuto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    androidx.wear.compose.material3.Button(
                        onClick = { onMinuteChange(if (selectedMinute < 59) selectedMinute + 1 else 0) },
                        colors = androidx.wear.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("+", fontSize = 12.sp)
                    }

                    Text(
                        text = String.format("%02d", selectedMinute),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    androidx.wear.compose.material3.Button(
                        onClick = { onMinuteChange(if (selectedMinute > 0) selectedMinute - 1 else 59) },
                        colors = androidx.wear.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text("-", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// Función para programar notificaciones
fun scheduleNotification(
    context: Context,
    day: String,
    exercise: String,
    repetitions: Int,
    hour: Int,
    minute: Int
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ExerciseReminderReceiver::class.java).apply {
        putExtra("exercise", exercise)
        putExtra("repetitions", repetitions)
        putExtra("day", day)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        day.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)

        // Si la hora ya pasó hoy, programar para mañana
        if (timeInMillis < System.currentTimeMillis()) {
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    //alarmManager.setRepeating(
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY * 7,
        pendingIntent
    )
}

// Receptor de notificaciones
class ExerciseReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val exercise = intent.getStringExtra("exercise") ?: return
        val repetitions = intent.getIntExtra("repetitions", 0)
        val day = intent.getStringExtra("day") ?: return

        showNotification(context, day, exercise, repetitions)
    }

    private fun showNotification(context: Context, day: String, exercise: String, repetitions: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal de notificación (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "exercise_reminders",
                "Recordatorios de Ejercicio",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "exercise_reminders")
            .setContentTitle("¡Hora de ejercitarse!")
            .setContentText("$day: $exercise - $repetitions repeticiones")
            .setSmallIcon(R.drawable.baseline_fitness_center_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(day.hashCode(), notification)
    }
}

// Configuración de navegación
@Composable
fun ExerciseReminderApp() {
    val navController = rememberNavController()
    val viewModel: ExerciseReminderViewModel = viewModel()

    NavHost(navController = navController, startDestination = "recordatorios") {
        composable("recordatorios") {
            Recordatorios(navController, viewModel)
        }
        composable("editReminder/{day}") { backStackEntry ->
            val day = backStackEntry.arguments?.getString("day") ?: ""
            EditReminderScreen(navController, day, viewModel) // Usa el mismo ViewModel
        }
    }
}