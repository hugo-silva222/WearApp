package com.example.aplicacionprincipal.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun AplicacionPrincipalTheme(
    content: @Composable () -> Unit
) {
    /**
     * Empty theme to customize for your app.
     * See: https://developer.android.com/jetpack/compose/designsystems/custom
     */
    MaterialTheme(
        content = content
    )
}