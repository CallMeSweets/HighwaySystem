package algorithm;

import java.util.*;

import data.DataHolder;
import data.Edge;
import data.Point;

public class Algorithm {

    private DataHolder dataHolder;
    private Edge[] edgeTo;
    private double[] objectives;
    private IndexMinPQ<Double> pq;
    private final int roads;

    public Algorithm() {
        dataHolder = new DataHolder();

        int vertices = dataHolder.getNumberOfPoints();
        roads = (vertices - 1) * vertices / 2;

        edgeTo = new Edge[vertices];
        objectives = new double[vertices];
        pq = new IndexMinPQ<Double>(vertices);

        for(int v = 0; v < objectives.length; v++) {
            objectives[v] = Double.POSITIVE_INFINITY;
        }
    }

    public void start() {
        Edge minDist = Collections.min(dataHolder.getEdges());
        Point startPoint = minDist.getBeginPoint();
        int startPointIndex = dataHolder.getPoints().indexOf(startPoint);

        objectives[startPointIndex] = 0.0;

        pq.insert(startPointIndex, 0.0);
        while(!pq.isEmpty()){
            int currentV = pq.delMin();
            makePointFree(currentV);
        }

        LinkedList<Edge> highwayEdges = new LinkedList<>(Arrays.asList(edgeTo));
        highwayEdges.remove(null);
        for (Edge edge : highwayEdges) {
            System.out.println(edge);
        }
    }

    private void makePointFree(int pointIndex) {
        List<Point> points = dataHolder.getPoints();
        Point from = points.get(pointIndex);

        for(Edge e: dataHolder.getAllPointEdges(from)) {
            Point to = e.getBeginPoint().equals(from) ? e.getEndPoint() : e.getBeginPoint();

            int w = points.indexOf(to);

            double distance = e.getWeight();
            double currObjective = dataHolder.getW1() * distance + dataHolder.getW2() * distance / roads;

            if(objectives[w] > currObjective) {
                objectives[w] = currObjective;
                
                if(Arrays.asList(edgeTo).contains(e))
                    continue;
                
                edgeTo[w] = e;
                
                if(pq.contains(w))
                    pq.change(w, objectives[w]);
                else
                    pq.insert(w, objectives[w]);
            }
        }
    }
}
