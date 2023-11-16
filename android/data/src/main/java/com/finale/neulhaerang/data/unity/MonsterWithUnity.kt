package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class MonsterWithUnity {
    private val unityGameObject = "MonsterController"

    init {
    }

    /**
     * 나태 괴물 처치
     */
    private fun defeatLazyMonster() {
        Log.i("heejeong", "[MonsterWithUnity] getMemberStats")
        runBlocking {
            MemberApi.instance.defeatLazyMonster()
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "$data")
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }
}
