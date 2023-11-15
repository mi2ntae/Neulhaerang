using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DemonWaterController : MonoBehaviour
{
    private int EXP = 200;
    public GameObject ElementalSuperDemonWater;
    public Image hpBar;
    private GameObject attackWaterObject;
    private ParticleSystem attackWaterParticle;

    void Start()
    {
        hpBar.rectTransform.localScale = new Vector3(1f, 1f, 1f);
        ElementalSuperDemonWater.SetActive(true);

        attackWaterObject = GameObject.Find("HolyBigExplosion");
        attackWaterParticle = attackWaterObject.GetComponent<ParticleSystem>();
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
        if (attackWaterParticle != null)
        {
            attackWaterParticle.Stop();
            attackWaterParticle.Clear();
        }
    }

    void PlayParticleEffect()
    {
        if (attackWaterParticle != null)
        {
            StopParticleEffect();
            attackWaterParticle.Play();
        }
    }

    void DownEXP()
    {
        EXP -= 10;
        Debug.Log("EXP: " + EXP);

        if (EXP <= 0)
        {
            ElementalSuperDemonWater.SetActive(false);
        }

        hpBar.rectTransform.localScale = new Vector3((float)EXP / (float)200, 1f, 1f);
    }
}
