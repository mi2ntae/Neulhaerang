using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GoDemonScript : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void GoDemon(){
        Debug.Log("Clicked GoDemon Button");
        SceneManager.LoadScene("MonsterScene");
    }
}
