package NumericalMethods;

import Equation.CoffeeCoolingProcess;

public class Runge_KuttaMethod {
    public static Boolean Solve(CoffeeCoolingProcess coffeeCoolingProcess, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            coffeeCoolingProcess.Clear();
            coffeeCoolingProcess.addPoint(x, y);

            for (int i = 0; i < countIter; i++)
            {
                double k1 = coffeeCoolingProcess.computeFunction(x, y);
                double k2 = coffeeCoolingProcess.computeFunction(x + step / 2, y + step * k1 / 2);
                double k3 = coffeeCoolingProcess.computeFunction(x + step / 2, y + step * k2 / 2);
                double k4 = coffeeCoolingProcess.computeFunction(x + step, y + step * k3);

                y += step * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                x += step;
                coffeeCoolingProcess.addPoint(x, y);
            }
        }
        catch (Exception e) {
            System.out.println("Runge_KuttaMethod fail");
            return false;
        }
        return true;
    }
}
