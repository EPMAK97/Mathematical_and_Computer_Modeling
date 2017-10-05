package ResultsTable;

import Equation.Equation;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;

import javax.swing.*;

public class ResultsTable {
    public static JTable GetTable(Equation equation, int countIter, double x0, double x1, double y0) {
        String[] columns = new String[] {
                "k", "t", "T_a", "T_E1", "error_E1", "T_E2", "error_E2", "T_RK", "error_RK"
        };

        EulerMethod.Solve(equation, countIter, x0, x1, y0);
        equation.getAbsoluteErrors();

        int sz = equation.getX().size();
        Object[][] data = new Object[sz][columns.length];
        for (int i = 0; i < sz; ++i) {
            data[i][0] = i;
            data[i][1] = x0 + i * equation.getStep();
            data[i][2] = equation.getAnalyticalSolution().get(i);
            data[i][3] = equation.getY().get(i);
            data[i][4] = equation.getAbsoluteErrors().get(i);
        }

        EulerMethodImproved.Solve(equation, countIter, x0, x1, y0);
        for (int i = 0; i < sz; ++i) {
            data[i][5] = equation.getY().get(i);
            data[i][6] = equation.getAbsoluteErrors().get(i);
        }

        Runge_KuttaMethod.Solve(equation, countIter, x0, x1, y0);
        for (int i = 0; i < sz; ++i) {
            data[i][7] = equation.getY().get(i);
            data[i][8] = equation.getAbsoluteErrors().get(i);
        }

        return new JTable(data, columns);
    }
}
