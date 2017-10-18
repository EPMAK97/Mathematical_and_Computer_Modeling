package NumericalMethods;


import Equation.Equation;

public class EulerMethodImproved {
    public static Boolean Solve(Equation equation, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            equation.Clear();
            equation.addPoint(x, y);

            for (int i = 0; i < countIter; i++) {
                y += step * equation.computeFunction(x + step / 2, y + step / 2 * equation.computeFunction(x, y));
                x += step;
                equation.addPoint(x, y);
            }
        }
        catch (Exception e) {
            System.out.println("EulerMethodImproved fail");
            return false;
        }
        return true;
    }
}
