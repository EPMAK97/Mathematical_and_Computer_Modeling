package Equation;

import java.util.ArrayList;

public class ObjectFallingProcess extends Equation {

    private ArrayList<Double> _NumericalVelocity = new ArrayList<>();
    private ArrayList<Double> _NumericalAcceleration = new ArrayList<>();
    private Double V0;

    private static final Double G = 6.67408e-11;
    private static final Double g_0 = 9.81;
    private static final Double PlanetR = 6.4e6;

    private Double k_buoyant = 0.0;
    private Double k_linear = 0.0;
    private Double k_square = 0.0;
    private Double mass;
    private Double radius;

    private Integer CConstGrav, CGrav;
    private Integer CBuoyant;
    private Integer CLinearAcc, CSquareAcc;

    private Double ConstantGravityAcceleration() {
        return g_0;
    }

    private Double GravityAcceleration(Double h) {
        return g_0 / Math.pow(1 + h / PlanetR, 2.0);
    }

    private Double BuoyantForce() {
        return k_buoyant * g_0;
    }

    private Double LinearResistance(Double v) {
        return -k_linear * v / mass;
    }

    private Double SquareResistance(Double v) {
        return -k_square * v * Math.abs(v) / mass;
    }

    public ObjectFallingProcess() {

    }

    public Double computeAcceleration(Double x, Double y, Double v) {
        return  - CConstGrav * ConstantGravityAcceleration()
                - CGrav * GravityAcceleration(y)
                + CBuoyant * BuoyantForce()
                + CLinearAcc * LinearResistance(v)
                + CSquareAcc * SquareResistance(v);
    }

    public void addPoint(Double x, Double y, Double v, Double a) {
        super.addPoint(x, y);
        _NumericalAcceleration.add(a);
        _NumericalVelocity.add(v);
    }

    @Override
    public double computeFunction(double x, double y) {
        return 0;
    }

    @Override
    public double computeAnalyticalSolution(int i) {
        return 0;
        //return  -2.0 * (get_PointsX(i) + 2.0) + 4 * Math.exp(1.0 / 2.0 * get_PointsX(i));
    }

    public void setCoefficients(Integer constGrav, Integer grav, Integer buoyant, Integer linear, Integer square) {
        CConstGrav = constGrav;
        CGrav = grav;
        CBuoyant = buoyant;
        CLinearAcc = linear;
        CSquareAcc = square;
    }

    public void setBuoyantCoeff(Double coeff) {
        k_buoyant = coeff;
    }

    public void setLinearResistanceCoeff(Double coeff) {
        k_linear = coeff;
    }

    public void setSquareResistanceCoeff(Double coeff) {
        k_square = coeff;
    }

    public void setMass(Double _mass) {
        mass = _mass;
    }

    public void setRadius(Double _radius) {
        radius = _radius;
    }

    public Double getV0() {
        return V0;
    }

    public void setV0(Double _V0) {
        V0 = _V0;
    }

}
