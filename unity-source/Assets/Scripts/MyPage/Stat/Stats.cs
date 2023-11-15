using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class Stats : MonoBehaviour
{
    public event EventHandler OnStatsChanged;
    public static int STAT_MIN = 0;
    public static int STAT_MAX = 2500;

    public enum Type
    {
        Godsang,
        Survive,
        Inssa,
        Teunteun,
        Goodidea,
        Love
    }

    private SingleStat godsangStat;
    private SingleStat surviveStat;
    private SingleStat inssaStat;
    private SingleStat teunteunStat;
    private SingleStat goodideaStat;
    private SingleStat loveStat;

    public Stats(int godsangAmount, int surviveAmount, int inssaAmount, int teunteunAmount, int goodideaAmount, int loveAmount)
    {
        godsangStat = new SingleStat(godsangAmount);
        surviveStat = new SingleStat(surviveAmount);
        inssaStat = new SingleStat(inssaAmount);
        teunteunStat = new SingleStat(teunteunAmount);
        goodideaStat = new SingleStat(goodideaAmount);
        loveStat = new SingleStat(loveAmount);
    }

    private SingleStat GetSingleStat(Type statType)
    {
        switch (statType)
        {
            default:
            case Type.Godsang:  return godsangStat;
            case Type.Survive:  return surviveStat;
            case Type.Inssa:    return inssaStat;
            case Type.Teunteun: return teunteunStat;
            case Type.Goodidea: return goodideaStat;
            case Type.Love:     return loveStat;
        }

    }


    public void SetStatAmount(Type statType, int StatAmount)
    {
        GetSingleStat(statType).SetStatAmount(StatAmount);
        if (OnStatsChanged != null) OnStatsChanged(this, EventArgs.Empty);
    }

    public int GetStatAmount(Type statType)
    {
        return GetSingleStat(statType).GetStatAmount();
    }

    public float GetStatAmountNormalized(Type statType)
    {
        return GetSingleStat(statType).GetStatAmountNormalized();
    }


    /*
     * Represents a Single Stat of any Type
     */
    private class SingleStat
    {
        private int stat;

        public SingleStat(int statAmount) {
            SetStatAmount(statAmount);
        }
        public void SetStatAmount(int attackStatAmount)
        {
            stat = Mathf.Clamp(attackStatAmount, STAT_MIN, STAT_MAX);
        }

        public int GetStatAmount()
        {
            return stat;
        }

        public float GetStatAmountNormalized()
        {
            return (float) stat / STAT_MAX;
        }
    }
}
