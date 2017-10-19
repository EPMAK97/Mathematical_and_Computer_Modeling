package NumericalMethods;


import Equation.CoffeeCoolingProcess;

public class Euler_KoshiMethod {
    public static Boolean Solve(CoffeeCoolingProcess coffeeCoolingProcess, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            coffeeCoolingProcess.Clear();
            coffeeCoolingProcess.addPoint(x, y);

            for (int i = 0; i < countIter; i++) {
                y0 = y + step * coffeeCoolingProcess.computeFunction(x, y);
                y += (coffeeCoolingProcess.computeFunction(x, y) + coffeeCoolingProcess.computeFunction(x + step, y0)) * step / 2;
                x += step;
                coffeeCoolingProcess.addPoint(x, y);
            }
        }
        catch (Exception e) {
            System.out.println("Euler-KoshiMethod fail");
            return false;
        }
        return true;
    }
}
