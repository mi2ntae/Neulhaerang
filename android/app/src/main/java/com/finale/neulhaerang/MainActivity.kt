package com.finale.neulhaerang

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import androidx.core.view.WindowCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.ui.app.App
import com.finale.neulhaerang.data.Memo
import com.finale.neulhaerang.data.SqliteHelper
import com.finale.neulhaerang.data.api.ArApi
import com.finale.neulhaerang.data.model.request.AroundMemberCharacterReqDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


/**
 * 메인 엑티비티
 * 푸시 알림이나 기기 연결 등의 설정이 들어감
 */
@OptIn(BuildCompat.PrereleaseSdkCheck::class)
class MainActivity : ComponentActivity() {
    lateinit var getResult: ActivityResultLauncher<Intent>
    var pageCode by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                pageCode = it.data?.getStringExtra("pageCode")?.toInt() ?: 0
            }
        }

        checkPermissionsAndRun()

        val lm = getSystemService(LOCATION_SERVICE) as LocationManager

        Log.i("GPS", "Build Before")
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

//        val token = FirebaseMessaging.getInstance().token.result
//        Log.i("heejeong",token)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.i("heejeong", it)
        }


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BackOnPressed()
            App(getResult, this@MainActivity, lm)
        }


    }

    fun checkPermissionsAndRun() {
        Log.i("mintae", "start")
        val PERMISSIONS =
            setOf(
                HealthPermission.getReadPermission(HeartRateRecord::class),
                HealthPermission.getWritePermission(HeartRateRecord::class),
                HealthPermission.getReadPermission(StepsRecord::class),
                HealthPermission.getWritePermission(StepsRecord::class),
                HealthPermission.getReadPermission(SleepSessionRecord::class),
                HealthPermission.getWritePermission(SleepSessionRecord::class),
            )

        val availabilityStatus = HealthConnectClient.getSdkStatus(this@MainActivity)
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE) {
            return
        }
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
            val uriString =
                "market://details?id=com.google.android.apps.healthdata"
            this@MainActivity.startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    setPackage("com.android.vending")
                    data = Uri.parse(uriString)
                    putExtra("overlay", true)
                    putExtra("callerId", this@MainActivity.packageName)
                }
            )
            return
        }
        val healthConnectClient = HealthConnectClient.getOrCreate(this@MainActivity)
        val requestPermissionActivityContract =
            PermissionController.createRequestPermissionResultContract()
        val requestPermissions =
            registerForActivityResult(requestPermissionActivityContract) { granted ->
                if (granted.containsAll(PERMISSIONS)) {
                    // Permissions successfully granted
                    Log.i("mintae", "Permission All Granted!!")
                    lifecycleScope.launch {
                        onPermissionAvailable(healthConnectClient)
                    }
                } else {
                    // Lack of required permissions
                    Log.i("mintae", "Not Enough Permissions!!")
                    Toast.makeText(this@MainActivity, "Permission not granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        lifecycleScope.launch {
            val granted = healthConnectClient.permissionController
                .getGrantedPermissions()
            if (granted.containsAll(PERMISSIONS)) {
                // Permissions already granted
                Log.i("mintae", "Second Permissions All Granted!!")
                onPermissionAvailable(healthConnectClient)
            } else {
                // Permissions not granted, request permissions.
                Log.i("mintae", "Second Not Enough Permissions!!")
                requestPermissions.launch(PERMISSIONS)
            }
        }
    }

    suspend fun onPermissionAvailable(healthConnectClient: HealthConnectClient) {
        val sqliteHelper = SqliteHelper(this, "memo", null, 1)
//        insertData(healthConnectClient, 17);      // 헬스 커넥트에 데이터 삽입 예시
//        readDailyRecords(healthConnectClient)       // 걸음수 받아오기
        sqliteHelper.deleteMemo(LocalDateTime.now().toLocalDate().toString())
        var memo = sqliteHelper.selectMemo(LocalDateTime.now().toLocalDate().toString())
        Log.i("SQLITE", memo.size.toString())

        if (memo.size == 0) {
            sqliteHelper.insertMemo(Memo(LocalDateTime.now().toLocalDate().toString()))
            val sleepTime = readSleepSessions(healthConnectClient)      // 수면량 받아오기
            val dataStore = DataStoreApplication.getInstance().getDataStore()
            val tiredness = getScoreOfIndolence(sleepTime)
            Log.i("Tiredness", tiredness.toString())
            dataStore.setTiredness(tiredness)
        } else {
            Log.i("SQLITE", memo.get(0).date)
        }
    }

    suspend fun readSleepSessions(healthConnectClient: HealthConnectClient): Long {
        val lastDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1)
        val firstDay = lastDay.minusDays(3)
        // 설정한 날짜 범위
        val sessions = mutableListOf<SleepSessionData>()
        val sleepSessionRequest = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(firstDay.toInstant(), lastDay.toInstant())
//            ascendingOrder = false,
        )
        val sleepSessions = healthConnectClient.readRecords(sleepSessionRequest)
        sleepSessions.records.forEach { session ->
            val sessionTimeFilter = TimeRangeFilter.between(session.startTime, session.endTime)
            val durationAggregateRequest = AggregateRequest(
                metrics = setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                timeRangeFilter = sessionTimeFilter
            )
            Log.i(
                "mintae",
                "[sleep] " + session.metadata.id + " / " + session.title + " / " + session.startTime.toString() + " / " + session.startZoneOffset + " / " + session.endTime.toString() + " / " + session.stages.toString() + " / " + session.endZoneOffset
            )
            val aggregateResponse = healthConnectClient.aggregate(durationAggregateRequest)
            sessions.add(
                SleepSessionData(
                    uid = session.metadata.id,
                    title = session.title,
                    notes = session.notes,
                    startTime = session.startTime,
                    startZoneOffset = session.startZoneOffset,
                    endTime = session.endTime,
                    endZoneOffset = session.endZoneOffset,
                    duration = aggregateResponse[SleepSessionRecord.SLEEP_DURATION_TOTAL],
                    stages = session.stages
                )
            )
        }

        if (sessions.size > 0) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

//            val calStartDate = dateFormat.parse(sessions.get(sessions.size-1).startTime.toString().substring(0, 19).replace("T", " ").format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).time
            val calStartDate = dateFormat.parse(
                sessions.get(sessions.size - 1).startTime.toString().substring(0, 19)
                    .replace("T", " ")
            ).time

            val calEndDate = dateFormat.parse(
                sessions.get(sessions.size - 1).endTime.toString().substring(0, 19)
                    .replace("T", " ")
            ).time

            Log.i("Cal", calStartDate.toString() + " / " + calEndDate.toString())
            val diff = calEndDate - calStartDate
            Log.i("Cal", TimeUnit.MILLISECONDS.toMinutes(diff).toString())
            return TimeUnit.MILLISECONDS.toMinutes(diff)
        }
        return 840;
    }

    private fun getScoreOfIndolence(sleepTime: Long): Int {
        for (i: Int in 0..9 step (1)) {
            if (sleepTime < (1 + i) * 60) return 100 - (10 * i)
        }
        return 0
    }

    private suspend fun readDailyRecords(client: HealthConnectClient) {
        val today = ZonedDateTime.now()
        val startOfDay = today.truncatedTo(ChronoUnit.DAYS)
        val timeRangeFilter = TimeRangeFilter.between(
            startOfDay.toLocalDateTime(),
            today.toLocalDateTime()
        )

        val stepsRecordRequest = ReadRecordsRequest(StepsRecord::class, timeRangeFilter)
        val numberOfStepsToday = client.readRecords(stepsRecordRequest)
            .records.sumOf { it.count }

        Log.i("mintae", "Steps ${numberOfStepsToday}")

    }

    private fun insertData(client: HealthConnectClient, steps: Long) {
        // 1
        val startTime = ZonedDateTime.now().minusSeconds(1).toInstant()
        val endTime = ZonedDateTime.now().toInstant()

        // 2
        val records = listOf(
            StepsRecord(
                count = steps,
                startTime = startTime,
                endTime = endTime,
                startZoneOffset = null,
                endZoneOffset = null,
            ),
        )

        // 3
        lifecycleScope.launch {
            client.insertRecords(records)
            Log.i("mintae", "Insert Success")
        }
    }
}

/*
 * 뒤로 가기 두 번 눌렀을 때 앱 종료
 */
@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

/*
*
* */