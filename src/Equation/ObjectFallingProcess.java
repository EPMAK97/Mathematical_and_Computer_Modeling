package Equation;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class ObjectFallingProcess extends Equation {

    private static int modelCounter = 1;
    private int number;

    private ArrayList<Double> _NumericalVelocity = new ArrayList<>();
    private ArrayList<Double> _NumericalAcceleration = new ArrayList<>();
    private Double V0;

    private static final Double G = 6.67408e-11;
    private static final Double g_0 = 9.81;
    private static final Double PlanetR = 6.4e6;

    private Double k_buoyant = 0.0;
    private Double k_linear = 0.0;
    private Double k_square = 0.0;
    private Double radius = 0.0;

    private Integer CConstGrav, CGrav;
    private Integer CBuoyant;
    private Integer CLinearAcc, CSquareAcc;

    private String environment;
    private String materialBody;
    private String F_A          = "нет";
    private String F_C1         = "нет";
    private String F_C2         = "нет";
    private String constGravity = "нет";

    @Override
    public void Clear() {
        super.Clear();
        _NumericalVelocity = new ArrayList<>();
        _NumericalAcceleration = new ArrayList<>();
    }

    private Double ConstantGravityAcceleration() {
        return -g_0;
    }

    private Double GravityAcceleration(Double h) {
        return -g_0 / Math.pow(1 + h / PlanetR, 2.0);
    }

    private Double BuoyantForce() {
        return k_buoyant * g_0;
    }

    private Double LinearResistance(Double v) {
        return -k_linear * v;
    }

    private Double SquareResistance(Double v) {
        return -k_square * v * Math.abs(v);
    }

    public ObjectFallingProcess() {
        this.number = modelCounter++;
    }

    public Double computeAcceleration(Double x, Double y, Double v) {
        return  + CConstGrav * ConstantGravityAcceleration()
                + CGrav * GravityAcceleration(y)
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
    }

    public void setCoefficients(Integer constGrav, Integer grav, Integer buoyant, Integer linear, Integer square) {
        CConstGrav = constGrav;
        CGrav = grav;
        CBuoyant = buoyant;
        CLinearAcc = linear;
        CSquareAcc = square;
    }

    public void setBuoyantCoeff(Double densityBody, Double densityEnvironment) {
        k_buoyant = densityEnvironment / densityBody;
    }

    public void setLinearResistanceCoeff(Double vz, Double p_env, Double p_body, Double R) {
        k_linear = 9.0 / 2.0 * (vz * p_env) / (p_body * Math.pow(R, 2.0));
    }

    public void setSquareResistanceCoeff(Double p_env, Double p_body, Double R) {
        k_square = 3.0 / 4.0 * p_env / (p_body * R);
    }

    public ArrayList<Double> getNumericalVelocity() {
        return new ArrayList<Double> (_NumericalVelocity);
    }

    public ArrayList<Double> getNumericalAcceleration() {
        return new ArrayList<Double> (_NumericalAcceleration);
    }

    public void setRadius(Double _radius) {
        radius = _radius;
    }

    public Double getRadius() {return radius; }

    public Double getV0() {
        return V0;
    }

    public void setV0(Double _V0) {
        V0 = _V0;
    }

    public Integer getNumber() {
        return number;
    }

    public String getEnvironment() { return environment; }

    public void setEnvironment(String environment) { this.environment = environment; }

    public String getMaterialBody() {return materialBody; }

    public void setMaterialBody(String materialBody) { this.materialBody = materialBody; }

    public void setF_A(String f_A) { F_A = f_A; }

    public String getF_A() { return F_A; }

    public void setF_C1(String f_C1) { F_C1 = f_C1; }

    public void setF_C2(String f_C2) { F_C2 = f_C2; }

    public void setConstGravity(String constGravity) { this.constGravity = constGravity; }

    public String getF_C1() {
        if (F_C1.equals("нет") && F_C2.equals("нет"))
            return "нет";
        return F_C1.equals("учтено") ? "линейная" : "квадратичная"; }

    public String getF_C2() { return F_C2; }

    public String getConstGravity() { return constGravity; }

}
