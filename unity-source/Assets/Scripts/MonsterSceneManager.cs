using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MonsterSceneManager : MonoBehaviour
{
    public GameObject ElementalSuperDemonDark;
    public GameObject ElementalSuperDemonFire;
    public GameObject ElementalSuperDemonWater;

    void Update()
    {
        if(!ElementalSuperDemonDark.activeSelf && !ElementalSuperDemonFire.activeSelf && !ElementalSuperDemonWater.activeSelf)
        {
            Invoke("GotoGameOverScene", 1.0f);
        }
    }

    void GotoGameOverScene()
    {
        // 안드로이드 나태 괴물 처치 API 함수 호출
        GoSceneManager goSceneManager = GameObject.Find("MonsterSceneManager").GetComponent<GoSceneManager>();
        goSceneManager.GotoSceneSingle("GameOverScene");
    }
}
