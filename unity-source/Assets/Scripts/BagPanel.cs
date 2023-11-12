using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BagPanel : MonoBehaviour
{
    public List<ClothesButton> bagButtons;
    public List<GameObject> bagObjects;

    public void ClickTab(int id)
    {
        // active -> not active
        if (bagObjects[id].activeSelf) { bagObjects[id].SetActive(false); }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < bagObjects.Count; i++)
            {
                if (i == id)
                {
                    bagObjects[i].SetActive(true);
                }
                else
                {
                    bagObjects[i].SetActive(false);
                }
            }
        }

        // TODO
        // Update equipment state into server
    }
}
