/**
* @author Dominik Kinal <kinaldominik@gmail.com>
*/
public class Main {

    public static void main(String[] args) throws Exception {
        chart("maps/naPG_128");
        chart("maps/norway_128");
    }

    private static void chart(String file) throws Exception {
        InputData min = null;
        double minNorm = 999999999;
        for(int i = 2; i < 10; i++) {
            InputData d = InputData.readCSV(file + ".csv");
            InputData half = d.reduceByOne();

            Lagrange l = new Lagrange(half);
            InputData out = l.chart(1, i);

            double norma = norm(out.compare(d));
            System.out.println(i + ", " + norma);
            if(norma < minNorm) {
                minNorm = norma;
                min = out;
            }
        }

        if (min != null)
            min.saveCSV(file + "_lagrange.csv");

        InputData d = InputData.readCSV(file + ".csv");
        InputData half = d.reduceByOne();
        SplineCubic s = SplineCubic.createMonotoneCubicSpline(half);
        InputData out = s.chart(1);
        double norma = norm(out.compare(d));
        System.out.println("CubicSpline " + norma);
        out.saveCSV(file + "_cubic.csv");
    }

    private static double norm(double[] d) {
        double sum = 0;
        for (double a : d) {
            sum += a * a;
        }

        return Math.sqrt(sum);
    }
}


/*
        * Lagrange
        *

        float getLi(unsigned int i, unsigned int n0, unsigned int n, float x) {
        #ifdef _DEBUG_
        printf("Product of: %u %u %u\n",i,n0,n);
        #endif
        float product = 1;
        unsigned int j;
        for(j=n0;j<n;++j) {
        if(j!=i) product*= (x-D[j]) / (D[i]-D[j]);
        }
        return product;
        }

        float function(float x) {
        float sum = 0;
        unsigned int i,n;
        n=(int)(x/D_MAX*D_LENGTH);
        #ifdef _DEBUG_
        printf("function of %u %.3f\n",n,x);
        #endif
        if(n>0 && n<D_LENGTH-2)
            for(i=n-1;i<n+2;++i)
                sum+=getLi(i,n-1,n+2,x)*H[i];
        else if(n>0 && n<D_LENGTH-1)
            for(i=n-1;i<n+1;++i)
                sum+=getLi(i,n-1,n+1,x)*H[i];
        else if(n>0 && n<D_LENGTH)
            for(i=n-1;i<n;++i)
                sum+=getLi(i,n-1,n,x)*H[i];
        else if(n<=0)
            for(i=n;i<n+2;++i)
                sum+=getLi(i,n,n+2,x)*H[i];
        return sum;
        }*/