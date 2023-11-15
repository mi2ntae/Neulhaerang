using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ScarfPanel : MonoBehaviour
{
    public List<Button> scarfButtons;
    public List<GameObject> scarfObjects;

    public List<Sprite> scarfOff;
    public List<Sprite> scarfOn;

    public void ClickTab(int id)
    {
        // active -> not active
        if (scarfObjects[id].activeSelf)
        {
            scarfObjects[id].SetActive(false);
            scarfButtons[id].GetComponent<Image>().sprite = scarfOff[id];
        }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < scarfObjects.Count; i++)
            {
                if (i == id)
                {
                    scarfObjects[i].SetActive(true);
                    scarfButtons[i].GetComponent<Image>().sprite = scarfOn[i];
                }
                else
                {
                    scarfObjects[i].SetActive(false);
                    scarfButtons[i].GetComponent<Image>().sprite = scarfOff[i];
                }
            }
        }

        // TODO
        // Update equipment state into server
        PlayerPrefs.SetInt("Scarf", id + 1);

        int bag = PlayerPrefs.GetInt("Bag");
        int glasses = PlayerPrefs.GetInt("Glasses");
        int minihat = PlayerPrefs.GetInt("Minihat");
        int scarf = PlayerPrefs.GetInt("Scarf");
        int title = PlayerPrefs.GetInt("Title");
        MemberItem datas = new MemberItem(bag, glasses, minihat, scarf, title);
        //var andController = new AndroidController();
        //andController.ModifyCharacterItems(datas);
        AndroidController.instance.ModifyCharacterItems(datas);
    }
}
