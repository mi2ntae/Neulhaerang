buildscript {
    val versionCode by extra(2)
    val versionName by extra("1.0.1")
    val compileSDKVersion by extra(34)
    val targetSDKVersion by extra(33)
    val minimumSDKVersion by extra(30)
    val activityComposeVersion by extra("1.8.0")
    val composeBomVersion by extra("2023.03.00")
    val composeNavVersion by extra("2.7.4")
    val composeUiVersion by extra("1.5.4")
    val coreKtxVersion by extra("1.12.0")
    val lifecycleVersion by extra("2.6.2")
    val materialVersion by extra("1.5.4")
    val material3Version by extra("1.1.2")
    val retrofit2Version by extra("2.9.0")
    val wearComposeVersion by extra("1.2.1")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("com.android.application") version "8.1.2" apply false
//    id("com.android.library") version "8.1.2" apply false
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.3.15" apply false

}
