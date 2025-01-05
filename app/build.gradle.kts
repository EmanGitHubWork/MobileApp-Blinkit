plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.blinkit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.blinkit"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{

        viewBinding = true
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database:20.2.3")
    implementation("org.osmdroid:osmdroid-android:6.1.11")
    implementation ("com.google.firebase:firebase-firestore-ktx:24.8.1")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation ("com.google.firebase:firebase-auth-ktx:23.1.0")

    implementation ("com.google.firebase:firebase-auth:23.1.0")
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.auth)
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation(libs.firebase.firestore.ktx)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)




    //text dimension
//    implementation("com.intuit.sdp:sdp-android:1.1.0")
//    implementation("com.intuit.ssp:ssp-android:1.1.0")
    // Navigation Component
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    //lifecycle
//    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
//    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
}