package Equation;

public class CoffeeCoolingProcess extends Equation {

    private Double T0;
    private Double Tc;
    private Double r;

    public CoffeeCoolingProcess() {

    }

    @Override
    public double computeFunction(double x, double y) {
        //return 1.0 / 2.0 * y + x;
        //return -Math.pow(x, 2.0) + y + 4;
        //return y + 2;
        return -r * y + r * Tc;
    }

    @Override
    public double computeAnalyticalSolution(int i) {
        //Double C_1 = (T0 + 2 * getXStart() + 4) / Math.exp(0.5 * getXStart());
        //return C_1 * Math.exp(0.5 * get_PointsX(i)) - 2 * get_PointsX(i) - 4;
        //-2.0 * (get_PointsX(i) + 2.0) + 4 * Math.exp(1.0 / 2.0 * get_PointsX(i));
        return Tc + (T0 - Tc) * Math.exp(-r * get_PointsX(i));
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