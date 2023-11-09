using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DemonFireController : MonoBehaviour
{
    private int EXP = 100;
    public GameObject ElementalSuperDemonFire;
    public Image hpBar;
    private GameObject cleaveFireObject;
    private ParticleSystem cleaveFireParticle;

    void Start()
    {
        hpBar.rectTransform.localScale = new Vector3(1f, 1f, 1f);
        ElementalSuperDemonFire.SetActive(true);

        cleaveFireObject = GameObject.Find("CleaveFire");
        cleaveFireParticle = cleaveFireObject.GetComponent<ParticleSystem>();
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
        if (cleaveFireParticle != null)
        {
            cleaveFireParticle.Stop();
            cleaveFireParticle.Clear();
        }
    }

    void PlayParticleEffect()
    {
        if(cleaveFireParticle != null)
        {
            StopParticleEffect();
            cleaveFireParticle.Play();
        }
    }

    void DownEXP()
    {
        EXP-=10;
        Debug.Log("EXP: " + EXP);

        if (EXP <= 0)
        {
            ElementalSuperDemonFire.SetActive(false);
        }

        hpBar.rectTransform.localScale = new Vector3((float)EXP / (float)100, 1f, 1f);
    }
}
