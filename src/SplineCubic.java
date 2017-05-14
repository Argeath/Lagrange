/**
 * Performs spline interpolation given a set of control points.
 *
 */
class SplineCubic {

    private final InputData inputData;
    private final double[] mM;

    private SplineCubic(InputData data, double[] m) {
        inputData = data;
        mM = m;
    }

    static SplineCubic createMonotoneCubicSpline(InputData data) {
        final int n = data.distances.length;
        double[] d = new double[n - 1];
        double[] m = new double[n];

        for (int i = 0; i < n - 1; i++) {
            double h = data.distances[i + 1] - data.distances[i];
            d[i] = (data.heights[i + 1] - data.heights[i]) / h;
        }

        m[0] = d[0];
        for (int i = 1; i < n - 1; i++) {
            m[i] = (d[i - 1] + d[i]) * 0.5f;
        }
        m[n - 1] = d[n - 2];

        for (int i = 0; i < n - 1; i++) {
            if (d[i] == 0f) {
                m[i] = 0f;
                m[i + 1] = 0f;
            } else {
                double a = m[i] / d[i];
                double b = m[i + 1] / d[i];
                double h = Math.hypot(a, b);
                if (h > 9f) {
                    double t = 3f / h;
                    m[i] = t * a * d[i];
                    m[i + 1] = t * b * d[i];
                }
            }
        }
        return new SplineCubic(data, m);
    }

    double interpolate(double x) {
        int i = 0;
        while (x >= inputData.distances[i + 1]) {
            i += 1;
            if (x == inputData.distances[i]) {
                return inputData.heights[i];
            }
        }

        // Perform cubic Hermite spline interpolation.
        double h = inputData.distances[i + 1] - inputData.distances[i];
        double t = (x - inputData.distances[i]) / h;
        return (inputData.heights[i] * (1 + 2 * t) + h * mM[i] * t) * (1 - t) * (1 - t)
                + (inputData.heights[i + 1] * (3 - 2 * t) + h * mM[i + 1] * (t - 1)) * t * t;
    }

    InputData chart(int resolution) {
        InputData output = new InputData();
        int elements = (inputData.distances.length - 1) * (resolution + 1) + 1;
        output.distances = new double[elements];
        output.heights = new double[elements];

        int index = 0;
        int len = inputData.distances.length;
        for (int i = 0; i < len - 1; i++) {
            output.distances[index] = inputData.distances[i];
            output.heights[index++] = inputData.heights[i];

            double odl = (inputData.distances[i + 1] - inputData.distances[i]) / (resolution + 1);
            for (int r = 0; r < resolution; r++) {
                double x = inputData.distances[i] + (odl * (r + 1));
                output.distances[index] = x;
                output.heights[index++] = interpolate(x);
            }
        }

        output.distances[index] = inputData.distances[len - 1];
        output.heights[index] = inputData.heights[len - 1];

        return output;
    }
}