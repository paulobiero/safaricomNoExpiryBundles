package sample;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LinearRegression {
    private RealMatrix w,estimate;

    public RealMatrix getW() {
        return w;
    }

    public void setW(RealMatrix w) {
        this.w = w;
    }

    public RealMatrix getEstimate() {
        return estimate;
    }

    public void setEstimate(RealMatrix estimate) {
        this.estimate = estimate;
    }

    public LinearRegression(double xArray[][], double yArray[][]) throws Exception {
 applyNormalEquation(MatrixUtils.createRealMatrix(xArray),MatrixUtils.createRealMatrix(yArray));
    }
    private void applyNormalEquation(RealMatrix x,RealMatrix y) throws Exception {
        LUDecomposition luDecomposition=new LUDecomposition(x.transpose().multiply(x));
        if(luDecomposition.getDeterminant()==0.0)
            throw new Exception("Singular matrix with no exception");
        else
        {
            w=luDecomposition.getSolver().getInverse().multiply(x.transpose().multiply(y));
        }
        estimate=x.multiply(w);
    }
    public double estimatedBundles(String entry)
    {
        return MatrixUtils.createColumnRealMatrix(new double[]{1,Double.valueOf(entry)}).transpose().multiply(w).getData()[0][0];
    }


}
