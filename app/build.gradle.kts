import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.mprzypasniak.themoviedbapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mprzypasniak.themoviedbapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-alpha"

        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val token = properties.getProperty("TOKEN") ?: ""

        buildConfigField(
            type = "String",
            name = "TOKEN",
            value = token
        )

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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.glide)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.kapt)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}