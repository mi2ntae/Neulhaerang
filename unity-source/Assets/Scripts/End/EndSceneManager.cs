using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class EndSceneManager : MonoBehaviour
{
    private GameObject SparkObject1;
    private ParticleSystem SparkParticle1;
    private GameObject SparkObject2;
    private ParticleSystem SparkParticle2;
    private GameObject SparkObject3;
    private ParticleSystem SparkParticle3;
    void Start()
    {
        SparkObject1 = GameObject.Find("SparkExplosionEpic1");
        SparkParticle1 = SparkObject1.GetComponent<ParticleSystem>();

        SparkObject2 = GameObject.Find("SparkExplosionEpic2");
        SparkParticle2 = SparkObject2.GetComponent<ParticleSystem>();

        SparkObject3 = GameObject.Find("SparkExplosionEpic3");
        SparkParticle3 = SparkObject3.GetComponent<ParticleSystem>();

        // 1초씩 시간을 두고 Particle System 실행
        StartCoroutine(PlayParticlesWithDelay());
    }
    IEnumerator PlayParticlesWithDelay()
    {
        yield return new WaitForSeconds(1f);
        SparkParticle1.Play();

        yield return new WaitForSeconds(1f);
        SparkParticle2.Play();

        yield return new WaitForSeconds(1f);
        SparkParticle3.Play();
    }

    void Update()
    {

    }
}
