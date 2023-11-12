using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScarfPanel : MonoBehaviour
{
    public List<ClothesButton> scarfButtons;
    public List<GameObject> scarfObjects;

    public void ClickTab(int id)
    {
        // active -> not active
        if (scarfObjects[id].activeSelf) { scarfObjects[id].SetActive(false); }
        else // not active -> all off -> activate
        {
            for (int i = 0; i < scarfObjects.Count; i++)
            {
                if (i == id)
                {
                    scarfObjects[i].SetActive(true);
                }
                else
                {
                    scarfObjects[i].SetActive(false);
                }
            }
        }

        // TODO
        // Update equipment state into server
    }
}
