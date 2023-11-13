package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.api.TitleApi
import com.finale.neulhaerang.data.entity.StatResult
import com.finale.neulhaerang.data.entity.UserData
import com.finale.neulhaerang.data.model.request.MemberItemReqDto
import com.finale.neulhaerang.data.model.response.MemberItemResDto
import com.finale.neulhaerang.data.model.response.MemberStatusResDto
import com.finale.neulhaerang.data.model.response.MemberTitlesResDto
import com.finale.neulhaerang.data.util.onFailure
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
    private var memberId: Long = 0

    init {
        Log.i("heejeong", "TransferWithUnity Init")
        Log.i("heejeong1", memberId.toString())
        runBlocking {
            GlobalScope.launch {
                memberId =
                    DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull()
                        ?: 0
            }.join()
        }
        Log.i("heejeong2", memberId.toString())
    }

    // 메소드가 static (companion object) 이면 안 됨
    fun receiveMessage(userId: String) {
        getUserTitles()
        Log.i("heejeong", "TransferWithUnity $userId")
        //받은 userId로 작업 진행 예시
        val userData = UserData(userId, "rocketman", "heejeong@gmail.com")
        sendMessage(userData)
    }

    /*
    * 유니티에 데이터 보내기
    */
    private fun sendMessage(userData: UserData) {
        val gson = Gson()
        val jsonMessage = gson.toJson(userData)
        val unityGameObject = "AndroidController"
        val unityMethod = "ReceiveMessage"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 보유한 유저 칭호 정보
     */
    private fun getUserTitles() {
        runBlocking {
            GlobalScope.launch {
                TitleApi.instance.getTitles()
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", "$data")
                        sendUserTitles(data)
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }

    /**
     * 나태괴물처치
     */
    private fun defeatLazyMonster() {
        runBlocking {
            GlobalScope.launch {
                MemberApi.instance.defeatLazyMonster()
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", "$data")
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }

    /**
     * 유저 캐릭터 아이템 정보 수정
     */
    fun modifyCharacterItems(userItems: MemberItemReqDto) {
        runBlocking {
            GlobalScope.launch {
                MemberApi.instance.modifyCharacterItems(userItems)
                    .onSuccess { (_, data) ->
//                        checkNotNull(data)
                        Log.i("heejeong", "$data")
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }

    /**
     * 유저 캐릭터 아이템 정보 조회
     */
    private fun getCharacterItems() {
        runBlocking {
            GlobalScope.launch {
                MemberApi.instance.getCharacterItems(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", "$data")
                        sendCharacterItems(data)
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }

    /**
     * 유저 상태 정보 조회
     */
    fun getMemberStatus() {
        runBlocking {
            GlobalScope.launch {
                MemberApi.instance.getMemberStatus(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", data.toString())
                        sendMemberStatus(data)
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }

    /**
     * 유저 능력치 조회
     */
    fun getMemberStats() {
        runBlocking {
            GlobalScope.launch {
                MemberApi.instance.getMemberStat(memberId)
                    .onSuccess { (_, data) ->
                        checkNotNull(data)
                        Log.i("heejeong", "$data")
                        val statInfo = StatResult(
                            life = data[0].score,
                            survival = data[1].score,
                            popularity = data[2].score,
                            power = data[3].score,
                            creative = data[4].score,
                            love = data[5].score,
                        )
                        Log.i("heejeong", "$statInfo")
                        sendMemberStats(statInfo)
                    }.onFailure { (_, message, _) ->
                        Log.e("heejeong", "실패! %n$message")
                    }
            }.join()
        }
    }


    /**
     * 보유한 유저 아이템 정보를 유니티로 전송
     */
    private fun sendCharacterItems(userItems: MemberItemResDto) {
        val gson = Gson()
        val jsonMessage = gson.toJson(userItems)
        val unityMethod = "ReceiveCharacterItems"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 유저 상태 정보를 유니티로 전송
     */
    private fun sendMemberStatus(indolence: MemberStatusResDto) {
        val gson = Gson()
        val jsonMessage = gson.toJson(indolence)
        val unityMethod = "ReceiveMemberStatus"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 유저 능력치 정보를 유니티로 전송
     */
    private fun sendMemberStats(memberStats: StatResult) {
        val gson = Gson()
        val jsonMessage = gson.toJson(memberStats)
        val unityMethod = "ReceiveMemberStats"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }


    /**
     * 보유한 유저 칭호들을 유니티로 전송
     */
    private fun sendUserTitles(titles: List<MemberTitlesResDto>) {
        val gson = Gson()
        val jsonMessage = gson.toJson(titles)
        val unityMethod = "ReceiveUserTitles"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

}