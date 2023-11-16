using System;
using UnityEngine;

[Serializable]
public class MemberProfile
{
    [SerializeField]
    private int level;
    [SerializeField]
    private int nxtExp;
    [SerializeField]
    private int curExp;
    [SerializeField]
    private string nickname;

    public int Level { get => level; }
    public int NxtExp { get => nxtExp; }
    public int CurExp { get => curExp; }
    public string Nickname { get => nickname; }

    public override string ToString()
    {
        return "[level]" + level + " [nxtExp]" + nxtExp + " [curExp]" + curExp + " [nickname]" + nickname;
    }
}