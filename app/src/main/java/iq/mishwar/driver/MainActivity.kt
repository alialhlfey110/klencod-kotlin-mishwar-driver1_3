pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MishwarDriver"
include(":app")plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.services) apply false
}org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=filterValues { $0 }distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/Dispatchers[versions]
agp = "8.7.2"
kotlin = "2.0.21"
coreKtx = "1.15.0"
lifecycleRuntimeKtx = "2.8.7"
activityCompose = "1.9.3"
composeBom = "2024.10.01"
navigationCompose = "2.8.4"
googleServices = "4.4.2"
firebaseBom = "33.5.1"
playServicesMaps = "19.0.0"
playServicesLocation = "21.3.0"
mapsCompose = "6.1.2"
coilCompose = "2.7.0"
coroutinesPlayServices = "1.9.0"
datastore = "1.1.1"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-material-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-datastore-preferences = { group = "androidx.datastore", name = "datastore-preferences", version.ref = "datastore" }

firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
firebase-firestore = { group = "com.google.firebase", name = "firebase-firestore" }
firebase-storage = { group = "com.google.firebase", name = "firebase-storage" }
firebase-messaging = { group = "com.google.firebase", name = "firebase-messaging" }

play-services-maps = { group = "com.google.android.gms", name = "play-services-maps", version.ref = "playServicesMaps" }
play-services-location = { group = "com.google.android.gms", name = "play-services-location", version.ref = "playServicesLocation" }
maps-compose = { group = "com.google.maps.android", name = "maps-compose", version.ref = "mapsCompose" }

coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coilCompose" }
kotlinx-coroutines-play-services = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-play-services", version.ref = "coroutinesPlayServices" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
google-services = { id = "com.google.gms.google-services", version.ref = "googleServices" }import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

val localProps = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val mapsApiKey: String = localProps.getProperty("MAPS_API_KEY")
    ?: System.getenv("MAPS_API_KEY")
    ?: ""

val firestoreDatabaseId: String = localProps.getProperty("FIRESTORE_DATABASE_ID")
    ?: System.getenv("FIRESTORE_DATABASE_ID")
    ?: "ai-studio-mishwardriver-3193265b-9bc1-4906-9421-34b8b4b41722"

android {
    namespace = "iq.mishwar.driver"
    compileSdk = 35

    defaultConfig {
        applicationId = "iq.mishwar.driver"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
        buildConfigField("String", "FIRESTORE_DATABASE_ID", "\"$firestoreDatabaseId\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel-compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)

    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.maps.compose)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.play-services)

    debugImplementation(libs.androidx.ui.tooling)
}# Keep Firestore / Firebase data model classes if needed by serialization
-keepattributes Signature
-keepattributes *Annotation*
-keep class iq.mishwar.driver.data.model.** { *; }# Copy this file to local.properties and fill in your Google Maps API key.
# Do NOT commit local.properties with real keys to GitHub.
MAPS_API_KEY=YOUR_RESTRICTED_GOOGLE_MAPS_ANDROID_API_KEY
FIRESTORE_DATABASE_ID=ai-studio-mishwardriver-3193265b-9bc1-4906-9421-34b8b4b41722{
  "project_info": {
    "project_number": "722920950197",
    "project_id": "gen-lang-client-0120505976",
    "storage_bucket": "gen-lang-client-0120505976.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "REPLACE_WITH_ANDROID_APP_ID_FROM_FIREBASE_CONSOLE",
        "android_client_info": {
          "package_name": "iq.mishwar.driver"
        }
      },
      "api_key": [
        {
          "current_key": "REPLACE_WITH_ANDROID_FIREBASE_API_KEY"
        }
      ]
    }
  ],
  "configuration_version": "1"
}<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.telephony"
        android:required="false" />

    <!-- Network & Location Permissions (Android 14+ Compliant) -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />

    <!-- Camera, Media, Phone Call & Push Notification Permissions -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission
        android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <!-- Package Visibility for Iraqi Navigation (Waen) and Google Maps -->
    <queries>
        <package android:name="com.fvee.waen" />
        <package android:name="com.google.android.apps.maps" />
        <intent>
            <action android:name="android.intent.action.VIEW" />
            <data android:scheme="geo" />
        </intent>
        <intent>
            <action android:name="android.intent.action.DIAL" />
        </intent>
    </queries>

    <application
        android:name=".MishwarApp"
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MishwarDriver">

        <!-- Google Maps API Key injected via manifestPlaceholders from local.properties / env -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="${MAPS_API_KEY}" />

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:launchMode="singleTop"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Foreground GPS Location Service for Live Trip & Online Availability Tracking -->
        <service
            android:name=".service.LocationTrackingService"
            android:enabled="true"
            android:exported="false"
            android:foregroundServiceType="location" />

        <!-- Firebase Cloud Messaging Service for New Ride Push Alerts -->
        <service
            android:name=".service.MishwarFirebaseMessagingService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <!-- FileProvider for Camera Photo Capture -->
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
    </application>

</manifest><?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">مشوار – Mishwar Driver</string>
    <string name="default_notification_channel_id">mishwar_rides_channel</string>
</resources><?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="mishwar_primary">#FF0F52BA</color>
    <color name="mishwar_accent">#FFF59E0B</color>
    <color name="mishwar_surface">#FFF8FAFC</color>
</resources>