using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ScarfPanel : MonoBehaviour
{
    public List<ClothesButton> scarfButtons;
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
    }
}
