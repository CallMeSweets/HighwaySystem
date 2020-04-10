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
    private int roads;

    public Algorithm() {
        dataHolder = new DataHolder();
    }

    public void initAlgorithm(){
        int numberOfPoints = dataHolder.getNumberOfPoints();
        roads = (numberOfPoints - 1) * numberOfPoints / 2;

        edgeTo = new Edge[numberOfPoints];
        objectives = new double[numberOfPoints];
        priorityQueue = new IndexMinPQ<>(numberOfPoints);

        for(int v = 0; v < objectives.length; v++) {
            objectives[v] = Double.POSITIVE_INFINITY;
        }
    }

    public void start() {
        initAlgorithm();

        Edge minDist = Collections.min(dataHolder.getEdges());
        Point startPoint = minDist.getBeginPoint();
        int startPointIndex = dataHolder.getPoints().indexOf(startPoint);

        objectives[startPointIndex] = 0.0;

        priorityQueue.insert(startPointIndex, 0.0);
        while(!priorityQueue.isEmpty()){
            int currentV = priorityQueue.delMinElementInPQ();
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
        Point startPoint = points.get(pointIndex);

        for(Edge startPointEdge: dataHolder.getAllPointEdges(startPoint)) {
            Point nextPoint = startPointEdge.getBeginPoint().equals(startPoint) ? startPointEdge.getEndPoint() : startPointEdge.getBeginPoint();

            int indexNextPoint = points.indexOf(nextPoint);

            double distance = startPointEdge.getWeight();
            double currObjective = dataHolder.getW1() * distance + dataHolder.getW2() * distance / roads;

            if(objectives[indexNextPoint] > currObjective) {
                objectives[indexNextPoint] = currObjective;
                
                if(Arrays.asList(edgeTo).contains(startPointEdge))
                    continue;
                
                edgeTo[indexNextPoint] = startPointEdge;
                
                if(priorityQueue.contains(indexNextPoint))
                    priorityQueue.change(indexNextPoint, objectives[indexNextPoint]);
                else
                    priorityQueue.insert(indexNextPoint, objectives[indexNextPoint]);
            }
        }
    }

    public void setDataHolder(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }
}
