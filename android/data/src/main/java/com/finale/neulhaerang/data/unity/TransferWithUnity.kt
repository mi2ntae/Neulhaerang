package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.DataStoreApplication
import com.finale.neulhaerang.data.api.ArApi
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.api.TitleApi
import com.finale.neulhaerang.data.model.request.MemberItemReqDto
import com.finale.neulhaerang.data.model.response.AroundMemberCharacterResDto
import com.finale.neulhaerang.data.model.response.MemberItemResDto
import com.finale.neulhaerang.data.model.response.MemberStatResDto
import com.finale.neulhaerang.data.model.response.MemberStatusResDto
import com.finale.neulhaerang.data.model.response.MemberTitlesResDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.google.gson.Gson
import com.unity3d.player.UnityPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TransferWithUnity {
    // 데이터를 받을 유니티 스크립트가 컴포넌트로 붙어 있는 게임오브젝트명
    private val unityGameObject = "AndroidController"
    private var memberId: Long = 0
    private val gson = Gson()

    init {
        Log.i("heejeong", "TransferWithUnity Init")
        Log.i("heejeong1", memberId.toString())
        runBlocking {
            CoroutineScope(Dispatchers.Main).launch {
                memberId =
                    DataStoreApplication.getInstance().getDataStore().getMemberId().firstOrNull()
                        ?: 0
            }.join()
        }
        Log.i("heejeong2", memberId.toString())
    }

    /**
     * 보유한 유저 칭호 정보
     */
    private fun getUserTitles() {
        runBlocking {
            TitleApi.instance.getTitles()
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "$data")
                    sendUserTitles(data)
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 나태 괴물 처치
     */
    private fun defeatLazyMonster() {
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

    /**
     * 유저 캐릭터 아이템 정보 수정
     */
    fun modifyCharacterItems(jsonMessage: String) {
        val memberItem = gson.fromJson(jsonMessage, MemberItemReqDto::class.java)
        Log.i("heejeong", "$memberItem")
        runBlocking {
            MemberApi.instance.modifyCharacterItems(memberItem)
                .onSuccess { (_, data) ->
//                        checkNotNull(data)
                    Log.i("heejeong", "$data")
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 유저 캐릭터 아이템 정보 조회
     */
    private fun getCharacterItems() {
        runBlocking {
            MemberApi.instance.getCharacterItems(memberId)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "$data")
                    sendCharacterItems(data)
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }


    /**
     * 유저 상태 정보 조회
     */
    fun getMemberStatus() {
        runBlocking {
            MemberApi.instance.getMemberStatus(memberId)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", data.toString())
                    sendMemberStatus(data)
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 유저 능력치 조회
     */
    fun getMemberStats() {
        runBlocking {
            MemberApi.instance.getMemberStat(memberId)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "$data")
                    sendMemberStats(data)
//                        val statInfo = StatResult(
//                            life = data[0].score,
//                            survival = data[1].score,
//                            popularity = data[2].score,
//                            power = data[3].score,
//                            creative = data[4].score,
//                            love = data[5].score,
//                        )
//                        Log.i("heejeong", "$statInfo")
//                        sendMemberStats(statInfo)
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 유저 근처 다른 사용자 정보 조회
     */
    fun getNearByUsers() {
        runBlocking {
            ArApi.instance.getAroundMembers()
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "getAroundMembers $data")
                    sendNearByUsers(data)
                }
                .onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 보유한 유저 아이템 정보를 유니티로 전송
     */
    private fun sendCharacterItems(userItems: MemberItemResDto) {
        val jsonMessage = gson.toJson(userItems)
        val unityMethod = "ReceiveCharacterItems"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 유저 상태 정보를 유니티로 전송
     */
    private fun sendMemberStatus(indolence: MemberStatusResDto) {
        val jsonMessage = gson.toJson(indolence)
        val unityMethod = "ReceiveMemberStatus"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 유저 능력치 정보를 유니티로 전송
     */
    private fun sendMemberStats(stats: List<MemberStatResDto>) {
        val jsonTitles = StatsWrapper(stats)
        val jsonMessage = gson.toJson(jsonTitles)
        Log.i("heejeong", "sendMemberStats $jsonMessage")
        val unityMethod = "ReceiveMemberStats"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }
//    private fun sendMemberStats(memberStats: StatResult) {
//        val jsonMessage = gson.toJson(memberStats)
//        val unityMethod = "ReceiveMemberStats"
//        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
//    }

    /**
     * 보유한 유저 칭호들을 유니티로 전송
     */
    private fun sendUserTitles(titles: List<MemberTitlesResDto>) {
        val jsonTitles = TitlesWrapper(titles)
        val jsonMessage = gson.toJson(jsonTitles)
        Log.i("heejeong", "sendUserTitles $jsonMessage")
        val unityMethod = "ReceiveUserTitles"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    /**
     * 근처 다른 유저 정보들을 유니티로 전송
     */
    private fun sendNearByUsers(members: List<AroundMemberCharacterResDto>) {
        val jsonMembers = MemberWrapper(members)
        val jsonMessage = gson.toJson(jsonMembers)
        Log.i("heejeong", "sendNearByUsers $jsonMessage")
        val unityMethod = "ReceiveNearByUsers"
        UnityPlayer.UnitySendMessage(unityGameObject, unityMethod, jsonMessage)
    }

    data class TitlesWrapper(val titles: List<MemberTitlesResDto>)
    data class StatsWrapper(val stats: List<MemberStatResDto>)
    data class MemberWrapper(val members: List<AroundMemberCharacterResDto>)
}
