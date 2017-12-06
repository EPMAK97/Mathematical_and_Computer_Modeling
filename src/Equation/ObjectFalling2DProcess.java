package Equation;

import java.util.ArrayList;
import Equation.ObjectFallingProcess;

public class ObjectFalling2DProcess {

    private static int modelCounter = 1;
    private int number;

    private ObjectFallingProcess processX, processY;

    public ObjectFalling2DProcess() {
        this.number = modelCounter++;
        processX = new ObjectFallingProcess();
        processY = new ObjectFallingProcess();
    }

    public void Clear() {
        processX.Clear();
        processY.Clear();
    }

    public void addPoint(Double t, Double x, Double y, Double vx, Double vy) {
        processX.addPoint(t, x, vx, 0.0);
        processY.addPoint(t, y, vy, 0.0);
    }

    public void setCoefficients(Integer constGrav, Integer grav, Integer buoyant, Integer linear, Integer square) {
        processX.setCoefficients(0, 0, 0, linear, square);
        processY.setCoefficients(constGrav, grav, buoyant, linear, square);
    }

    public void setBuoyantCoeff(Double densityBody, Double densityEnvironment) {
        // buoyant coefficient for Y is zero
        processX.setBuoyantCoeff(1.0, 0.0);
        processY.setBuoyantCoeff(densityBody, densityEnvironment);
    }

    public void setLinearResistanceCoeff(Double vz, Double p_env, Double p_body, Double R) {
        processX.setLinearResistanceCoeff(vz, p_env, p_body, R);
        processY.setLinearResistanceCoeff(vz, p_env, p_body, R);
    }

    public void setSquareResistanceCoeff(Double p_env, Double p_body, Double R) {
        processX.setSquareResistanceCoeff(p_env, p_body, R);
        processY.setSquareResistanceCoeff(p_env, p_body, R);
    }

    public Double computeAccelerationX(Double x, Double y, Double v) {
        return processX.computeAcceleration(x, y, v);
    }

    public Double computeAccelerationY(Double x, Double y, Double v) {
        return processY.computeAcceleration(x, y, v);
    }

    public ArrayList<Double> getNumericalVelocityX() { return processX.getNumericalVelocity(); }
    public ArrayList<Double> getNumericalVelocityY() { return processY.getNumericalVelocity(); }

    public ArrayList<Double> getTime() { return processX.getX(); }
    public ArrayList<Double> getX() { return processX.getY(); }
    public ArrayList<Double> getY() { return processY.getY(); }

    public void setRadius(Double _radius) {
        processX.setRadius(_radius);
        processY.setRadius(_radius);
    }

    public Double getX0() { return processX.getY0(); }
    public Double getY0() { return processY.getY0(); }

    public Double getV0_X() {
        return processX.getV0();
    }
    public Double getV0_Y() { return processY.getV0(); }

    public Double getXStart() {
        return processX.getXStart();
    }

    public void setXStart(Double _xStart) {
        processX.setXStart(_xStart);
        processY.setXStart(_xStart);
    }

    public Double getXFinish() {
        return processX.getXFinish();
    }

    public void setXFinish(Double _xFinish) {
        processX.setXFinish(_xFinish);
        processY.setXFinish(_xFinish);
    }

    public void setCoordinates(Double x, Double y) {
        processX.setY0(x);
        processY.setY0(y);
    }

    public void setVelocity(Double v, Double angle) {
        angle *= Math.PI / 180.0;
        processX.setV0(v * Math.cos(angle));
        processY.setV0(v * Math.sin(angle));
    }

    public void setEnvVelocity(Double x) {
        processX.setEnvVelocity(x);
        processY.setEnvVelocity(0.0);
    }

    public Integer getNumber() { return number; }

    public String getEnvironment() { return processX.getEnvironment(); }

    public void setEnvironment(String environment) {
        processX.setEnvironment(environment);
        processY.setEnvironment(environment);
    }

    public String getMaterialBody() {return processX.getMaterialBody(); }

    public void setMaterialBody(String materialBody) {
        processX.setMaterialBody(materialBody);
        processY.setMaterialBody(materialBody);
    }

    public void setF_A(String f_A) {
        processX.setF_A(f_A);
        processY.setF_A(f_A);
    }

    public String getF_A() { return processX.getF_A(); }

    public void setF_C1(String f_C1) {
        processX.setF_C1(f_C1);
        processY.setF_C1(f_C1);
    }

    public void setF_C2(String f_C2) {
        processX.setF_C2(f_C2);
        processY.setF_C2(f_C2);
    }

    public void setConstGravity(String constGravity) {
        processX.setConstGravity(constGravity);
        processY.setConstGravity(constGravity);
    }

    public String getF_C1() {
        return processX.getF_C1();
    }

    public String getF_C2() { return processX.getF_C2(); }

    public String getConstGravity() { return processX.getConstGravity(); }

    public int getN() { return processX.getN(); }

    public void setN(int n) {
        processX.setN(n);
        processY.setN(n);
    }
}
