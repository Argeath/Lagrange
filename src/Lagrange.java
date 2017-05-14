/**
* @author Dominik Kinal <kinaldominik@gmail.com>
*/
class Lagrange {
    private InputData inputData;

    Lagrange(InputData data) {
        inputData = data;
    }

    double Li(double x, int k, int fromN, int toN) {
        double result = 1;

        for(int j = fromN; j < toN; j++) {
            if(j == k) continue;

            result *= x - inputData.distances[j];
            result /= inputData.distances[k] - inputData.distances[j];
        }

        return result;
    }

    double interpolate(double x, int fromN, int toN)
    {
        double sum = 0;

        for (int i=fromN; i<toN; i++)
            sum += inputData.heights[i] * Li(x, i, fromN, toN);

        return sum;
    }

    InputData chart(int resolution, int n) {
        InputData output = new InputData();
        int elements = (inputData.distances.length - 1) * (resolution + 1) + 1;
        output.distances = new double[elements];
        output.heights = new double[elements];

        int index = 0;
        int len = inputData.distances.length;
        for (int i = 0; i < len - 1; i++) {
            int fromN = n;
            if (i < fromN) fromN = i;

            int toN = n;
            if (len - i <= toN) toN = len - i - 1;
            // len = 50, i = 46, n = 4, 50 - 46 = 4, 3 <= 4

            output.distances[index] = inputData.distances[i];
            output.heights[index++] = inputData.heights[i];

            double odl = (inputData.distances[i + 1] - inputData.distances[i]) / (resolution + 1);
            for (int r = 0; r < resolution; r++) {
                double x = inputData.distances[i] + (odl * (r + 1));
                output.distances[index] = x;
                output.heights[index++] = interpolate(x, i - fromN, i + toN);
            }
        }

        output.distances[index] = inputData.distances[len - 1];
        output.heights[index] = inputData.heights[len - 1];

        return output;
    }
}
