package NumericalMethods;

import Equation.Equation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Adam_BashforthMethod {
    public interface TwoParameterFunction<A, B, C> {
        public C apply(A a, B b);
    }

    private static Double computeBrackets(TwoParameterFunction<Double, Double, Double> f,
                                          Double[] x, Double[] y, Integer cnt, Double[] coefficients)
    {
        Double res = 0.0;
        for (int i = cnt - 1; i >= 0; --i) {
            res += coefficients[cnt - 1 - i] * f.apply(x[i], y[i]);
        }

        return res;
    }

    public static Boolean Solve(Equation equation, int order, int countIter, double x0, double x1, double y0) {
        try {
            if (order == 1) {
                return EulerMethod.Solve(equation, countIter, x0, x1, y0);
            }

            if (countIter < order) {
                throw new Exception("Iterations count can't be less then order");
            }

            if (order < 1 || order > 4) {
                throw new Exception("Order must be from 1 to 4");
            }

            Double[] x = new Double[order + 1];
            Double[] y = new Double[order + 1];
            Double step = (x1 - x0) / countIter;
            equation.setStep(step);
            equation.Clear();
            equation.addPoint(x0, y0);

            // compute start points
            Runge_KuttaMethod.Solve(equation, order - 1, x0, x0 + (order - 1) * step, y0);
            TwoParameterFunction <Double, Double, Double> f = (_x, _y)-> equation.computeFunction(_x, _y);

            for (int i = 0; i < order; ++i) {
                x[i] = equation.getX().get(i);
                y[i] = equation.getY().get(i);
            }
            for (int i = 0; i + order - 1 < countIter; i++) {
                x[order] = x0 + step * (i + order);
                Double insideBrackets = 0.0;
                if (order == 2) {
                    insideBrackets = computeBrackets(f, x, y, 2, new Double[] {3.0 / 2.0, -1.0 / 2.0});
                }
                else if (order == 3) {
                    insideBrackets = computeBrackets(f, x, y, 3, new Double[] {23.0 / 12.0, -4.0 / 3.0, 5.0 / 12.0});
                }
                else {
                    insideBrackets = computeBrackets(f, x, y, 4, new Double[] {55.0 / 24.0, -59.0 / 24.0, 37.0 / 24.0, - 3.0 / 8.0});
                }
                y[order] = y[order-1] + step * insideBrackets;
                equation.addPoint(x[order], y[order]);

                for (int j = 0; j < order; ++j) {
                    x[j] = x[j + 1];
                    y[j] = y[j + 1];
                }
            }
        } catch (Exception e) {
            System.out.println("Adam_BashforthMethod fail: " + e.toString());
            return false;
        }
        return true;
    }
}
