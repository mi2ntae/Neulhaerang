package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.api.AuthApi
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.entity.UserData
import com.finale.neulhaerang.data.model.response.MemberStatResDto
import com.finale.neulhaerang.data.model.response.MemberStatusResDto
import com.finale.neulhaerang.data.util.onSuccess
import com.google.gson.Gson
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
@OptIn(DelicateCoroutinesApi::class)
class TransferWithUnity {
    // 데이터를 받을 유니티 스크립트가 컴포넌트로 붙어 있는 게임오브젝트명
    private val unityGameObject = "AndroidController"
    // 메소드가 static (companion object) 이면 안 됨
    fun receiveMessage(userId: String) {
        runBlocking {
            GlobalScope.launch {
                val memberId =
                    DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull() ?: 0
                MemberApi.instance.getMemberStat(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", data.toString())
                    }
            }.join()
        }
        Log.i("heejeong", "TransferWithUnity $userId")
        //받은 userId로 작업 진행 예시
        val userData = UserData(userId, "rocketman", "heejeong@gmail.com")
        sendMessage(userData)
    }

    /*
    * 유니티에 데이터 보내기
     */
    // UserData객체를 JSON으로 직렬화시켜서 전송 (GSON 라이브러리 사용)
    fun sendMessage(userData: UserData) {
        val gson = Gson()
        val jsonMessage = gson.toJson(userData)

        // 데이터를 받을 유니티 스크립트가 컴포넌트로 붙어 있는 게임오브젝트명
        val unityGameObject = "AndroidController"
        // 데이터를 받을 유니티 스크립트의 메소드명
        val unityMethod = "ReceiveMessage"

        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /*
    * 유저 상태 정보 조회
    * */
    fun getMemberStatus() {
        runBlocking {
            GlobalScope.launch {
                val memberId =
                    DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull() ?: 0
                MemberApi.instance.getMemberStatus(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", data.toString())
                        sendMemberStatus(data)
                    }
            }.join()
        }
    }
    private fun sendMemberStatus(indolence: MemberStatusResDto) {
        val gson = Gson()
        val jsonMessage = gson.toJson(indolence)
        val unityMethod = "ReceiveMessage"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /*
    * 유저 능력치 조회
    * */
    fun getMemberStats() {
        runBlocking {
            GlobalScope.launch {
                val memberId =
                    DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull() ?: 0
                MemberApi.instance.getMemberStat(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", data.toString())
                        sendMemberStats(data)
                    }
            }.join()
        }
    }

    private fun sendMemberStats(memberStats: List<MemberStatResDto>) {
        val gson = Gson()
        val jsonMessage = gson.toJson(memberStats)
        val unityMethod = "ReceiveMessage"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }
}