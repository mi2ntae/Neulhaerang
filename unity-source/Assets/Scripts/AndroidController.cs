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
        RequestMemberStats();
        RequestMemberStatus();
    }

    // Update is called once per frame
    void Update()
    {

    }

    //스탯 조회

    void RequestMemberStats()
    {
        string androidMethod = "getMemberStats";
        _pluginInstance.Call(androidMethod);
    }

    // 메소드가 static이면 안 됨
    void ReceiveMemberStats(string jsonMessage)
    {
        Debug.Log("heejeong ReceiveMemberStats");
        MemberStat datas = JsonUtility.FromJson<MemberStat>(jsonMessage);

        //수신한 데이터로 작업 예시: 로그 띄우기
        Debug.Log("heejeong Life" + datas.Life);
        Debug.Log("heejeong Love" + datas.Love);
        Debug.Log("heejeong Survival" + datas.Survival);
        Debug.Log("heejeong Popularity" + datas.Popularity);
        Debug.Log("heejeong Power" + datas.Power);
        Debug.Log("heejeong Creative" + datas.Creative);

        //foreach (var data in datas)
        //{
        //    Debug.Log("heejeong" + data.ToString());
        //}
    }


    //스탯 수정

    //상태 조회

    void RequestMemberStatus()
    {
        string androidMethod = "getMemberStatus";
        _pluginInstance.Call(androidMethod);
    }

    // 메소드가 static이면 안 됨
    void ReceiveMemberStatus(string jsonMessage)
    {
        //수신한 JSON 데이터를 UserData객체로 역직렬화 (UnityEngine 라이브러리 사용)
        MemberStatus datas = JsonUtility.FromJson<MemberStatus>(jsonMessage);

        //수신한 데이터로 작업 예시: 로그 띄우기
        Debug.Log("heejeong 나태도" + datas.Indolence);
        Debug.Log("heejeong 피로도" + datas.Tiredness);
    }

    //나태 괴물 처치 완료

    //유저 아이템 조회

    //유저 아이템 수정
}
