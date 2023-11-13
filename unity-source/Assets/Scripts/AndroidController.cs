using UnityEditor;
using UnityEngine;

public class AndroidController : MonoBehaviour
{
    AndroidJavaObject _pluginInstance;

    void Awake()
    {
        // 데이터를 받을 안드로이드 플러그인의 클래스 주소
        string androidClass = "com.finale.neulhaerang.data.unity.TransferWithUnity";

        // 데이터를 받을 안드로이드 클래스를 인스턴스화
        _pluginInstance = new AndroidJavaObject(androidClass);
    }

    // Start is called before the first frame update
    void Start()
    {
        //RequestMemberStats();
        //RequestMemberStatus();
        //RequestDefeatMonster();
        //RequestCharacterItems();
        //ModifyCharacterItems(new MemberItem(1,2,1,1,1));
        RequestUserTitles();
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
        Debug.Log("heejeong [ReceiveMemberStats]" + jsonMessage);
        MemberStat datas = JsonUtility.FromJson<MemberStat>(jsonMessage);
        Debug.Log("heejeong Life" + datas.ToString());
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
    }


    /**
     * 나태 괴물 처치 완료
     */
    void RequestDefeatMonster()
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
    }

    /**
     * 유저 장착 아이템 수정
     */
    void ModifyCharacterItems(MemberItem item)
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
    }
}
