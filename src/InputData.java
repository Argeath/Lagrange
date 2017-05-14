import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amino on 12.05.2017.
 */
public class InputData {
    double distances[];
    double heights[];
    double delta[];

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
                if(delta.length > i)
                    br.write(distances[i] + "," + heights[i] + "," + delta[i] + "\n");
                else
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

    InputData reduceByOne() {
        InputData output = new InputData();

        output.distances = new double[distances.length / 2];
        output.heights = new double[distances.length / 2];

        for(int i = 0; i < distances.length; i += 2) {
            output.distances[i/2] = distances[i];
            output.heights[i/2] = heights[i];
        }

        return output;
    }

    double[] compare(InputData d) throws Exception {

        double w[] = new double[distances.length / 2];
        delta = new double[distances.length];

        for(int i = 0, j = 0; i < distances.length; i++) {
            if(Math.abs(d.distances[i] - distances[i]) > 0.0001 )
                throw new Exception(d.distances[i] + " : " + distances[i]);
            double sub = d.heights[i] - heights[i];

            if(Math.abs(sub) > 0.000000000000000000000001)
                w[j++] = sub;

            delta[i] = sub;
            //System.out.println(distances[i] + ": " + delta[i]);
        }

        return delta;
    }
}
