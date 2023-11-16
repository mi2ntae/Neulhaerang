using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.UI;

public class AroundPeopleManager : MonoBehaviour
{
    public GameObject[] Character;
    public GameObject[] CharaterInfo;
    public Image[] TitleImage;

    public GameObject[] Bags;
    public GameObject[] Glasses;
    public GameObject[] Scarfs;
    public GameObject[] Hats;

    public AroundMembers data;

    void Start()
    {
        for(int i = 0; i < 3; i++)
        {
            Character[i].SetActive(false);
            CharaterInfo[i].SetActive(false);
        }

        //AndroidController.instance.RequestNearByUsers();

        //int size = data.members.Length;
        int size = 1;/////////////////////////////////////////////수정

        for(int i = 0; i < size; i++)
        {
            Transform transform = Character[i].transform.Find("Root_M/Spine1_M/Chest_M");
            GameObject Back = transform.Find("BagA").gameObject;
            GameObject Neck = transform.Find("Neck_M/ScarfA").gameObject;
            GameObject Face = transform.Find("Neck_M/Head_M/GlassesA").gameObject;
            GameObject Head = transform.Find("Neck_M/Head_M/MinihatA").gameObject;

            int index = i;

            //if (data.members[i].Title != 0)
            //{
            // 에디터에서 프로젝트 경로에 있는 이미지의 경로
            //Sprite sprite = Resources.Load<Sprite>("TitleImage/on/on_"+data.members[i].Title.toString()+".png");
            Sprite sprite = Resources.Load<Sprite>("TitleImage/on/on_4");

            if (sprite != null)
            {
                TitleImage[index].sprite = sprite;
            }
            else
            {
                Debug.LogError("Failed to load image");
            }
            //}

            //if (data.members[i].Scarf != 0)
            //{
            // Instantiate a new Hat Prefab
            //GameObject instantiatedNeck = Instantiate(Scarfs[data.members[i].Neck-1], transform.Find("Neck_M"));
                GameObject instantiatedNeck = Instantiate(Scarfs[2], transform.Find("Neck_M"));
                instantiatedNeck.transform.position = Neck.transform.position;
                instantiatedNeck.transform.rotation = Neck.transform.rotation;
                instantiatedNeck.name="ScarfA";
                instantiatedNeck.SetActive(true);
            //}

            //if (data.members[i].Backpack != 0)
            //{
            //GameObject instantiatedNeck = Instantiate(Bags[data.members[i].Backpack-1], transform.Find("Neck_M"));
            GameObject instantiatedBack = Instantiate(Bags[7], transform);
            instantiatedBack.transform.position = Back.transform.position;
            instantiatedBack.transform.rotation = Back.transform.rotation;
            instantiatedBack.name = "BagA";
            instantiatedBack.SetActive(true);
            //}
            //if (data.members[i].Glasses != 0)
            //{
            //GameObject instantiatedNeck = Instantiate(Bags[data.members[i].Glasses-1], transform.Find("Neck_M"))
            GameObject instantiatedFace = Instantiate(Glasses[1], transform.Find("Neck_M/Head_M"));
            instantiatedFace.transform.position = Face.transform.position;
            instantiatedFace.transform.rotation = Face.transform.rotation;
            instantiatedFace.name = "GlassesA";
            instantiatedFace.SetActive(true);
            //}
            //if (data.members[i].Hat != 0)
            //{
            //GameObject instantiatedNeck = Instantiate(Bags[data.members[i].Hat-1], transform.Find("Neck_M"))
            GameObject instantiatedHead = Instantiate(Hats[2], transform.Find("Neck_M/Head_M"));
            instantiatedHead.transform.position = Head.transform.position;
            instantiatedHead.transform.rotation = Head.transform.rotation;
            instantiatedHead.name = "HatA";
            instantiatedHead.SetActive(true);
            //}

            Destroy(Back);
            Destroy(Neck);
            Destroy(Face);
            Destroy(Head);

            Character[i].SetActive(true);
        }
    }

    void ReceiveNearByUsers(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveNearByUsers]" + jsonMessage);
        data = JsonUtility.FromJson<AroundMembers>(jsonMessage);
    }
}
