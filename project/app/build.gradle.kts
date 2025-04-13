plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)


    alias(libs.plugins.hiltAndroid)
    kotlin("kapt")
   }

android {
    namespace = "com.project.mobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.mobile"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Dépendances CORE ESSENTIELLES pour SharedPreferences.edit {}
    implementation("androidx.core:core-ktx:1.12.0") // Version mise à jour
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation("org.testng:testng:7.1.0")
    kapt(libs.hilt.android.compiler) // Utilise kapt au lieu de ksp

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.media3:media3-common-ktx:1.5.1")

    // Room for database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Test libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug libraries
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Additional dependencies
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("com.google.code.gson:gson:2.8.9")

    // Compose et Lifecycle versions
    implementation("androidx.compose.ui:ui:1.4.3") // Remplacer <compose_version> par la version réelle
    implementation("androidx.compose.material3:material3:1.0.1") // Remplacer <compose_version> par la version réelle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1") // Remplacer <lifecycle_version> par la version réelle
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3") // Remplacer <compose_version> par la version réelle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1") // Remplacer <lifecycle_version> par la version réelle
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0") // Version compatible avec Kotlin 2.0

}
