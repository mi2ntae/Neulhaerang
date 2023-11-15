using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GoSceneManager : MonoBehaviour
{
    public void GotoSceneSingle(string sceneName)
    {
        SceneManager.LoadScene(sceneName, LoadSceneMode.Single);

        // 서버로 잡았다고 전송
        //var andController = new AndroidController();
        //andController.RequestDefeatMonster();

        AndroidController.instance.RequestDefeatMonster();
    }

    public void GotoSceneAdditive(string sceneName)
    {
        SceneManager.LoadScene(sceneName, LoadSceneMode.Additive);
    }

    public void Quit()
    {
        Application.Quit();
    }
}
