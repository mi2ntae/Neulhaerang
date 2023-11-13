using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StatsRadarChart : MonoBehaviour
{
    [SerializeField] private Material radarMaterial;
    [SerializeField] private Texture2D radarTexture2D;


    private Stats stats;
    private CanvasRenderer radarMeshCanvasRenderer;

    private void Awake()
    {
        radarMeshCanvasRenderer = transform.Find("radarMesh").GetComponent<CanvasRenderer>();
    }

    public void SetStats(Stats stats)
    {
        this.stats = stats;
        stats.OnStatsChanged += Stats_OnStasChanged;
        UpdateStatsVisual();
    }

    private void Stats_OnStasChanged(object sender, System.EventArgs e)
    {
        // only update when stat changed
        UpdateStatsVisual();
    }

    private void UpdateStatsVisual()
    {
        //transform.Find("godsangBar").localScale = new Vector3(1, stats.GetStatAmountNormalized(Stats.Type.Godsang));
        //transform.Find("surviveBar").localScale = new Vector3(1, stats.GetStatAmountNormalized(Stats.Type.Godsang));

        Mesh mesh = new Mesh();
        Vector3[] vertices = new Vector3[7];
        Vector2[] uv = new Vector2[7];
        int[] triangles = new int[3 * 6];

        float angleIncrement = 360f / 6;
        float radarChartSize = 870f;

        Vector3 godsangVertex = Quaternion.Euler(0, 0, -angleIncrement * 0) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Godsang);
        int godsangVertexIndex = 1;

        Vector3 surviveVertex = Quaternion.Euler(0, 0, -angleIncrement * 1) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Survive);
        int surviveVertexIndex = 2;

        Vector3 inssaVertex = Quaternion.Euler(0, 0, -angleIncrement * 2) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Inssa);
        int inssaVertexIndex = 3;

        Vector3 teunteunVertex = Quaternion.Euler(0, 0, -angleIncrement * 3) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Teunteun);
        int teunteunVertexIndex = 4;

        Vector3 goodideaVertex = Quaternion.Euler(0, 0, -angleIncrement * 4) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Goodidea);
        int goodideaVertexIndex = 5;

        Vector3 loveVertex = Quaternion.Euler(0, 0, -angleIncrement * 5) * Vector3.up * radarChartSize * stats.GetStatAmountNormalized(Stats.Type.Love);
        int loveVertexIndex = 6;


        vertices[0] = Vector3.zero;
        vertices[godsangVertexIndex] = godsangVertex;
        vertices[surviveVertexIndex] = surviveVertex;
        vertices[inssaVertexIndex] = inssaVertex;
        vertices[teunteunVertexIndex] = teunteunVertex;
        vertices[goodideaVertexIndex] = goodideaVertex;
        vertices[loveVertexIndex] = loveVertex;

        uv[0] = Vector2.zero;
        uv[godsangVertexIndex] = Vector2.one;
        uv[surviveVertexIndex] = Vector2.one;
        uv[inssaVertexIndex] = Vector2.one;
        uv[teunteunVertexIndex] = Vector2.one;
        uv[goodideaVertexIndex] = Vector2.one;
        uv[loveVertexIndex] = Vector2.one;

        triangles[0] = 0;
        triangles[1] = godsangVertexIndex;
        triangles[2] = surviveVertexIndex;

        triangles[3] = 0;
        triangles[4] = surviveVertexIndex;
        triangles[5] = inssaVertexIndex;

        triangles[6] = 0;
        triangles[7] = inssaVertexIndex;
        triangles[8] = teunteunVertexIndex;

        triangles[9] = 0;
        triangles[10] = teunteunVertexIndex;
        triangles[11] = goodideaVertexIndex;

        triangles[12] = 0;
        triangles[13] = goodideaVertexIndex;
        triangles[14] = loveVertexIndex;

        triangles[15] = 0;
        triangles[16] = loveVertexIndex;
        triangles[17] = godsangVertexIndex;

        mesh.vertices = vertices;
        mesh.uv = uv;
        mesh.triangles = triangles;

        radarMeshCanvasRenderer.SetMesh(mesh);
        radarMeshCanvasRenderer.SetMaterial(radarMaterial, radarTexture2D);
    }
}
