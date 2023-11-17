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

    [SerializeField] private SocialStatsRadarChart1 statsRadarChart1;
    [SerializeField] private SocialStatsRadarChart2 statsRadarChart2;
    [SerializeField] private SocialStatsRadarChart3 statsRadarChart3;

    public GameObject[] Character;
    public GameObject[] CharaterInfo;
    public Image[] TitleImage;

    public GameObject[] Bags;
    public GameObject[] Glasses;
    public GameObject[] Scarfs;
    public GameObject[] Hats;

    public AroundMembers data;

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
        Debug.Log("junyeong social start");
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
    public void RequestFriendStats(long memberId)
    {
        Debug.Log("heejeong SocialController Call: RequestFriendStats(" + memberId+")");

        /*
         *  memberId를 기억하지 않으므로, response에서 어떤 캐릭터의 스텟을 보여줄건지 기억해야함.
         */

        if (!string.IsNullOrEmpty(PlayerPrefs.GetString("MemberId1")) && memberId == long.Parse(PlayerPrefs.GetString("MemberId1")))
        {
            PlayerPrefs.SetInt("clickMember", 1);
        }
        else if (!string.IsNullOrEmpty(PlayerPrefs.GetString("MemberId2")) && memberId == long.Parse(PlayerPrefs.GetString("MemberId2")))
        {
            PlayerPrefs.SetInt("clickMember", 2);
        }
        else if (!string.IsNullOrEmpty(PlayerPrefs.GetString("MemberId3")) && memberId == long.Parse(PlayerPrefs.GetString("MemberId3")))
        {
            PlayerPrefs.SetInt("clickMember", 3);
        }
        else
        {
            // MemberId가 안들어왔을 때, 더미 데이터를 추가하기 위한 용도
            PlayerPrefs.SetInt("clickMember", 0);
        }
        
        PlayerPrefs.Save();

        string androidMethod = "getFriendStats";
        _pluginInstance.Call(androidMethod, memberId);
    }

    void ReceiveFriendStats(string jsonMessage)
    {
        int[] scores = new int[6];

        Debug.Log("heejeong [ReceiveFriendStats]" + jsonMessage);
        MemberStats datas = JsonUtility.FromJson<MemberStats>(jsonMessage);

        for (var i = 0; i < scores.Length; i++)
        {
            Debug.Log("heejeong 유저 스탯 점수::" + datas.stats[i].Score);
            Debug.Log("stat type : " + datas.stats[i].Score.GetType().Name);
            scores[i] = datas.stats[i].Score;
        }

        scores = changeValue(scores);
        Stats stats = new Stats(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5]);

        if (PlayerPrefs.GetInt("clickMember") == 1)
        {
            statsRadarChart1.SetStats(stats);
        }
        else if (PlayerPrefs.GetInt("clickMember") == 2)
        {
            statsRadarChart2.SetStats(stats);
        }
        else if (PlayerPrefs.GetInt("clickMember") == 3)
        {
            statsRadarChart3.SetStats(stats);
        }
        else
        {
            // 더미데이터
            statsRadarChart1.SetStats(stats);
            statsRadarChart2.SetStats(stats);
            statsRadarChart3.SetStats(stats);
        }
    }

    /*
    * Social Radar chart 에 맞게 수치 조정
    * A+ => 310       150
    * A  => 260       120
    * B+ => 210       90
    * B  => 160       60
    * C+ => 110        30
    * C  => 50          0
    */
    private int[] changeValue(int[] scores)
    {
        for (int i = 0; i < 6; i++)
        {
            if (scores[i] >= 150) scores[i] = 310;
            else
            {
                scores[i] = scores[i] * 31 / 15;
            }
            scores[i] += 50;
            scores[i] /= 4;
            Debug.Log("junyeong " + i + "  value : " + scores[i]);
        }
        return scores;
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
        AroundMembers data = JsonUtility.FromJson<AroundMembers>(jsonMessage);

        // 초기화
        for (int i = 0; i < 3; i++)
        {
            Character[i].SetActive(false);
            CharaterInfo[i].SetActive(false);
        }

        int size = data.members.Length;

        for (int i = 0; i < size; i++)
        {
            Debug.Log("junyeong MemberId" + data.members[i].MemberId);
            Transform transform = Character[i].transform.Find("Root_M/Spine1_M/Chest_M");
            GameObject Back = transform.Find("BagA").gameObject;
            GameObject Neck = transform.Find("Neck_M/ScarfA").gameObject;
            GameObject Face = transform.Find("Neck_M/Head_M/GlassesA").gameObject;
            GameObject Head = transform.Find("Neck_M/Head_M/MinihatA").gameObject;

            int index = i;

            if (data.members[i].Title != 0)
            {
                // 에디터에서 프로젝트 경로에 있는 이미지의 경로
                Sprite sprite = Resources.Load<Sprite>("TitleImage/on/on_" + data.members[i].Title.ToString() + ".png");
                //Sprite sprite = Resources.Load<Sprite>("TitleImage/on/on_4");

                if (sprite != null)
                {
                    TitleImage[index].sprite = sprite;
                }
                else
                {
                    Debug.LogError("Failed to load image");
                    TitleImage[index].enabled = false;
                }
            }

            if (data.members[i].Scarf != 0)
            {
                //Instantiate a new Hat Prefab
                GameObject instantiatedNeck = Instantiate(Scarfs[data.members[i].Scarf - 1], transform.Find("Neck_M"));
                //GameObject instantiatedNeck = Instantiate(Scarfs[2], transform.Find("Neck_M"));
                instantiatedNeck.transform.position = Neck.transform.position;
                instantiatedNeck.transform.rotation = Neck.transform.rotation;
                //instantiatedNeck.name = "ScarfA";
                instantiatedNeck.SetActive(true);
            }

            if (data.members[i].Backpack != 0)
            {
                GameObject instantiatedBack = Instantiate(Bags[data.members[i].Backpack - 1], transform);
                //GameObject instantiatedBack = Instantiate(Bags[7], transform);
                instantiatedBack.transform.position = Back.transform.position;
                instantiatedBack.transform.rotation = Back.transform.rotation;
                //instantiatedBack.name = "BagA";
                instantiatedBack.SetActive(true);
            }
            if (data.members[i].Glasses != 0)
            {
                GameObject instantiatedFace = Instantiate(Glasses[data.members[i].Glasses - 1], transform.Find("Neck_M/Head_M"));
                //GameObject instantiatedFace = Instantiate(Glasses[1], transform.Find("Neck_M/Head_M"));
                instantiatedFace.transform.position = Face.transform.position;
                instantiatedFace.transform.rotation = Face.transform.rotation;
                //instantiatedFace.name = "GlassesA";
                instantiatedFace.SetActive(true);
            }
            if (data.members[i].Hat != 0)
            {
                GameObject instantiatedHead = Instantiate(Hats[data.members[i].Hat - 1], transform.Find("Neck_M/Head_M"));
                //GameObject instantiatedHead = Instantiate(Hats[2], transform.Find("Neck_M/Head_M"));
                instantiatedHead.transform.position = Head.transform.position;
                instantiatedHead.transform.rotation = Head.transform.rotation;
                //instantiatedHead.name = "HatA";
                instantiatedHead.SetActive(true);
            }

            Destroy(Back);
            Destroy(Neck);
            Destroy(Face);
            Destroy(Head);

            Character[i].SetActive(true);

            // MemberId 전역 저장
            if (i == 0) { PlayerPrefs.SetString("MemberId1", data.members[i].MemberId.ToString()); }
            if (i == 1) { PlayerPrefs.SetString("MemberId2", data.members[i].MemberId.ToString()); }
            if (i == 2) { PlayerPrefs.SetString("MemberId3", data.members[i].MemberId.ToString()); }
            PlayerPrefs.Save();
        }

        foreach (AroundMember it in data.members)
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
