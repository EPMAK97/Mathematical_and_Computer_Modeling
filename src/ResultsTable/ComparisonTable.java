package ResultsTable;

import Equation.CoffeeCoolingProcess;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;

import javax.swing.*;

public class ComparisonTable {
    public static JTable GetTable(CoffeeCoolingProcess coffeeCoolingProcess, int countIter, double x0, double x1, double y0) {
        String[] columns = new String[] {
                "solutions", "BuoyantCoeff", "LinearResistanceCoeff", "T_Euler_1", "error_Euler_1", "T_Euler_2", "error_Euler_2", "T_Runge-Kutte", "error_Runge-Kutte"
        };

        EulerMethod.Solve(coffeeCoolingProcess, countIter, x0, x1, y0);
        coffeeCoolingProcess.getAbsoluteErrors();
        String formatSol = "%.8f";
        String formatErr = "%.8e";

        int sz = coffeeCoolingProcess.getX().size();
        Object[][] data = new Object[sz + 2][columns.length];

        Double sum = 0.0;
        for (int i = 0; i < sz; ++i) {
            data[i][0] = i;
            data[i][1] = String.format("%.3f", x0 + i * coffeeCoolingProcess.getStep());
            data[i][2] = String.format(formatSol, coffeeCoolingProcess.getAnalyticalSolution().get(i));
            data[i][3] = String.format(formatSol, coffeeCoolingProcess.getY().get(i));
            data[i][4] = String.format(formatErr, coffeeCoolingProcess.getAbsoluteErrors().get(i));
            sum += coffeeCoolingProcess.getAbsoluteErrors().get(i);
        }
        data[sz + 1][4] = String.format(formatErr, sum / sz);

        EulerMethodImproved.Solve(coffeeCoolingProcess, countIter, x0, x1, y0);
        sum = 0.0;
        for (int i = 0; i < sz; ++i) {
            data[i][5] = String.format(formatSol, coffeeCoolingProcess.getY().get(i));
            data[i][6] = String.format(formatErr, coffeeCoolingProcess.getAbsoluteErrors().get(i));
            sum += coffeeCoolingProcess.getAbsoluteErrors().get(i);
        }
        data[sz + 1][6] = String.format(formatErr, sum / sz);

        Runge_KuttaMethod.Solve(coffeeCoolingProcess, countIter, x0, x1, y0);
        sum = 0.0;
        for (int i = 0; i < sz; ++i) {
            data[i][7] = String.format(formatSol, coffeeCoolingProcess.getY().get(i));
            data[i][8] = String.format(formatErr, coffeeCoolingProcess.getAbsoluteErrors().get(i));
            sum += coffeeCoolingProcess.getAbsoluteErrors().get(i);
        }
        data[sz + 1][8] = String.format(formatErr, sum / sz);

        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 2; i < columns.length; ++i) {
            table.getColumnModel().getColumn(i).setWidth(120);
            table.getColumnModel().getColumn(i).setMinWidth(120);
        }
        return table;
    }
}
