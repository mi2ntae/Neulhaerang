using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MainPageDirector : MonoBehaviour
{
    public GameObject characterPrefab;

    // Clothes GameObject List
    public List<GameObject> bagList;
    public List<GameObject> glassesList;
    public List<GameObject> minihatList;
    public List<GameObject> scarfList;

    // Clothes Button Image List
    public List<Sprite> bagOn;
    public List<Sprite> glassesOn;
    public List<Sprite> minihatOn;
    public List<Sprite> scarfOn;

    // Clothes Button List
    public List<ClothesButton> bagButtons;
    public List<ClothesButton> glassesButtons;
    public List<ClothesButton> minihatButtons;
    public List<ClothesButton> scarfButtons;

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

        // active test
        //BagA.SetActive(true);
        //Debug.Log("가방 상태 : " + BagA.activeSelf);
    }

    
    void Init()
    {
        // All Clothes Object setActive false
        foreach (var bag in bagList)
        {
            bag.SetActive(false);
        }

        foreach (var glasses in glassesList)
        {
            glasses.SetActive(false);
        }

        foreach (var minihat in minihatList)
        {
            minihat.SetActive(false);
        }

        foreach (var scarf in scarfList)
        {
            scarf.SetActive(false);
        }

        // TODO
        // Receive the clothes data from server, update the clothes state.
        ClothesUpdate();
    }

    void ClothesUpdate()
    {
        // We must update the clothes gameobject and button image
        // example
        bagList[0].SetActive(true);
        bagButtons[0].GetComponent<Image>().sprite = bagOn[0];
    }


    // Update is called once per frame
    void Update()
    {
        
    }
}