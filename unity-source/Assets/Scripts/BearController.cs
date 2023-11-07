using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BearController : MonoBehaviour
{
    private int score = 0;

    void Start()
    {
        // 초기화할 내용을 작성합니다.
    }

    void Update()
    {
        // 업데이트할 내용을 작성합니다.
    }

    void OnMouseDown()
    {
        // 오브젝트를 터치했을 때 호출됩니다.
        IncreaseScore();
    }

    void IncreaseScore()
    {
        score++;
        Debug.Log("Score: " + score);

        // 여기에서 점수를 업데이트하고 필요한 작업을 수행합니다.
    }
}
