import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.finale.neulhaerang"
    compileSdkVersion(rootProject.extra["compileSDKVersion"] as Int)

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.finale.neulhaerang"
        minSdkVersion(rootProject.extra["minimumSDKVersion"] as Int)
        targetSdkVersion(rootProject.extra["targetSDKVersion"] as Int)
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\""+getApiKey("kakao.native.app.key")+"\"")
        buildConfigField("String", "NATIVE_APP_KEY", "\""+getApiKey("native.app.key")+"\"")

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getApiKey("kakao.native.app.key")
        manifestPlaceholders["NATIVE_APP_KEY"] = getApiKey("native.app.key")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {

    // modules
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":ui"))
    // android
    implementation("androidx.core:core-ktx:${rootProject.extra["coreKtxVersion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["activityComposeVersion"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra["lifecycleVersion"]}")
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    implementation("androidx.compose.ui:ui-android:${rootProject.extra["composeUiVersion"]}")
    implementation("androidx.compose.material3:material3:${rootProject.extra["material3Version"]}")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["composeUiVersion"]}")
    // debug
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["composeUiVersion"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["composeUiVersion"]}")
    //kakao login
    implementation ("com.kakao.sdk:v2-user:2.17.0") // 카카오 로그인

    // unity
    implementation(project(":unityLibrary"))
//    implementation(fileTree(mapOf("dir" to "../UnityProject/androidBuild/unityLibrary/libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "..\\UnityProject\\androidBuild\\unityLibrary\\libs", "include" to listOf("*.jar"))))


    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    // When using the BoM, you don't specify versions in Firebase library dependencies
    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // health connect
    implementation ("androidx.health.connect:connect-client:1.1.0-alpha01")
}