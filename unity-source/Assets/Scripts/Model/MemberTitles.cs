using System;
using UnityEngine;

[Serializable]
public class MemberTitle
{
    [SerializeField]
    private long titleId;
    [SerializeField]
    private string name;
    [SerializeField]
    private string content;

    public long TitleId { get => titleId; }
    public string Name { get => name; }
    public string Content { get => content; }
}

public class MemberTitles
{
    public MemberTitle[] titles;
}