using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GlassesPanel : MonoBehaviour
{
    public List<Button> glassesButtons;
    public List<GameObject> glassesObjects;

    public List<Sprite> glassesOff;
    public List<Sprite> glassesOn;

    public void ClickTab(int id)
    {
        // active -> not active
        if (glassesObjects[id].activeSelf)
        {
            glassesObjects[id].SetActive(false);
            glassesButtons[id].GetComponent<Image>().sprite = glassesOff[id];
        }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < glassesObjects.Count; i++)
            {
                if (i == id)
                {
                    glassesObjects[i].SetActive(true);
                    glassesButtons[i].GetComponent<Image>().sprite = glassesOn[i];
                }
                else
                {
                    glassesObjects[i].SetActive(false);
                    glassesButtons[i].GetComponent<Image>().sprite = glassesOff[i];
                }
            }
        }

        // Update equipment state into server
        PlayerPrefs.SetInt("Glasses", id+1);

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
