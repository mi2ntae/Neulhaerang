using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GlassesPanel : MonoBehaviour
{
    public List<ClothesButton> glassesButtons;
    public List<GameObject> glassesObjects;

    public void ClickTab(int id)
    {
        // active -> not active
        if (glassesObjects[id].activeSelf) { glassesObjects[id].SetActive(false); }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < glassesObjects.Count; i++)
            {
                if (i == id)
                {
                    glassesObjects[i].SetActive(true);
                }
                else
                {
                    glassesObjects[i].SetActive(false);
                }
            }
        }

        // TODO
        // Update equipment state into server
    }
}
