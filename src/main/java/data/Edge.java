package data;

public class Edge implements Comparable<Edge> {

    private Point beginPoint;
    private Point endPoint;
    private int weight;

    public Edge(final Point beginPoint, final Point endPoint, final int weight) {
        this.beginPoint = beginPoint;
        this.endPoint = endPoint;
        this.weight = weight;
    }

    public Point getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(final Point beginPoint) {
        this.beginPoint = beginPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(final Point endPoint) {
        this.endPoint = endPoint;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(final int weight) {
        this.weight = weight;
    }

    public int compareTo(Edge edge) {
        if(weight > edge.weight)
            return 1;
        else if(weight == edge.weight)
            return 0;
        else
            return -1; 
    }

    public String toString() {
        return beginPoint.toString() + " <=> " + endPoint.toString();
    }
}
