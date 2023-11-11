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

    // Management of GameObject
    public List<GameObject> characterBags;
    public List<GameObject> characterGlasses;
    public List<GameObject> characterMinihats;
    public List<GameObject> characterScarfs;

    // Start is called before the first frame update
    void Start()
    {
        GameObject myCharacter = Instantiate(characterPrefab);
        myCharacter.SetActive(true);

        // Init clothes
        Init();

        // characterPosition position
        Vector3 characterPosition = myCharacter.transform.position;
        Debug.Log("characterPrefab 위치: " + characterPosition);

        characterBags = new List<GameObject>();
        characterGlasses = new List<GameObject>();
        characterMinihats = new List<GameObject>();
        characterScarfs = new List<GameObject>();

        // active test
        BagA.SetActive(true);
        Debug.Log("가방 상태 : " + BagA.activeSelf);
    }

    // All Clothes Object setActive false
    void Init()
    {
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

    // Update is called once per frame
    void Update()
    {
        
    }
}