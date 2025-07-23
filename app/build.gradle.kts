import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "ir.abdev.intentbridge"
    compileSdk = 33
    
    signingConfigs {
        create("release") {
            val properties = Properties().apply {
                load(rootProject.file("local.properties").inputStream())
            }

            storeFile = rootProject.file("keystore.jks")
            storePassword = properties.getProperty("storePassword")
            keyAlias = "abg"
            keyPassword = properties.getProperty("keyPassword")
        }
    }

    defaultConfig {
        applicationId = "ir.abdev.intentbridge"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

// هیچ dependency اضافی لازم نیست!
// پلاگین kotlin-android به صورت خودکار kotlin-stdlib را اضافه می‌کند.
// dependencies { }
