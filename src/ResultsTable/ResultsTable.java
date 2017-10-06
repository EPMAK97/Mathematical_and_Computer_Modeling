package ResultsTable;

import Equation.Equation;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;

import javax.swing.*;

public class ResultsTable {
    public static JTable GetTable(Equation equation, int countIter, double x0, double x1, double y0) {
        String[] columns = new String[] {
                "k", "time", "T_analytical", "T_Euler_1", "error_Euler_1", "T_Euler_2", "error_Euler_2", "T_Runge-Kutte", "error_Runge-Kutte"
        };

        EulerMethod.Solve(equation, countIter, x0, x1, y0);
        equation.getAbsoluteErrors();
        String formatSol = "%.8f";
        String formatErr = "%.8e";

        int sz = equation.getX().size();
        Object[][] data = new Object[sz][columns.length];
        for (int i = 0; i < sz; ++i) {
            data[i][0] = i;
            data[i][1] = String.format("%.3f", x0 + i * equation.getStep());
            data[i][2] = String.format(formatSol, equation.getAnalyticalSolution().get(i));
            data[i][3] = String.format(formatSol, equation.getY().get(i));
            data[i][4] = String.format(formatErr, equation.getAbsoluteErrors().get(i));
        }

        EulerMethodImproved.Solve(equation, countIter, x0, x1, y0);
        for (int i = 0; i < sz; ++i) {
            data[i][5] = String.format(formatSol, equation.getY().get(i));
            data[i][6] = String.format(formatErr, equation.getAbsoluteErrors().get(i));
        }

        Runge_KuttaMethod.Solve(equation, countIter, x0, x1, y0);
        for (int i = 0; i < sz; ++i) {
            data[i][7] = String.format(formatSol, equation.getY().get(i));
            data[i][8] = String.format(formatErr, equation.getAbsoluteErrors().get(i));
        }

        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 2; i < columns.length; ++i) {
            table.getColumnModel().getColumn(i).setWidth(120);
            table.getColumnModel().getColumn(i).setMinWidth(120);
        }
        return table;
    }
}
