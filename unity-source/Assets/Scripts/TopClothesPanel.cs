using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TopClothesPanel : MonoBehaviour
{
    public List<ClothesTabButton> tabButtons;
    public List<GameObject> contentsPanels;

    public void ClickTab(int id){
        for(int i = 0 ; i < contentsPanels.Count; i++){
            if(i==id){
                contentsPanels[i].SetActive(true);
            }
            else{
                contentsPanels[i].SetActive(false);
            }
        }
    }
}
