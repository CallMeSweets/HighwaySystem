package generator;


import algorithm.Algorithm;
import data.DataHolder;
import data.Point;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private Algorithm algorithm;
    private DataHolder dataHolder;
    private List<Point> points;
    private File resultsFile;
    private FileWriter fileWriter;

    public DataGenerator() {
        generateData();
        dataHolder = new DataHolder(true);
        dataHolder.setW1(-1.2);
        dataHolder.setW1(4.7);
        algorithm = new Algorithm();
        createFile();
        createFileWriter();
    }

    private void generateData(){
        Random rand = new Random();
        points = new LinkedList<>();

        for(int i=0; i < 1000; i++){
            int x = rand.nextInt(2000);
            int y = rand.nextInt(2000);
            points.add(new Point(x, y));
        }

    }

    public void symulateAlgorithm(){
        for(int i=5; i < 6; i++){
            dataHolder.setPoints(points.subList(0, i));
            dataHolder.initEdges();
            algorithm.setDataHolder(dataHolder);
            System.out.println("\n");
            System.out.println("Iteration: " + (i-1));
            long startTime = System.nanoTime();
            algorithm.start();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("\n");
            saveDataToFile(i, duration);

            for(Point point: points.subList(0, i)){
                System.out.println(point.getX() + "," + point.getY());
            }



        }
        closeFileWriter();
    }

    private void createFile() {
        try {
            resultsFile = new File("src/main/resources/results.txt");
            if (resultsFile.createNewFile()) {
                System.out.println("File created: " + resultsFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void createFileWriter(){
        try {
            fileWriter = new FileWriter("src/main/resources/results.txt");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void closeFileWriter(){
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile(int numberOfPoints, double time){
        try {
            fileWriter.write("" + time + "\n");
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
