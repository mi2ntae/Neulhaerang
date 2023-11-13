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

    public int Backpack { get => backpack; }
    public int Glasses { get => glasses; }
    public int Hat { get => hat; }
    public int Scarf { get => scarf; }
}