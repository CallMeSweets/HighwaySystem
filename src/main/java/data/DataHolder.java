package data;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    private int W1;
    private int W2;

    private List<Point> points = new ArrayList<>();
    private List<Edge>  edges = new ArrayList<>();

    public DataHolder() {
        initDataFromFile();
        initEdges();
    }


    private void initDataFromFile() {
        FileReader fileReader = new FileReader();
        fileReader.readDataFromFile(this);
    }

    private void initEdges() {
        for(int i = 0; i < points.size(); i++){
            Point p1 = points.get(i);
            for(int j = i + 1; j < points.size(); j++){
                Point p2 = points.get(j);

                int xDistance = p1.getX() - p2.getX();
                int yDistance = p1.getY() - p2.getY();
                int weight = (int)Math.sqrt(xDistance*xDistance + yDistance*yDistance);

                Edge edge = new Edge(p1, p2, weight);
                edges.add(edge);
            }
        }
    }

    public Edge getEdgeByPoints(Point p1, Point p2){
        for(Edge edge: edges){
            Point beginPoint = edge.getBeginPoint();
            Point endPoint = edge.getEndPoint();

            if(arePointsEquals(beginPoint, p1) && arePointsEquals(endPoint, p2)){
                return edge;
            }else if(arePointsEquals(beginPoint, p2) && arePointsEquals(endPoint, p1)){
                return edge;
            }
        }

        return null;
    }

    public List<Edge> getAllPointEdges(Point point){
        return getPointEdges(point);
    }

    private List<Edge> getPointEdges(Point point){
        List<Edge> edgesForPoint = new ArrayList<>();
        for(Edge edge: edges){
            Point p1 = edge.getBeginPoint();
            Point p2 = edge.getEndPoint();
            if(point.getX() == p1.getX() && point.getY() == p1.getY()){
                edgesForPoint.add(edge);
            }
            if(point.getX() == p2.getX() && point.getY() == p2.getY()){
                edgesForPoint.add(edge);
            }
        }

        return edgesForPoint;
    }

    public Boolean arePointsEquals(Point p1, Point p2){
        if(p1.getX() == p2.getX() && p1.getY() == p2.getY()){
            return true;
        }
        return false;
    }

    public Integer getNumberOfPoints(){
        return points.size();
    }

    public Integer getNumberOfEdges(){
        return edges.size();
    }

    public Integer getNumberOfPointEdges(Point point){
        return  getPointEdges(point).size();
    }

    public Boolean removePoint(Point point){
        return points.remove(point);
    }

    public Boolean removeEdge(Edge edge){
        return edges.remove(edge);
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getW1() {
        return W1;
    }

    public void setW1(int w1) {
        W1 = w1;
    }

    public int getW2() {
        return W2;
    }

    public void setW2(int w2) {
        W2 = w2;
    }

    public void addPoint(Point point){
        points.add(point);
    }


}
