plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.aplicacionprincipal"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.aplicacionprincipal"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    useLibrary("wear-sdk")
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.tiles)
    implementation(libs.tiles.material)
    implementation(libs.tiles.tooling.preview)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.watchface.complications.data.source.ktx)
    implementation(libs.material3.android)
    implementation(libs.compose.material3)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.tiles.tooling)
    implementation("androidx.navigation:navigation-compose:2.9.0")

    implementation("androidx.wear.compose:compose-material:1.2.1")
    //implementation("androidx.wear.compose:compose-foundation:1.2.1")
    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")

    //dependencias
    implementation("androidx.wear.compose:compose-material:1.3.1")
    implementation("androidx.wear.compose:compose-navigation:1.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //musica
    // Versiones base (obligatorias)
    implementation("androidx.core:core-ktx:1.12.0")

    // Wear OS Compose (versiones estables más recientes)
    implementation("androidx.wear.compose:compose-foundation:1.3.0")

    // Jetpack Compose (compatibilidad con Wear)
    implementation("androidx.compose.ui:ui-tooling:1.6.3")
    implementation("androidx.compose.foundation:foundation:1.6.3")

    // Para íconos

    // MediaPlayer
    implementation("androidx.media:media:1.7.0")
    implementation("androidx.compose.material:material:1.6.4")

}