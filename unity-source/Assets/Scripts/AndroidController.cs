using System;
using System.Collections.Generic;
using TMPro;
using UnityEditor;
using UnityEngine;
using UnityEngine.UI;


public class AndroidController : MonoBehaviour
{
    public static AndroidController instance;

    AndroidJavaObject _pluginInstance;

    [SerializeField] private StatsRadarChart statsRadarChart;

    // Monster button
    public Button monsterButton;

    // Mypage Status
    public List<GameObject> bagList;
    public List<GameObject> glassesList;
    public List<GameObject> minihatList;
    public List<GameObject> scarfList;
    public Button titleObject;

    // Clothes Button Image List
    public List<Sprite> bagOn;
    public List<Sprite> glassesOn;
    public List<Sprite> minihatOn;
    public List<Sprite> scarfOn;
    public List<Sprite> titleSprites;

    public List<Button> bagButtons;
    public List<Button> glassesButtons;
    public List<Button> minihatButtons;
    public List<Button> scarfButtons;
    public List<Button> titleButtons;

    // 스텟의 레벨을 표시하는 텍스트
    public List<TextMeshProUGUI> statLevelList;

    void Awake()
    {
        // 데이터를 받을 안드로이드 플러그인의 클래스 주소
        string androidClass = "com.finale.neulhaerang.data.unity.TransferWithUnity";

        // 데이터를 받을 안드로이드 클래스를 인스턴스화
        _pluginInstance = new AndroidJavaObject(androidClass);

        // 싱글톤
        instance = this;
    }

    // Start is called before the first frame update
    void Start()
    {
        RequestMemberStats();
        RequestMemberStatus();
        //RequestDefeatMonster();
        RequestCharacterItems();
        //ModifyCharacterItems(new MemberItem(1, 2, 1, 1, 1));
        //RequestUserTitles();
        RequestGetUserProfile();

        //Debug.Log("stat Level : " + statLevelList[0].text);

        statLevelList.Add(GameObject.Find("GodsangLevel").GetComponent<TextMeshProUGUI>());
        statLevelList.Add(GameObject.Find("SurviveLevel").GetComponent<TextMeshProUGUI>());
        statLevelList.Add(GameObject.Find("InssaLevel").GetComponent<TextMeshProUGUI>());
        statLevelList.Add(GameObject.Find("TeunteunLevel").GetComponent<TextMeshProUGUI>());
        statLevelList.Add(GameObject.Find("GoodideaLevel").GetComponent<TextMeshProUGUI>());
        statLevelList.Add(GameObject.Find("LoveLevel").GetComponent<TextMeshProUGUI>());
    }

    // Update is called once per frame
    void Update()
    {

    }

    // 메소드가 static이면 안 됨
    /**
     * 멤버 능력치 조회
     */
    void RequestMemberStats()
    {
        string androidMethod = "getMemberStats";
        _pluginInstance.Call(androidMethod);
    }

    void ReceiveMemberStats(string jsonMessage)
    {
        //// StartStat

        int[] scores = new int[6];

        Debug.Log("heejeong [ReceiveMemberStats]" + jsonMessage);
        MemberStats datas = JsonUtility.FromJson<MemberStats>(jsonMessage);

        int index = 0;
        foreach (MemberStatItem lt in datas.stats)
        {
            Debug.Log("heejeong 유저 스탯 점수::" + lt.Score);
            Debug.Log("heejeong 유저 스탯 레벨::" + lt.Level);
            scores[index] = lt.Score;
            statLevelList[index++].text = lt.Level;
            Debug.Log("stat Level : " + statLevelList[index - 1].text);
            /*TODO*/
            // Level UI 생성 및 값 표시 작업 필요
        }
        scores = changeValue(scores);

        //// godsang
        //scores[0] = 2500;
        //// survive
        //scores[1] = 2000;
        //// inssa
        //scores[2] = 1500;
        //// teunteun
        //scores[3] = 2000;
        //// goodidea
        //scores[4] = 2500;
        //// love
        //scores[5] = 1500;

        Debug.Log("score 0 : " + scores[0]);
        Debug.Log("score 1 : " + scores[1]);
        Debug.Log("score 2 : " + scores[2]);
        Debug.Log("score 3 : " + scores[3]);
        Debug.Log("score 4 : " + scores[4]);
        Debug.Log("score 5 : " + scores[5]);

        Stats stats = new Stats(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5]);
        statsRadarChart.SetStats(stats);
    }

    /*
    * Radar chart 에 맞게 수치 조정
    * A+ => 2500       150
    * A  => 2100       120
    * B+ => 1700       90
    * B  => 1300       60
    * C+ => 900        30
    * C  => 500        0
    */
    private int[] changeValue(int[] scores)
    {
        for (int i = 0; i < 6; i++)
        {
            if (scores[i] >= 150) scores[i] = 2000;
            else
            {
                scores[i] = scores[i] * 2000 / 150;
            }
            scores[i] += 500;
            Debug.Log("change score value - index : " + i + "  value : " + scores[i]);
        }
        return scores;
    }

    /**
     * 멤버 상태 조회
     */
    void RequestMemberStatus()
    {
        string androidMethod = "getMemberStats";
        _pluginInstance.Call(androidMethod);
    }
    void ReceiveMemberStatus(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveMemberStatus]" + jsonMessage);
        MemberStatus datas = JsonUtility.FromJson<MemberStatus>(jsonMessage);

        Debug.Log("heejeong 나태도" + datas.Indolence);
        Debug.Log("heejeong 피로도" + datas.Tiredness);

        // 나태도가 50 이하면 나태괴물 안나타나게
        if (monsterButton != null && datas.Indolence < 50)
        {
            Image buttonImage = monsterButton.GetComponent<Image>();
            Color newColor = buttonImage.color;
            newColor.a = 0.0f;
            buttonImage.color = newColor;
        }

    }


    /**
     * 나태 괴물 처치 완료
     */
    public void RequestDefeatMonster()
    {
        string androidMethod = "defeatLazyMonster";
        _pluginInstance.Call(androidMethod);
    }

    /**
     * 유저 장착 아이템 조회
     */
    void RequestCharacterItems()
    {
        string androidMethod = "getCharacterItems";
        _pluginInstance.Call(androidMethod);
    }

    void ReceiveCharacterItems(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveCharacterItems]" + jsonMessage);
        MemberItem datas = JsonUtility.FromJson<MemberItem>(jsonMessage);
        Debug.Log("heejeong 착장한 사용자 아이템 ::" + datas.ToString());

        /*
         * MemberItem list
         * 
         * backpack;
         * glasses;
         * hat;
         * scarf;
         * title;
         */

        // 아이템을 장착했다면 Active

        if (datas.Backpack != 0)
        {
            bagList[datas.Backpack].SetActive(true);
            bagButtons[datas.Backpack].GetComponent<Image>().sprite = bagOn[datas.Backpack];
        }

        if (datas.Glasses != 0)
        {
            glassesList[datas.Glasses].SetActive(true);
            glassesButtons[datas.Glasses].GetComponent<Image>().sprite = glassesOn[datas.Glasses];
        }

        if (datas.Hat != 0)
        {
            minihatList[datas.Hat].SetActive(true);
            minihatButtons[datas.Hat].GetComponent<Image>().sprite = minihatOn[datas.Hat];
        }

        if (datas.Scarf != 0)
        {
            scarfList[datas.Scarf].SetActive(true);
            scarfButtons[datas.Scarf].GetComponent<Image>().sprite = scarfOn[datas.Scarf];
        }

        if (datas.Title != 0)
        {
            // transparent
            Image buttonImage = titleObject.GetComponent<Image>();
            Color newColor = buttonImage.color;
            newColor.a = 1.0f;
            buttonImage.color = newColor;
            Debug.Log("color : " + newColor);
            titleObject.GetComponent<Image>().sprite = titleSprites[datas.Title];
        }

        // 아이템 전역 저장
        PlayerPrefs.SetInt("Bag", datas.Backpack);
        PlayerPrefs.SetInt("Glasses", datas.Glasses);
        PlayerPrefs.SetInt("Minihat", datas.Hat);
        PlayerPrefs.SetInt("Scarf", datas.Scarf);
        PlayerPrefs.SetInt("Title", datas.Title);
    }

    /**
     * 유저 장착 아이템 수정
     */
    public void ModifyCharacterItems(MemberItem item)
    {
        string datas = JsonUtility.ToJson(item);
        Debug.Log("heejeong 유저 아이템 수정 목록::" + item.ToString());
        string androidMethod = "modifyCharacterItems";
        _pluginInstance.Call(androidMethod, datas);
    }

    /**
     * 유저 보유 칭호 조회
     */
    void RequestUserTitles()
    {
        string androidMethod = "getUserTitles";
        _pluginInstance.Call(androidMethod);
    }

    void ReceiveUserTitles(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveUserTitles]" + jsonMessage);
        MemberTitles datas = JsonUtility.FromJson<MemberTitles>(jsonMessage);
        Debug.Log("heejeong 착장한 사용자 아이템 ::" + datas.ToString());

        foreach (MemberTitle lt in datas.titles)
        {
            Debug.Log("heejeong 유저 보유 칭호 목록::" + lt.TitleId);
            Debug.Log("heejeong 유저 보유 칭호 목록::" + lt.Content);
        }

        /*TODO*/
    }

    /**
     * 주위 사용자 조회
     */
    public void RequestNearByUsers()
    {
        string androidMethod = "getNearByUsers";
        _pluginInstance.Call(androidMethod);
    }

    public void ReceiveNearByUsers(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveNearByUsers]" + jsonMessage);
        AroundMembers datas = JsonUtility.FromJson<AroundMembers>(jsonMessage);
        foreach (AroundMember it in datas.members)
        {
            Debug.Log("heejeong 근처 사용자::" +it.ToString());
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

    /**
    * 유저 프로필 조회 (레벨, 경험치, 닉네임)
    */
    void RequestGetUserProfile()
    {
        string androidMethod = "getUserProfile";
        _pluginInstance.Call(androidMethod);
    }

    void ReceiveGetUserProfile(string jsonMessage)
    {
        Debug.Log("junyeong [ReceiveGetUserProfile]" + jsonMessage);
        MemberProfile datas = JsonUtility.FromJson<MemberProfile>(jsonMessage);
        Debug.Log("junyeong 사용자 레벨과 경험치 ::" + datas.ToString());

        Debug.Log("junyeong 사용자 레벨" + datas.Level);
        Debug.Log("junyeong 사용자 다음 경험치" + datas.NxtExp);
        Debug.Log("junyeong 사용자 현재 경험치" + datas.CurExp);
        Debug.Log("junyeong 사용자 닉네임" + datas.Nickname);
    }
}
