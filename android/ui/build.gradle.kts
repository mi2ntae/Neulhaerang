plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.finale.neulhaerang.ui"
    compileSdkVersion(rootProject.extra["compileSDKVersion"] as Int)

    defaultConfig {

        minSdkVersion(rootProject.extra["minimumSDKVersion"] as Int)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // modules
    implementation(project(":common"))

    // android
    implementation("androidx.core:core-ktx:${rootProject.extra["coreKtxVersion"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycleRuntimeKtxVertion"]}")
    implementation("androidx.activity:activity-compose:${rootProject.extra["activityComposeVersion"]}")
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    implementation("androidx.compose.ui:ui:${rootProject.extra["composeUiVersion"]}")
    implementation("androidx.compose.ui:ui-graphics:${rootProject.extra["composeUiVersion"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["composeUiVersion"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["materialVersion"]}")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.extra["materialVersion"]}")
    // navigation
    implementation("androidx.navigation:navigation-compose:${rootProject.extra["composeNavVersion"]}")
    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.extra["composeBomVersion"]}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["composeUiVersion"]}")
    // debug
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["composeUiVersion"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["composeUiVersion"]}")
}