package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.api.AuthApi
import com.finale.neulhaerang.data.entity.UserData
import com.finale.neulhaerang.data.util.onSuccess
import com.google.gson.Gson
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TransferWithUnity {
    // 메소드가 static (companion object) 이면 안 됨
    fun receiveMessage(userId: String) {
        runBlocking {
            GlobalScope.launch {
                AuthApi.instance.postCheck().onSuccess {
                    Log.i("heejeong", "api test")
                    Log.i("heejeong", it.data.toString())
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
}