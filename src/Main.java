public class Main {

    public static void main(String[] args) {
        InputData d = InputData.readCSV("jasienGaleriaMorena.csv");

        Lagrange l = new Lagrange(d);
        InputData out = l.chart(1);
        out.print();
        out.saveCSV("test.csv");
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