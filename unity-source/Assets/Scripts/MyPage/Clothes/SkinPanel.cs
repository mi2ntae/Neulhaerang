using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class SkinPanel : MonoBehaviour
{
    public List<Button> skinButtons;
    public Material[] skinMaterials;
    public GameObject character;
    public List<Sprite> skinOff;
    public List<Sprite> skinOn;

    public void ClickTab(int id)
    {
        int activeIndex = PlayerPrefs.GetInt("Skin");
        // active -> no work
        if (id == activeIndex)
        {
            Debug.Log("You have chosen this skin. Please click other skin");
        }
        else // not active -> all off -> activate
        {
            // equip
            if (character != null)
            {
                // "Cat" GameObject에서 SkinnedMeshRenderer 컴포넌트를 찾음
                SkinnedMeshRenderer skinnedMeshRenderer = character.GetComponent<SkinnedMeshRenderer>();
            
                if (skinnedMeshRenderer != null && skinMaterials != null && skinMaterials.Length > 0)
                    {
                    Material newMaterialInstance = new Material(skinMaterials[id]);
                    // Materials 배열을 새로운 배열로 교체
                    //skinnedMeshRenderer.materials[0] = skinMaterials[id];
                    skinnedMeshRenderer.materials[0].CopyPropertiesFromMaterial(newMaterialInstance);
                }
            }

            // button image change
            for (int i = 0; i < skinButtons.Count; i++)
            {
                if (i == id)
                {   
                    skinButtons[i].GetComponent<Image>().sprite = skinOn[i];
                }
                else
                {
                    skinButtons[i].GetComponent<Image>().sprite = skinOff[i];
                }
            }
            PlayerPrefs.SetInt("Skin", id);
        }

        // Update equipment state into server
        int bag = PlayerPrefs.GetInt("Bag");
        int glasses = PlayerPrefs.GetInt("Glasses");
        int minihat = PlayerPrefs.GetInt("Minihat");
        int scarf = PlayerPrefs.GetInt("Scarf");
        int title = PlayerPrefs.GetInt("Title");
        int skin = PlayerPrefs.GetInt("Skin");
        int hand = PlayerPrefs.GetInt("Hand");
        MemberItem datas = new MemberItem(bag, glasses, minihat, scarf, title, skin, hand);
        //var andController = new AndroidController();
        //andController.ModifyCharacterItems(datas);
        AndroidController.instance.ModifyCharacterItems(datas);
    }
}
