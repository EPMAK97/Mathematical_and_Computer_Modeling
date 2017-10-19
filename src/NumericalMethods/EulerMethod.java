package NumericalMethods;


import Equation.CoffeeCoolingProcess;

public class EulerMethod {
    public static Boolean Solve(CoffeeCoolingProcess coffeeCoolingProcess, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            coffeeCoolingProcess.Clear();
            coffeeCoolingProcess.addPoint(x, y);

            for (int i = 0; i < countIter; i++) {
                y += step * coffeeCoolingProcess.computeFunction(x, y);
                x += step;
                coffeeCoolingProcess.addPoint(x, y);
            }
        }
        catch (Exception e) {
            System.out.println("EulerMethod fail");
            return false;
        }
        return true;
    }
}
