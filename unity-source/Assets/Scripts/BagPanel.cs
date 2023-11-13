using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class BagPanel : MonoBehaviour
{
    public List<ClothesButton> bagButtons;
    public List<GameObject> bagObjects;

    public List<Sprite> bagOff;
    public List<Sprite> bagOn;

    public void ClickTab(int id)
    {   
        // active -> not active
        if (bagObjects[id].activeSelf) 
        {
            bagObjects[id].SetActive(false);
            bagButtons[id].GetComponent<Image>().sprite = bagOff[id];
        }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < bagObjects.Count; i++)
            {
                if (i == id)
                {
                    bagObjects[i].SetActive(true);
                    bagButtons[i].GetComponent<Image>().sprite = bagOn[i];
                }
                else
                {
                    bagObjects[i].SetActive(false);
                    bagButtons[i].GetComponent<Image>().sprite = bagOff[i];
                }
            }
        }

        // TODO
        // Update equipment state into server
    }
}
