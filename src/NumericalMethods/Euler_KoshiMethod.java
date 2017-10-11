package NumericalMethods;


import Equation.Equation;

public class Euler_KoshiMethod {
    public static Boolean Solve(Equation equation, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            equation.setStep(step);
            equation.Clear();
            equation.addPoint(x, y);

            for (int i = 0; i < countIter; i++) {
                y0 = y + step * equation.computeFunction(x, y);
                y += (equation.computeFunction(x, y) + equation.computeFunction(x + step, y0)) * step / 2;
                x += step;
                equation.addPoint(x, y);
            }
        }
        catch (Exception e) {
            System.out.println("Euler-KoshiMethod fail");
            return false;
        }
        return true;
    }
}
