package com.finale.neulhaerang.ui.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finale.neulhaerang.common.navigation.AppNavItem
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.Memo
import com.finale.neulhaerang.data.SqliteHelper
import com.finale.neulhaerang.data.api.ArApi
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.model.request.AroundMemberCharacterReqDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.finale.neulhaerang.domain.KakaoAuthViewModel
import com.finale.neulhaerang.ui.app.checklist.CheckListCreationScreen
import com.finale.neulhaerang.ui.app.checklist.CheckListModifyScreen
import com.finale.neulhaerang.ui.app.config.SettingScreen
import com.finale.neulhaerang.ui.app.login.LoginScreen
import com.finale.neulhaerang.ui.app.main.MainScreen
import com.finale.neulhaerang.ui.app.mypage.MyPageScreen
import com.finale.neulhaerang.ui.app.social.SocialScreen
import com.finale.neulhaerang.ui.theme.NeulHaeRangTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * 메인 앱
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun App(getResult: ActivityResultLauncher<Intent>, context: Context, lm: LocationManager) {
    val viewModel = viewModel<KakaoAuthViewModel>()
    val scope = rememberCoroutineScope()
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()
    Log.i("KakaoAuthViewModel", "로그인 되었나요? " + isLoggedIn.value)

    val sqliteHelper = SqliteHelper(context, "memo", null, 1)
    val dataStore = DataStoreApplication.getInstance().getDataStore()
    if (isLoggedIn.value) {
        Log.i("mintaeApp", "login")
        if (sqliteHelper.selectMemo(LocalDateTime.now().toLocalDate().toString()).size == 1) {
            Log.i("mintaeApp", "data size okay")
            sqliteHelper.insertMemo(Memo(LocalDateTime.now().toLocalDate().toString()))
            scope.launch {
                MemberApi.instance.recordTiredness(dataStore.getTiredness().firstOrNull() ?: 1)
            }
        }

        var longitude = 0.0;
        var latitude = 0.0;

        val gpsLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val provider: String = location.provider.toString()
                val longitude: Double = location.longitude
                val latitude: Double = location.latitude
                val altitude: Double = location.altitude

                Log.i("Location", provider)
                Log.i("Location", longitude.toString())
                Log.i("Location", latitude.toString())
                Log.i("Location", altitude.toString())

                GlobalScope.launch {
                    val isLogin = DataStoreApplication.getInstance().getDataStore().getLoginStatus()
                        .firstOrNull() ?: false
                    if (!isLogin) {
                        Log.i("Location", "onLocationChanged: not login")
                        return@launch
                    }

                    Log.i("Location", "$latitude / $longitude")
                    ArApi.instance.changeGeo(
                        AroundMemberCharacterReqDto(
                            latitude = latitude,
                            longitude = longitude
                        )
                    ).onSuccess { (_, data) ->
                        data?.forEach { member ->
                            Log.i("Geo", member.memberId.toString())
                        }
                        Log.i("Geo", "size = 0")
                    }.onFailure {
                        Log.i("Geo", "Fail")
                    }
                }
            }

            //아래 3개함수는 형식상 필수부분
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        val isGPSEnabled: Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled: Boolean = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        Log.i("GPS", "Build Before")
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            when { //프로바이더 제공자 활성화 여부 체크
                isNetworkEnabled -> {
                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000, //몇초
                        0F,   //몇미터
                        gpsLocationListener
                    )
                }

                isGPSEnabled -> {
                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000, //몇초
                        0F,   //몇미터
                        gpsLocationListener
                    )
                }

                else -> {

                }
            }
        }
    }
    NeulHaeRangTheme {
        if (isLoggedIn.value) AppMain(getResult)
        else LoginScreen(viewModel)
    }
}

@Composable
fun AppMain(getResult: ActivityResultLauncher<Intent>) {
    val navController = rememberNavController()
    val kakaoAuthViewModel: KakaoAuthViewModel = viewModel()

    NeulHaeRangTheme {
        NavHost(
            navController = navController,
            startDestination = AppNavItem.Main.route,
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(route = AppNavItem.Main.route) {
                MainScreen(navController)
            }
            composable(route = AppNavItem.MyPage.route) {
                MyPageScreen(navController = navController, getResult)
            }
            composable(route = AppNavItem.Social.route) {
                SocialScreen()
            }
            composable(route = AppNavItem.CheckListCreation.route) {
                CheckListCreationScreen(navController = navController)
            }
            composable(
                route = "${AppNavItem.CheckListModify.route}/{type}/{index}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("index") { type = NavType.IntType }
                )) { entry ->
                CheckListModifyScreen(
                    navController = navController,
                    entry.arguments?.getString("type"),
                    entry.arguments?.getInt("index")
                )
            }
            composable(route = AppNavItem.Setting.route) {
                SettingScreen(
                    navController = navController,
                    kakaoAuthViewModel = kakaoAuthViewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
//    App()
}
