using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainPageDirector : MonoBehaviour
{
    public GameObject characterPrefab;

    public GameObject BagA;
    public GameObject BagA2;
    public GameObject BagA3;
    public GameObject BagA4;

    public GameObject ScarfA;
    public GameObject ScarfA2;
    public GameObject ScarfA3;

    public GameObject GlassesA;
    public GameObject GlassesA2;
    public GameObject GlassesA3;
    public GameObject GlassesA4;

    public GameObject MinihatA;
    public GameObject MinihatA2;
    public GameObject MinihatA3;
    public GameObject MinihatA4;

    // Start is called before the first frame update
    void Start()
    {
        GameObject myCharacter = Instantiate(characterPrefab);
        myCharacter.SetActive(true);

        // Init clothes
        Init();

        // TODO
        // Receive the clothes data from server, update the clothes state.
        ClothesUpdate();

        // characterPosition position
        Vector3 characterPosition = myCharacter.transform.position;
        Debug.Log("characterPrefab 위치: " + characterPosition);

        // active test
        BagA.SetActive(true);
        Debug.Log("가방 상태 : " + BagA.activeSelf);
    }

    
    void Init()
    {
        // All Clothes Object setActive false
        BagA.SetActive(false);
        BagA2.SetActive(false);
        BagA3.SetActive(false);
        BagA4.SetActive(false);

        ScarfA.SetActive(false);
        ScarfA2.SetActive(false);
        ScarfA3.SetActive(false);

        GlassesA.SetActive(false);
        GlassesA2.SetActive(false);
        GlassesA3.SetActive(false);
        GlassesA4.SetActive(false);

        MinihatA.SetActive(false);
        MinihatA2.SetActive(false);
        MinihatA3.SetActive(false);
        MinihatA4.SetActive(false);
    }

    void ClothesUpdate()
    {

    }


    // Update is called once per frame
    void Update()
    {
        
    }
}