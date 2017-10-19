package Equation;

public class CoffeeCoolingProcess extends Equation {

    private Double T0;
    private Double Tc;
    private Double r;

    public CoffeeCoolingProcess() {

    }

    @Override
    public double computeFunction(double x, double y) {
        return 1.0 / 2.0 * y + x;
        //return -Math.pow(x, 2.0) + y + 4;
        //return y + 2;
        //return -r * y + r * Tc;
    }

    @Override
    public double computeAnalyticalSolution(int i) {
        return  -2.0 * (get_PointsX(i) + 2.0) + 4 * Math.exp(1.0 / 2.0 * get_PointsX(i));
    }

    public Double getT0() {
        return T0;
    }

    public void setT0(Double t0) {
        T0 = t0;
    }

    public Double getTc() {
        return Tc;
    }

    public void setTc(Double tc) {
        Tc = tc;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double _r) {
        r = _r;
    }

}