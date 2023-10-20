//buildscript {
//    val compileSDKVersion by extra(34)
//    val targetSDKVersion by extra(33)
//    val coreKtxVersion by extra("1.9.0")
//    val composeUiVersion by extra("1.8.0")
//    val composeNavVersion by extra("2.5.3")
//}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.2" apply false
}
val compileSDKVersion by extra(34)
val targetSDKVersion by extra(33)
val coreKtxVersion by extra("1.9.0")
val composeUiVersion by extra("1.8.0")
val composeNavVersion by extra("2.5.3")
