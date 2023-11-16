package com.finale.neulhaerang.data.unity

import android.util.Log
import com.finale.neulhaerang.data.api.ArApi
import com.finale.neulhaerang.data.api.MemberApi
import com.finale.neulhaerang.data.model.response.AroundMemberCharacterResDto
import com.finale.neulhaerang.data.model.response.MemberStatResDto
import com.finale.neulhaerang.data.util.onFailure
import com.finale.neulhaerang.data.util.onSuccess
import com.google.gson.Gson
import com.unity3d.player.UnityPlayer
import kotlinx.coroutines.runBlocking

class SocialWithUnity {
    // 데이터를 받을 유니티 스크립트가 컴포넌트로 붙어 있는 게임오브젝트명
    private val unityGameObject = "AroundPeopleManager"
    private val gson = Gson()

    init {
    }

    /**
     * 친구 능력치 조회
     */
    fun getFriendStats(memberId: Long) {
        Log.i("heejeong", "[SocialWithUnity] getMemberStats - memberId : $memberId")
        runBlocking {
            MemberApi.instance.getMemberStat(memberId)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "$data")
                    sendFriendStats(data)
                }.onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 유저 근처 다른 사용자 정보 조회
     */
    fun getNearByUsers() {
        Log.i("heejeong", "[SocialWithUnity] getNearByUsers")
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
     * 소셜 사용자 클릭 시
     */
    fun clickOtherUser(memberId: Long) {
//        val memberId = gson.fromJson(jsonMessage)
        Log.i("heejeong", "clickOtherUser memberId $memberId")
        runBlocking {
            ArApi.instance.sendClickedMember(memberId)
                .onSuccess { (_, data) ->
                    checkNotNull(data)
                    Log.i("heejeong", "clickOtherUser Result $data")
                }
                .onFailure { (_, message, _) ->
                    Log.e("heejeong", "실패! %n$message")
                }
        }
    }

    /**
     * 소셜 유저 능력치 정보를 유니티로 전송
     */
    private fun sendFriendStats(stats: List<MemberStatResDto>) {
        val jsonTitles = StatsWrapper(stats)
        val jsonMessage = gson.toJson(jsonTitles)
        Log.i("heejeong", "sendFriendStats $jsonMessage")
        val unityMethod = "ReceiveFriendStats"
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
    data class StatsWrapper(val stats: List<MemberStatResDto>)
    data class MemberWrapper(val members: List<AroundMemberCharacterResDto>)
}
