import io.netty.util.internal.UnstableApi

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // TODO: firebase project와 연결해야하는 경우 아래 주석을 해제합니다.
    // id("com.google.gms.google-services")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "ai.fairytech.moment.cashback"

    defaultConfig {
        applicationId = "ai.fairytech.moment.cashback"
        minSdk = 24
        compileSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensionList.add("feature")
    productFlavors {
        register("default")
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        pickFirst("META-INF/DEPENDENCIES")
        pickFirst("META-INF/INDEX.LIST")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("ai.fairytech:moment-x:1.0.9")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
