package data;

public class Edge {

    private Point beginPoint;
    private Point endPoint;
    private int weight;

    public Edge(Point beginPoint, Point endPoint, int weight) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
        this.weight = weight;
    }

    public Point getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(Point beginPoint) {
        this.beginPoint = beginPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
