package NumericalMethods;


import Equation.Equation;

public class Euler_KoshiMethod {
    public static Boolean Solve(Equation equation, int countIter, double x0, double x1, double y0) {
        try {
            double x = x0, y = y0;
            double step = (x1 - x) / countIter;
            equation.setStep(step);

            for (int i = 0; i < countIter; i++) {
                equation.SetPoint(x, y);
                y0 = y + step * equation.MakeFunction(x, y);
                y += (equation.MakeFunction(x, y) + equation.MakeFunction(x + step, y0)) * step / 2;
                x -= step;
            }
        }
        catch (Exception e) {
            System.out.println("Euler-KoshiMethod fail");
            return false;
        }
        return true;
    }
}
