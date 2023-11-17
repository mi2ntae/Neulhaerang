using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StartStat : MonoBehaviour
{
    [SerializeField] private StatsRadarChart statsRadarChart;
    private void Start()
    {

        int[] scores = new int[6];

        scores = getScore(scores);

        /*
         * A+ => 2500
         * A  => 2000
         * B+ => 1500
         * B  => 1000
         * C+ => 500
         */        

        Stats stats = new Stats(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5]);
        //statsRadarChart.SetStats(stats);
    }

    private int[] getScore(int[] scores)
    {
        // TODO
        // get score from server

        // godsang
        scores[0] = 2500;
        // survive
        scores[1] = 2000;
        // inssa
        scores[2] = 1500;
        // teunteun
        scores[3] = 2000;
        // goodidea
        scores[4] = 2500;
        // love
        scores[5] = 1500;

        return scores;
    }
}