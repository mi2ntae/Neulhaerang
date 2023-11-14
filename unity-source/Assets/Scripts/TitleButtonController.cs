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

        // Update equipment state into server
        PlayerPrefs.SetInt("Title", id+1);

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
