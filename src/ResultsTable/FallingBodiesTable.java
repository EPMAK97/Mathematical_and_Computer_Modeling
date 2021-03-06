package ResultsTable;

import Equation.CoffeeCoolingProcess;
import Equation.ObjectFallingProcess;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FallingBodiesTable {
    public static JTable GetTable(ArrayList<ObjectFallingProcess> d) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Number");
        columns.add("Time");

        for (int i = 0; i < d.size(); ++i) {
            columns.add(String.format("y_%d", i + 1));
            columns.add(String.format("v_%d", i + 1));
            columns.add(String.format("a_%d", i + 1));
        }

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

        try {

            TableModel model = table.getModel();
            FileWriter csv = new FileWriter(new File("table.csv"));

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    csv.write(model.getValueAt(i, j).toString() + ",");
                }
                csv.write("\n");
            }

            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }
}
