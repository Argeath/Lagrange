import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amino on 12.05.2017.
 */
public class InputData {
    double distances[];
    double heights[];

    static InputData readCSV(String path) {
        String cvsSplitBy = ",";
        InputData inputData = new InputData();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.mark(100000);
            int length = (int)br.lines().count();
            br.reset();
            inputData.distances = new double[length];
            inputData.heights = new double[length];

            final AtomicInteger count = new AtomicInteger(0);
            br.lines().forEach(s -> {
                String[] data = s.split(cvsSplitBy);

                System.out.println("Wezel [x= " + data[0] + ", h=" + data[1] + "]");
                inputData.distances[count.get()] = Double.parseDouble(data[0]);
                inputData.heights[count.getAndIncrement()] = Double.parseDouble(data[1]);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputData;
    }

    void saveCSV(String path) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            for(int i = 0; i < distances.length; i++) {
                br.write(distances[i] + "," + heights[i] + "\n");
            }
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void print() {
        for(int i = 0; i < distances.length; i++)
            System.out.println(distances[i] + ", " + heights[i]);
    }


}
