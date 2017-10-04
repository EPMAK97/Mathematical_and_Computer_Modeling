package Equation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Equation {
    private static ArrayList<Point2D.Double> _list = new ArrayList<>();

    public Equation() { }

    public double MakeFunction(double x, double y) {
        return x * x - 2 * y;
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

}