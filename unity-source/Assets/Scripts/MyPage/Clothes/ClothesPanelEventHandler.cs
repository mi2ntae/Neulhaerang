using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ClothesPanelEventHandler : MonoBehaviour
{
    public List<Button> tabButtons;
    public List<GameObject> contentsPanels;

    public Color normalColor;
    public Color selectedColor;

    private void Start()
    {
        ShowPanel(contentsPanels[0], tabButtons[0]);

        for (int i = 0; i < tabButtons.Count; i++)
        {
            int index = i;
            tabButtons[i].onClick.AddListener(() => ShowPanel(contentsPanels[index], tabButtons[index]));
        }
    }

    void ShowPanel(GameObject targetPanel, Button targetButton){
        foreach(var panel in contentsPanels)
        {
            panel.SetActive(false);
        }
        targetPanel.SetActive(true);

        foreach(var button in tabButtons)
        {
            button.image.color = normalColor;
        }
        targetButton.image.color = selectedColor;
    }
}
