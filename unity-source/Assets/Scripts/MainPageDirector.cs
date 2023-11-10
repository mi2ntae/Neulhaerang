using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MainPageDirector : MonoBehaviour
{
    public GameObject characterPrefab;
    public GameObject characterBag;
    public GameObject characterGlasses;
    public GameObject characterMinihat;
    public GameObject characterScarf;

    // Start is called before the first frame update
    void Start()
    {
        GameObject myCharacter = Instantiate(characterPrefab);
        myCharacter.SetActive(true);

        // characterPosition position
        Vector3 characterPosition = myCharacter.transform.position;
        Debug.Log("characterPrefab À§Ä¡: " + characterPosition);

        GameObject myBag = Instantiate(characterBag);
        GameObject myGlasses = Instantiate(characterGlasses);
        GameObject myMinihat = Instantiate(characterMinihat);
        GameObject myScarf = Instantiate(characterScarf);

        myBag.SetActive(true);
        myGlasses.SetActive(true);
        myMinihat.SetActive(true);
        myScarf.SetActive(true);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}