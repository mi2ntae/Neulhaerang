using System;
using UnityEngine;

[Serializable]
public class MemberStatItem
{
    [SerializeField]
    private int score;
    [SerializeField]
    private string level;

    public int Score { get => score; }
    public string Level { get => level; }
}

public class MemberStats
{
    public MemberStatItem[] stats;
}