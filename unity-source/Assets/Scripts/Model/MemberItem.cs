using System;
using UnityEngine;

[Serializable]
public class MemberItem
{
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

    public int Backpack { get => backpack; }
    public int Glasses { get => glasses; }
    public int Hat { get => hat; }
    public int Scarf { get => scarf; }
    public int Title { get => title; }

    public MemberItem(int backpack, int glasses, int hat, int scarf, int title)
    {
        this.backpack = backpack;
        this.glasses = glasses;
        this.hat = hat;
        this.scarf = scarf;
        this.title = title;
    }

    public override string ToString()
    {
        return "[Backpack]" + Backpack + " [Glasses]" + Glasses + " [Hat]" + Hat + " [Scarf]" + Scarf + " [Title]" + Title;
    }
}