package NumericalMethods;

import Equation.ObjectFallingProcess;

public class Euler_KromerMethod_ocsillator_next {
    public static Boolean Solve(ObjectFallingProcess equation, int countIter, double x0, double x1, double y0) {
        try {
            double step = (x1 - x0) / countIter;
            double x = x0, y = y0, v = equation.getV0();
            equation.Clear();
            equation.addPoint(x0, y0, v, equation.computeAcceleration(x0, y0, v));

            for (int i = 1; i <= countIter; ++i) {
                v = v + step * equation.computeAcceleration(x, y, v);
                y = y + step * v;
                x += step;
                equation.addPoint(x, y, v, equation.computeAcceleration(x, y, v));
            }
        } catch (Exception e) {
            System.out.println("Euler_KromerMethod fail");
            return false;
        }
        return true;
    }
}
