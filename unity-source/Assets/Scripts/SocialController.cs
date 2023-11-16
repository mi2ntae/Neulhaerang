using System;
using System.Collections.Generic;
using TMPro;
using Unity.VisualScripting;
using UnityEditor;
using UnityEngine;
using UnityEngine.UI;

public class SocialController : MonoBehaviour
{
    public static SocialController instance;

    AndroidJavaObject _pluginInstance;
   

    void Awake()
    {
        // 데이터를 받을 안드로이드 플러그인의 클래스 주소
        string androidClass = "com.finale.neulhaerang.data.unity.SocialWithUnity";

        // 데이터를 받을 안드로이드 클래스를 인스턴스화
        _pluginInstance = new AndroidJavaObject(androidClass);

        // 싱글톤
        instance = this;
    }

    // Start is called before the first frame update
    void Start()
    {
        RequestNearByUsers();
        RequestFriendStats(4);
    }

    // Update is called once per frame
    void Update()
    {

    }

    // 메소드가 static이면 안 됨
    

    /**
     * 클릭한 사용자 상태 조회
     */
    void RequestFriendStats(long memberId)
    {
        Debug.Log("heejeong SocialController Call: RequestFriendStats(" + memberId+")");
        string androidMethod = "getFriendStats";
        _pluginInstance.Call(androidMethod, memberId);
    }

    void ReceiveFriendStats(string jsonMessage)
    {
        int[] scores = new int[6];

        Debug.Log("heejeong [ReceiveFriendStats]" + jsonMessage);
        MemberStats datas = JsonUtility.FromJson<MemberStats>(jsonMessage);

        //for (var i = 0; i < scores.Length; i++)
        //{
        //    Debug.Log("heejeong 유저 스탯 점수::" + datas.stats[i].Score);
        //    Debug.Log("stat type : " + datas.stats[i].Score.GetType().Name);
        //    Debug.Log("heejeong 유저 스탯 레벨::" + datas.stats[i].Level);
        //    Debug.Log("level type : " + datas.stats[i].Level.GetType().Name);
        //}
    }

    /**
     * 주위 사용자 조회
     */
    public void RequestNearByUsers()
    {
        Debug.Log("heejeong SocialController Call: RequestNearByUsers");
        string androidMethod = "getNearByUsers";
        _pluginInstance.Call(androidMethod);
    }

    public void ReceiveNearByUsers(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveNearByUsers]" + jsonMessage);
        AroundMembers datas = JsonUtility.FromJson<AroundMembers>(jsonMessage);
        foreach (AroundMember it in datas.members)
        {
            Debug.Log("heejeong 근처 사용자::" + it.ToString());
        }
    }


    /**
     * 주위 사용자 클릭 시
     */
    void RequestClickOtherUser(long memberId)
    {
        Debug.Log("heejeong [RequestClickOtherUser]" + memberId);
        string androidMethod = "clickOtherUser";
        _pluginInstance.Call(androidMethod, memberId);
    }

}
