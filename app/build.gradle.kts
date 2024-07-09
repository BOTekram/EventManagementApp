plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.fit2081.assignment1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fit2081.assignment1"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Add new dependency for google maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // import Room database common and runtime packages
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.activity:activity:1.9.0")

    // to parse annotations eg @Database, @Entity, @DAO, etc
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    implementation("com.android.volley:volley:1.2.1") //volley library
    implementation ("com.google.code.gson:gson:2.10.1") //gson library for json parsing
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.room:room-common:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}