using System;
using UnityEngine;

[Serializable]
public class MemberStat
{
    [SerializeField]
    private int life;
    [SerializeField]
    private int love;
    [SerializeField]
    private int survival;
    [SerializeField]
    private int popularity;
    [SerializeField]
    private int power;
    [SerializeField]
    private int creative;

    public int Life { get => life; }
    public int Love { get => love; }
    public int Survival { get => survival; }
    public int Popularity { get => popularity; }
    public int Power { get => power; }
    public int Creative { get => creative; }
}
