using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DemonDarkController : MonoBehaviour
{
    private int EXP = 100;
    public GameObject ElementalSuperDemonDark;
    public Image hpBar;
    private GameObject attackShadowObject;
    private ParticleSystem attackShadowParticle;

    void Start()
    {
        hpBar.rectTransform.localScale = new Vector3(1f, 1f, 1f);
        ElementalSuperDemonDark.SetActive(true);

        attackShadowObject = GameObject.Find("ShadowPurpleExplosion");
        attackShadowParticle = attackShadowObject.GetComponent<ParticleSystem>();
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
        if (attackShadowParticle != null)
        {
            attackShadowParticle.Stop();
            attackShadowParticle.Clear();
        }
    }

    void PlayParticleEffect()
    {
        if (attackShadowParticle != null)
        {
            StopParticleEffect();
            attackShadowParticle.Play();
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
