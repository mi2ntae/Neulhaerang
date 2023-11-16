using System;
using System.Collections.Generic;
using TMPro;
using Unity.VisualScripting;
using UnityEditor;
using UnityEngine;
using UnityEngine.UI;

public class MonsterController : MonoBehaviour
{
    public static MonsterController instance;

    AndroidJavaObject _pluginInstance;


    void Awake()
    {
        // 데이터를 받을 안드로이드 플러그인의 클래스 주소
        string androidClass = "com.finale.neulhaerang.data.unity.MonsterWithUnity";

        // 데이터를 받을 안드로이드 클래스를 인스턴스화
        _pluginInstance = new AndroidJavaObject(androidClass);

        // 싱글톤
        instance = this;
    }

    // Start is called before the first frame update
    void Start()
    {

    }


    /**
     * 나태 괴물 처치 완료
     */
    public void RequestDefeatMonster()
    {
        Debug.Log("heejeong MonsterController Call: RequestDefeatMonster");
        string androidMethod = "defeatLazyMonster";
        _pluginInstance.Call(androidMethod);
    }

}
