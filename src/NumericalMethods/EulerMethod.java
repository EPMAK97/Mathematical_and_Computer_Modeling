package NumericalMethods;


import Equation.Equation;

public class EulerMethod {
    public static Boolean Solve(Equation equation, double step, int countIter, double x0, double y0) {
        try {
            double x = x0, y = y0;
            for (int i = 0; i < countIter; i++) {
                equation.SetPoint(x, y);
                y += step * equation.MakeFunction(x, y);
                x += step;
            }
        }
        catch (Exception e) {
            System.out.println("EulerMethod fail");
            return false;
        }
        return true;
    }
}
