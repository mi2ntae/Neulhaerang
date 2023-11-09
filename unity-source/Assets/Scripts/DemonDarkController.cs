using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DemonDarkController : MonoBehaviour
{
    private int EXP = 100;
    public GameObject ElementalSuperDemonDark;
    public Image hpBar;
    private GameObject cleaveShadowObject;
    private ParticleSystem cleaveShadowParticle;

    void Start()
    {
        hpBar.rectTransform.localScale = new Vector3(1f, 1f, 1f);
        ElementalSuperDemonDark.SetActive(true);

        cleaveShadowObject = GameObject.Find("CleaveShadow");
        cleaveShadowParticle = cleaveShadowObject.GetComponent<ParticleSystem>();
        StopParticleEffect();
    }

    void Update()
    {

    }

    void OnMouseDown()
    {
        PlayParticleEffect();
        Invoke("DownEXP", 0.5f);
    }

    void StopParticleEffect()
    {
        if (cleaveShadowParticle != null)
        {
            cleaveShadowParticle.Stop();
            cleaveShadowParticle.Clear();
        }
    }

    void PlayParticleEffect()
    {
        if (cleaveShadowParticle != null)
        {
            StopParticleEffect();
            cleaveShadowParticle.Play();
        }
    }

    void DownEXP()
    {
        EXP -= 10;
        Debug.Log("EXP: " + EXP);

        if (EXP <= 0)
        {
            ElementalSuperDemonDark.SetActive(false);
        }

        hpBar.rectTransform.localScale = new Vector3((float)EXP / (float)100, 1f, 1f);
    }
}
