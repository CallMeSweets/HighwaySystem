package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {

    private static final String FILE_PATH = "src/main/resources/entry.txt";

    public void readDataFromFile(DataHolder dataHolder) {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.out.println("File can not be found");
        }

        Stream <String> lines = br.lines()
                .map(str -> parseData(str, dataHolder));
        lines.forEach(line -> {

        });

        lines.close();
    }

    private String parseData(String str, DataHolder dataHolder) {

        int w1FactorIndex = str.indexOf("w1=");
        if(w1FactorIndex != -1){
            String w1Factor = str.substring(str.indexOf("=") + 1, str.length());
            dataHolder.setW1(Integer.parseInt(w1Factor));
            return w1Factor;
        }

        int w2FactorIndex = str.indexOf("w2=");
        if(w2FactorIndex != -1){
            String w2Factor = str.substring(str.indexOf("=") + 1, str.length());
            dataHolder.setW2(Integer.parseInt(w2Factor));
            return w2Factor;
        }


        String x = str.substring(0, str.indexOf(","));
        String y = str.substring(str.indexOf(",") + 1, str.length());
        Point point = new Point(Integer.parseInt(x), Integer.parseInt(y));

        dataHolder.addPoint(point);

        return str.substring(0, str.indexOf(","));
    }

}
