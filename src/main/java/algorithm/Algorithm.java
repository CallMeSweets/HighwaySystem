package algorithm;

import java.util.*;

import data.DataHolder;
import data.Edge;
import data.Point;

public class Algorithm {

    private DataHolder dataHolder;
    private Edge[] edgeTo;
    private double[] objectives;
    private IndexMinPQ<Double> priorityQueue;
    private final int numberOfRoads;

    public Algorithm() {
        dataHolder = new DataHolder();

        int numberOfPoints = dataHolder.getNumberOfPoints();
        numberOfRoads = (numberOfPoints - 1) * numberOfPoints / 2;

        edgeTo = new Edge[numberOfPoints];
        objectives = new double[numberOfPoints];
        priorityQueue = new IndexMinPQ<>(numberOfPoints);

        for(int v = 0; v < objectives.length; v++) {
            objectives[v] = Double.POSITIVE_INFINITY;
        }
    }

    public void start() {
        Edge minDist = Collections.min(dataHolder.getEdges());
        Point startPoint = minDist.getBeginPoint();
        int startPointIndex = dataHolder.getPoints().indexOf(startPoint);

        objectives[startPointIndex] = 0.0;

        priorityQueue.insert(startPointIndex, 0.0);
        while(!priorityQueue.isEmpty()){
            int currentV = priorityQueue.delMin();
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
            double currObjective = dataHolder.getW1() * distance + dataHolder.getW2() * distance / numberOfRoads;

            if(objectives[w] > currObjective) {
                objectives[w] = currObjective;
                edgeTo[w] = e;
                
                if(priorityQueue.contains(w))
                    priorityQueue.change(w, objectives[w]);
                else
                    priorityQueue.insert(w, objectives[w]);
            }
        }
    }
}
