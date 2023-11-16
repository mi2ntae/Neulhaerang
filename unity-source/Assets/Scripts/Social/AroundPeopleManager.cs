using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AroundPeopleManager : MonoBehaviour
{
    public GameObject[] Character;
    public GameObject[] CharaterInfo;
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
        int size = 1;/////////////////////////////////////////////¼öÁ¤
        for(int i = 0; i < size; i++)
        {
            int index = i;
            Character[i].SetActive(true);
        }
    }

    void ReceiveNearByUsers(string jsonMessage)
    {
        Debug.Log("heejeong [ReceiveNearByUsers]" + jsonMessage);
        data = JsonUtility.FromJson<AroundMembers>(jsonMessage);
    }

    void Update()
    {
        
    }
}
