using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GlassesPanel : MonoBehaviour
{
    public List<ClothesButton> glassesButtons;
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

        // TODO
        // Update equipment state into server
    }
}
