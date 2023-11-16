using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.UI;

public class AroundPeopleManager : MonoBehaviour
{
    public GameObject[] Character;
    public GameObject[] CharaterInfo;
    public Image[] TitleImage;

    public GameObject[] Bags;
    public GameObject[] Glasses;
    public GameObject[] Scarfs;
    public GameObject[] Hats;

    public AroundMembers data;

    public static AroundPeopleManager instance;

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

    void Start()
    {
        for(int i = 0; i < 3; i++)
        {
            Character[i].SetActive(false);
            CharaterInfo[i].SetActive(false);
        }

        RequestNearByUsers();
        int size = data.members.Length;

        Debug.Log("heejeong "+size);

        for(int i = 0; i < size; i++)
        {
            Transform transform = Character[i].transform.Find("Root_M/Spine1_M/Chest_M");
            GameObject Back = transform.Find("BagA").gameObject;
            GameObject Neck = transform.Find("Neck_M/ScarfA").gameObject;
            GameObject Face = transform.Find("Neck_M/Head_M/GlassesA").gameObject;
            GameObject Head = transform.Find("Neck_M/Head_M/MinihatA").gameObject;

            int index = i;

            if (data.members[i].Title != 0)
            {
                // 에디터에서 프로젝트 경로에 있는 이미지의 경로
                Sprite sprite = Resources.Load<Sprite>("TitleImage/on/on_"+data.members[i].Title.ToString()+".png");

                if (sprite != null)
                {
                    TitleImage[index].sprite = sprite;
                }
                else
                {
                    Debug.LogError("Failed to load image");
                }
            }

            Debug.Log("heejeong Title Success");

            if (data.members[i].Scarf != 0)
            {
                GameObject instantiatedNeck = Instantiate(Scarfs[data.members[i].Scarf-1], transform.Find("Neck_M"));
                instantiatedNeck.transform.position = Neck.transform.position;
                instantiatedNeck.transform.rotation = Neck.transform.rotation;
                instantiatedNeck.SetActive(true);
            }

            Debug.Log("heejeong Scarf Success");

            if (data.members[i].Backpack != 0)
            {
                GameObject instantiatedBack = Instantiate(Bags[data.members[i].Backpack-1], transform);
                instantiatedBack.transform.position = Back.transform.position;
                instantiatedBack.transform.rotation = Back.transform.rotation;
                instantiatedBack.SetActive(true);
            }

            Debug.Log("heejeong Backpack Success");

            if (data.members[i].Glasses != 0)
            {
                GameObject instantiatedFace = Instantiate(Bags[data.members[i].Glasses - 1], transform.Find("Neck_M/Head_M"));
                instantiatedFace.transform.position = Face.transform.position;
                instantiatedFace.transform.rotation = Face.transform.rotation;
                instantiatedFace.SetActive(true);
            }

            Debug.Log("heejeong Glasses Success");

            if (data.members[i].Hat != 0)
            {
                GameObject instantiatedHead = Instantiate(Bags[data.members[i].Hat - 1], transform.Find("Neck_M/Head_M"));
                instantiatedHead.transform.position = Head.transform.position;
                instantiatedHead.transform.rotation = Head.transform.rotation;
                instantiatedHead.SetActive(true);
            }

            Debug.Log("heejeong Hat Success");

            Destroy(Back);
            Destroy(Neck);
            Destroy(Face);
            Destroy(Head);

            Character[i].SetActive(true);
        }
    }

    /**
     * 클릭한 사용자 상태 조회
     */
    void RequestFriendStats(long memberId)
    {
        Debug.Log("heejeong SocialController Call: RequestFriendStats(" + memberId + ")");
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
        data = JsonUtility.FromJson<AroundMembers>(jsonMessage);
        foreach (AroundMember it in data.members)
        {
            Debug.Log("heejeong 근처 사용자::" + it.ToString());
        }
    }

    /**
     * 주위 사용자 클릭 시 사용자 인싸력 상승
     */
    void RequestClickOtherUser(long memberId)
    {
        Debug.Log("heejeong [RequestClickOtherUser]" + memberId);
        string androidMethod = "clickOtherUser";
        _pluginInstance.Call(androidMethod, memberId);
    }
}
