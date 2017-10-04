package NumericalMethods;

import Equation.Equation;

public class Runge_KuttaMethod {
    public static Boolean Solve(Equation equation, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            equation.setStep(step);

            for (int i = 0; i < countIter; i++)
            {
                double k1 = equation.MakeFunction(x, y);
                double k2 = equation.MakeFunction(x + step / 2, y + step * k1 / 2);
                double k3 = equation.MakeFunction(x + step / 2, y + step * k2 / 2);
                double k4 = equation.MakeFunction(x + step, y + step * k3);

                equation.SetPoint(x, y);
                y += step * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                x += step;
            }
        }
        catch (Exception e) {
            System.out.println("Runge_KuttaMethod fail");
            return false;
        }
        return true;
    }
}
