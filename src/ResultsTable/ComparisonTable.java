package ResultsTable;

import Equation.CoffeeCoolingProcess;
import Equation.ObjectFallingProcess;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;

import javax.swing.*;
import java.util.ArrayList;

public class ComparisonTable {
    public static JTable GetTable(ArrayList<ObjectFallingProcess> d) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Номер");
        columns.add("Время");

        for (int i = 0; i < d.size(); ++i) {
            columns.add(String.format("y_%d", i + 1));
            columns.add(String.format("v_%d", i + 1));
            columns.add(String.format("a_%d", i + 1));
        }

        String formatSol = "%.8f";

        int rows = d.get(0).getX().size();
        Object[][] data = new Object[rows][d.size() * 3 + 2];

        for (int i = 0; i < rows; ++i) {
            data[i][0] = i;
            data[i][1] = String.format("%.3f", d.get(0).getX().get(i));

            for (int j = 0; j < d.size(); ++j) {
                data[i][2 + 3 * j] = String.format("%.3f", d.get(j).getY().get(i));
                data[i][3 + 3 * j] = String.format("%.3f", d.get(j).getNumericalVelocity().get(i));
                data[i][4 + 3 * j] = String.format("%.3f", d.get(j).getNumericalAcceleration().get(i));
            }
        }

        String[] tmp = new String[columns.size()];
        tmp = columns.toArray(tmp);

        JTable table = new JTable(data, tmp);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return table;
    }
}
