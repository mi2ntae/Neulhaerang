using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FirstMemberController : MonoBehaviour
{
    public GameObject FirstMemberInfo;
    public float animationDuration = 0.5f; // 애니메이션 지속 시간
    private Vector3 initialScale;

    void Start()
    {
        initialScale = FirstMemberInfo.transform.localScale;
    }

    void OnMouseDown()
    {
        StartCoroutine(AnimateInfoPanel());
    }

    IEnumerator AnimateInfoPanel()
    {
        if (!FirstMemberInfo.activeSelf)
        {
            FirstMemberInfo.SetActive(true);

            float elapsedTime = 0f;
            while (elapsedTime < animationDuration)
            {
                float t = elapsedTime / animationDuration;
                FirstMemberInfo.transform.localScale = Vector3.Lerp(Vector3.zero, initialScale, t);
                elapsedTime += Time.deltaTime;
                yield return null;
            }

            FirstMemberInfo.transform.localScale = initialScale;
        }
        else
        {
            float elapsedTime = 0f;
            while (elapsedTime < animationDuration)
            {
                float t = elapsedTime / animationDuration;
                FirstMemberInfo.transform.localScale = Vector3.Lerp(initialScale, Vector3.zero, t);
                elapsedTime += Time.deltaTime;
                yield return null;
            }

            FirstMemberInfo.transform.localScale = Vector3.zero;
            FirstMemberInfo.SetActive(false);
        }
    }
}
