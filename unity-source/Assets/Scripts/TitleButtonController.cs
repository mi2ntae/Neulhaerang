using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TitleButtonController : MonoBehaviour
{
    public List<TitleButton> titleButtons;
    public Button titleObject;
    public List<Sprite> titleSprites;

    public void ClickTab(int id)
    {
        // transparent
        Image buttonImage = titleObject.GetComponent<Image>();
        Color newColor = buttonImage.color;
        newColor.a = 1.0f;
        buttonImage.color = newColor;
        Debug.Log("color : " + newColor);
        
        titleObject.GetComponent<Image>().sprite = titleSprites[id];


        // TODO
        // Update equipment state into server
    }
}
