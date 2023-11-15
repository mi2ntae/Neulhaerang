using System;
using UnityEngine;

[Serializable]
public class AroundMember
{
    [SerializeField]
    private long memberId;
    [SerializeField]
    private int backpack;
    [SerializeField]
    private int glasses;
    [SerializeField]
    private int hat;
    [SerializeField]
    private int scarf;
    [SerializeField]
    private int title;

    public long MemberId { get => memberId; }
    public int Backpack { get => backpack; }
    public int Glasses { get => glasses; }
    public int Hat { get => hat; }
    public int Scarf { get => scarf; }
    public int Title { get => title; }

    public override string ToString()
    {
        return "[MemberId]" + MemberId + "[Backpack]" + Backpack + " [Glasses]" + Glasses + " [Hat]" + Hat + " [Scarf]" + Scarf + " [Title]" + Title;
    }
}


public class AroundMembers
{
    public AroundMember[] members;
}
