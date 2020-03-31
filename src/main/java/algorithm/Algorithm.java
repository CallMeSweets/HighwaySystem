package algorithm;

import java.util.*;

import data.DataHolder;
import data.Edge;
import data.Point;

public class Algorithm {

    private DataHolder G;
    private Edge[] edgeTo;
    private double[] objectives;
    private boolean[] relaxed;
    private IndexMinPQ<Double> pq;
    private final int roads;

    public Algorithm() {
        G = new DataHolder();

        int vertices = G.getNumberOfPoints();
        roads = (vertices - 1) * vertices / 2;

        edgeTo = new Edge[vertices];
        objectives = new double[vertices];
        relaxed = new boolean[vertices];
        pq = new IndexMinPQ<Double>(vertices);

        for(int v = 0; v < objectives.length; v++) {
            objectives[v] = Double.POSITIVE_INFINITY;
        }
    }

    public void start() {
        Edge minDist = Collections.min(G.getEdges());
        Point start = minDist.getBeginPoint();
        int startV = G.getPoints().indexOf(start);

        objectives[startV] = 0.0;

        pq.insert(startV, 0.0);
        while(!pq.isEmpty()){
            int currentV = pq.delMin();

            if(!relaxed[currentV])
                relax(currentV);
            
            relaxed[currentV] = true;
        }

        LinkedList<Edge> highwayEdges = new LinkedList<>(Arrays.asList(edgeTo));
        highwayEdges.remove(null);
        for (Edge edge : highwayEdges) {
            System.out.println(edge);
        }
    }

    private void relax(int v) {
        List<Point> points = G.getPoints();
        Point from = points.get(v);

        for(Edge e: G.getAllPointEdges(from)) {
            Point to = e.getBeginPoint().equals(from) ? e.getEndPoint() : e.getBeginPoint();

            int w = points.indexOf(to);

            double distance = e.getWeight();
            double currObjective = G.getW1() * distance + G.getW2() * distance / roads;

            if(objectives[w] > currObjective) {
                objectives[w] = currObjective;
                edgeTo[w] = e;
                
                if(pq.contains(w))
                    pq.change(w, objectives[w]);
                else
                    pq.insert(w, objectives[w]);
            }
        }
    }
}
