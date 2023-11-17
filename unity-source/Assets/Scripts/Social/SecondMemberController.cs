using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SecondMemberController : MonoBehaviour
{
    public GameObject SecondMemberInfo;
    public float animationDuration = 0.5f; // 애니메이션 지속 시간
    private Vector3 initialScale;

    void Start()
    {
        initialScale = SecondMemberInfo.transform.localScale;
    }

    void OnMouseDown()
    {
        StartCoroutine(AnimateInfoPanel());
    }

    IEnumerator AnimateInfoPanel()
    {
        if (!SecondMemberInfo.activeSelf)
        {
            SecondMemberInfo.SetActive(true);

            float elapsedTime = 0f;
            while (elapsedTime < animationDuration)
            {
                float t = elapsedTime / animationDuration;
                SecondMemberInfo.transform.localScale = Vector3.Lerp(Vector3.zero, initialScale, t);
                elapsedTime += Time.deltaTime;
                yield return null;
            }

            SecondMemberInfo.transform.localScale = initialScale;

            SocialController.instance.RequestFriendStats(long.Parse(PlayerPrefs.GetString("MemberId2")));
        }
        else
        {
            float elapsedTime = 0f;
            while (elapsedTime < animationDuration)
            {
                float t = elapsedTime / animationDuration;
                SecondMemberInfo.transform.localScale = Vector3.Lerp(initialScale, Vector3.zero, t);
                elapsedTime += Time.deltaTime;
                yield return null;
            }

            SecondMemberInfo.transform.localScale = Vector3.zero;
            SecondMemberInfo.SetActive(false);
        }
    }
}
