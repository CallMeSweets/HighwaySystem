
import algorithm.Algorithm;
import generator.DataGenerator;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to highway system");

        //Algorithm read data from file
        Algorithm algorithm = new Algorithm();
        algorithm.start();


        // DataGenerator for tests: find time for number of points from 2-249
//        DataGenerator dataGenerator = new DataGenerator();
//        dataGenerator.symulateAlgorithm();
    }
}
