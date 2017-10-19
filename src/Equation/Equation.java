package Equation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Equation {

    private ArrayList<Point2D.Double> _NumericalSolution = new ArrayList<>();
    private ArrayList<Double> _AbsoluteErrors = new ArrayList<>();
    private ArrayList<Double> _RelativeErrors = new ArrayList<>();
    private ArrayList<Double> _AnalyticalSolution = new ArrayList<>();
    private ArrayList<Double> _PointsX = new ArrayList<>();

    private int N;
    private Double y0;
    private Double xStart;
    private Double xFinish;

    public Equation() {

    }

    public void Clear() {
        _NumericalSolution  = new ArrayList<>();
        _AbsoluteErrors     = new ArrayList<>();
        _RelativeErrors     = new ArrayList<>();
        _AnalyticalSolution = new ArrayList<>();
        _PointsX            = new ArrayList<>();
    }

    public abstract double computeFunction(double x, double y);

    public abstract double computeAnalyticalSolution(int i);

    public void addPoint(double x, double y) {
        _NumericalSolution.add(new Point2D.Double(x, y));
    }

    public void ShowInConsole() {
        for (Point2D.Double point: _NumericalSolution)
            System.out.println(point.getX() + "  --  " +  point.getY());
    }

    public ArrayList<Double> getX() {
        if (_NumericalSolution.isEmpty()) return computePointsX();
        ArrayList<Double> pointsX = new ArrayList<Double>();
        for (int i = 0; i < _NumericalSolution.size(); i++)
            pointsX.add(_NumericalSolution.get(i).getX());
        return pointsX;
    }

    public ArrayList<Double> getY() {
        ArrayList<Double> pointsY = new ArrayList<Double>();
        for (int i = 0; i < _NumericalSolution.size(); i++) {
            pointsY.add(_NumericalSolution.get(i).getY());
        }
        return pointsY;
    }

    public ArrayList<Double> getAbsoluteErrors() {
        if (_NumericalSolution.isEmpty()) return null;
        if (_AnalyticalSolution.isEmpty()) getAnalyticalSolution();
        for (int i = 0; i < _NumericalSolution.size(); i++) {
            _AbsoluteErrors.add((Math.abs(_AnalyticalSolution.get(i) - _NumericalSolution.get(i).getY())));
        }
        return _AbsoluteErrors;
    }

    public ArrayList<Double> getRelativeErrors() {
        if (_NumericalSolution.size() == 0) return null;
        if (_AnalyticalSolution.isEmpty()) getAnalyticalSolution();
        for (int i = 0; i < _NumericalSolution.size(); i++) {
            _RelativeErrors.add(Math.abs((_AnalyticalSolution.get(i) - _NumericalSolution.get(i).getY()) / _AnalyticalSolution.get(i)));
        }
        return _RelativeErrors;
    }

    public ArrayList<Double> getAnalyticalSolution() {
        if (!_AnalyticalSolution.isEmpty()) return _AnalyticalSolution;
        if (_PointsX.isEmpty()) computePointsX();
        for (int i = 0; i <= N; i++) {
            //_AnalyticalSolution.add(-2.0 * (_PointsX.get(i) + 2.0) + 4 * Math.exp(1.0 / 2.0 * _PointsX.get(i)));
            //_AnalyticalSolution.add((T0 - Math.pow(xStart, 2.0) + 2 * xStart - 6) / Math.exp(-xStart));
            //_AnalyticalSolution.add((T0 + 2.0) / Math.exp(xStart) * Math.exp(_PointsX.get(i)) - 2.0);
            //_AnalyticalSolution.add(Tc + (T0 - Tc) * Math.exp(-r * _PointsX.get(i)));
            _AnalyticalSolution.add(computeAnalyticalSolution(i));

        }
        return _AnalyticalSolution;
    }

    public ArrayList<Double> computePointsX() {
        _PointsX.clear();
        double x = xStart;
        for (int i = 0; i <= N; i++) {
            _PointsX.add(x);
            x += getStep();
        }
        return _PointsX;
    }

    public Double get_PointsX(int i) { return _PointsX.get(i); }

    public Double getStep() {
        return (xFinish - xStart) / N;
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

    public int getN() { return N; }

    public void setN(int n) { N = n; }

}
