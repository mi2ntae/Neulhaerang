using System;
using UnityEngine;

[Serializable]
public class MemberStatus
{
	[SerializeField]
	private int indolence;
	[SerializeField]
	private int tiredness;

	public int Indolence { get => indolence; }
	public int Tiredness { get => tiredness; }
}