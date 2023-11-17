using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HandPanel : MonoBehaviour
{
    public List<Button> handButtons;
    public List<GameObject> handObjects;

    public List<Sprite> handOff;
    public List<Sprite> handOn;

    public void ClickTab(int id)
    {
        // active -> not active
        if (handObjects[id].activeSelf)
        {
            handObjects[id].SetActive(false);
            handButtons[id].GetComponent<Image>().sprite = handOff[id];
        }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < handObjects.Count; i++)
            {
                if (i == id)
                {
                    handObjects[i].SetActive(true);
                    handButtons[i].GetComponent<Image>().sprite = handOn[i];
                }
                else
                {
                    handObjects[i].SetActive(false);
                    handButtons[i].GetComponent<Image>().sprite = handOff[i];
                }
            }
        }

        // TODO
        // Update equipment state into server
        PlayerPrefs.SetInt("Hand", id + 1);

        int bag = PlayerPrefs.GetInt("Bag");
        int glasses = PlayerPrefs.GetInt("Glasses");
        int minihat = PlayerPrefs.GetInt("Minihat");
        int scarf = PlayerPrefs.GetInt("Scarf");
        int title = PlayerPrefs.GetInt("Title");
        int skin = PlayerPrefs.GetInt("Skin");
        int hand = PlayerPrefs.GetInt("Hand");
        MemberItem datas = new MemberItem(bag, glasses, minihat, scarf, title, skin, hand);
        //var andController = new AndroidController();
        //andController.ModifyCharacterItems(datas);
        AndroidController.instance.ModifyCharacterItems(datas);
    }
}
