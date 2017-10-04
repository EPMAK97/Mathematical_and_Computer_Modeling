package Equation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Equation {
    private static ArrayList<Point2D.Double> _list = new ArrayList<>();
    private static ArrayList<Double> _AbsoluteErrors = new ArrayList<>();
    private static ArrayList<Double> _RelativeErrors = new ArrayList<>();
    private static ArrayList<Double> _AnaliticalSolution = new ArrayList<>();
    private static ArrayList<Double> _PointsX = new ArrayList<>();

    // Rename please
    private static Double T0;
    private static Double Tc;
    private static Double r;

    private static Double step;

    public Equation() { }

    public double MakeFunction(double x, double y) {
        //return x * x - 2 * y;
        return -r * y + r * x;
    }

    public Point2D.Double GetPosint(int i) {
        return _list.get(i);
    }

    public void SetPoint(double x, double y) {
        _list.add(new Point2D.Double(x, y));
    }

    public void ShowInConsole() {
        for (Point2D.Double point: _list) {
            System.out.println(point.getX() + "  --  " +  point.getY());
        }
    }

    public static double[] getX() {
        double[] pointsX = new double[_list.size()];
        for (int i = 0; i < _list.size(); i++) {
            pointsX[i] = _list.get(i).getX();
        }
        return pointsX;
    }

    public static double[] getY() {
        double[] pointsY = new double[_list.size()];
        for (int i = 0; i < _list.size(); i++) {
            pointsY[i] = _list.get(i).getY();
        }
        return pointsY;
    }

    public ArrayList<Double> getAbsoluteErrors() {
        if (_list.isEmpty()) return null;
        if (_AnaliticalSolution.isEmpty()) computeAnalyticalSolution();
        for (int i = 0; i < _list.size(); i++) {
            _AbsoluteErrors.add((Math.abs(_AnaliticalSolution.get(i) - _list.get(i).getY())));
        }
        return _AbsoluteErrors;
    }

    public ArrayList<Double> getRelativeErrors() {
        if (_list.isEmpty()) return null;
        if (_AnaliticalSolution.isEmpty()) computeAnalyticalSolution();
        for (int i = 0; i < _list.size(); i++) {
            _RelativeErrors.add((Math.abs(_AnaliticalSolution.get(i) - _list.get(i).getY())/_AnaliticalSolution.get(i)));
        }
        return _RelativeErrors;
    }

    public void computeAnalyticalSolution() {
        if (_PointsX.isEmpty()) computePointsX();
        for (int i = 0; i < _list.size(); i++) {
            _AnaliticalSolution.add(Tc + (T0 - Tc) * Math.exp(-r * _PointsX.get(i)));
        }
    }

    public void computePointsX() {
        double x = T0;
        for (int i = 0; i < _list.size(); i++) {
            _PointsX.add(x);
            x += step;
        }
    }

    public ArrayList<Double> getPointsX() {
        return _PointsX;
    }

    public static Double getT0() {
        return T0;
    }

    public static void setT0(Double t0) {
        T0 = t0;
    }

    public static Double getTc() {
        return Tc;
    }

    public static void setTc(Double tc) {
        Tc = tc;
    }

    public static Double getR() {
        return r;
    }

    public static void setR(Double r) {
        Equation.r = r;
    }

    public static Double getStep() {
        return step;
    }

    public static void setStep(Double step) {
        Equation.step = step;
    }
}