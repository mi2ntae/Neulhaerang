using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MinihatPanel : MonoBehaviour
{
    public List<ClothesButton> minihatButtons;
    public List<GameObject> minihatObjects;

    public void ClickTab(int id)
    {
        // active -> not active
        if (minihatObjects[id].activeSelf) { minihatObjects[id].SetActive(false); }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < minihatObjects.Count; i++)
            {
                if (i == id)
                {
                    minihatObjects[i].SetActive(true);
                }
                else
                {
                    minihatObjects[i].SetActive(false);
                }
            }
        }

        // TODO
        // Update equipment state into server
    }
}
