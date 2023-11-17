using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.UI;

public class TitleClickEventHandler : MonoBehaviour
{
    public Button[] buttons;
    public Button titleObject;

    void Start()
    {
        foreach (Button button in buttons)
        {
            button.onClick.AddListener(() => OnButtonClick(button));
        }
    }

    // 버튼 클릭 시 호출되는 함수
    void OnButtonClick(Button clickedButton)
    {
        // 클릭된 버튼의 이미지 가져오기
        Image clickedImage = clickedButton.GetComponent<Image>();

        // 클릭된 버튼의 이미지 이름 확인
        string buttonImageName = clickedImage.sprite.name;

        // 이미지 이름이 "on_"으로 시작하는 경우에만 처리
        if (buttonImageName.StartsWith("on_"))
        {
            titleObject.GetComponent<Image>().sprite = clickedImage.sprite;

            // "on_" 다음의 숫자 추출
            string numberPart = buttonImageName.Substring(3);

            // 숫자를 int로 변환
            if (int.TryParse(numberPart, out int number))
            {
                PlayerPrefs.SetInt("Title", number);
                int bag = PlayerPrefs.GetInt("Bag");
                int glasses = PlayerPrefs.GetInt("Glasses");
                int minihat = PlayerPrefs.GetInt("Minihat");
                int scarf = PlayerPrefs.GetInt("Scarf");
                int title = PlayerPrefs.GetInt("Title");
                int skin = PlayerPrefs.GetInt("Skin");
                int hand = PlayerPrefs.GetInt("Hand");
                MemberItem datas = new MemberItem(bag, glasses, minihat, scarf, title, skin, hand);
                AndroidController.instance.ModifyCharacterItems(datas);
            }
            else
            {
                Debug.Log("junyeong 숫자 변환 실패");
            }
        }
    }
}
