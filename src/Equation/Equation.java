package Equation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Equation {
    private ArrayList<Point2D.Double> _list = new ArrayList<>();
    private ArrayList<Double> _AbsoluteErrors = new ArrayList<>();
    private ArrayList<Double> _RelativeErrors = new ArrayList<>();
    private ArrayList<Double> _AnaliticalSolution = new ArrayList<>();
    private ArrayList<Double> _PointsX = new ArrayList<>();

    // Rename please
    private Double T0;
    private Double Tc;
    private Double r;

    private Double xStart;
    private Double xFinish;
    private Double step;

    public Equation() { }

    public void Clear() {
        _list.clear();
        _AbsoluteErrors.clear();
        _RelativeErrors.clear();
        _AnaliticalSolution.clear();
        _PointsX.clear();
    }

    public double MakeFunction(double x, double y) {
        //return x * x - 2 * y;
        return -r * y + r * Tc;
    }

    public Point2D.Double GetPoint(int i) {
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

    public ArrayList<Double> getX() {
        ArrayList<Double> pointsX = new ArrayList<Double>();
        for (int i = 0; i < _list.size(); i++) {
            pointsX.add(_list.get(i).getX());
        }
        return pointsX;
    }

    public ArrayList<Double> getY() {
        ArrayList<Double> pointsY = new ArrayList<Double>();
        for (int i = 0; i < _list.size(); i++) {
            pointsY.add(_list.get(i).getY());
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
        if (_list.size() == 0) return null;
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
        double x = xStart;
        for (int i = 0; i < _list.size(); i++) {
            _PointsX.add(x);
            x += step;
        }
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

    public Double getStep() {
        return step;
    }

    public void setStep(Double _step) {
        step = _step;
    }

    public Double getXStart() {
        return xStart;
    }

    public void setXStart(Double _xStart) {
        xStart = _xStart;
    }

    public Double getXFinish() {
        return xFinish;
    }

    public void setXFinish(Double _xFinish) {
        xFinish = _xFinish;
    }

}