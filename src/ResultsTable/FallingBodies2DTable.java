package ResultsTable;

import Equation.ObjectFalling2DProcess;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FallingBodies2DTable {
    public static JTable GetTable(ArrayList<ObjectFalling2DProcess> d) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Number");
        columns.add("Time");

        for (int i = 0; i < d.size(); ++i) {
            columns.add(String.format("x_%d", i + 1));
            columns.add(String.format("y_%d", i + 1));
            columns.add(String.format("Vx_%d", i + 1));
            columns.add(String.format("Vy_%d", i + 1));
        }

        int rows = 0, maxRowIdx = 0;
        for (int i = 0; i < d.size(); ++i) {
            if (rows < d.get(i).getX().size()) {
                rows = d.get(i).getX().size();
                maxRowIdx = i;
            }
        }
        Object[][] data = new Object[rows][d.size() * 4 + 2];

        for (int i = 0; i < rows; ++i) {
            data[i][0] = i;
            data[i][1] = String.format("%.3f", d.get(maxRowIdx).getTime().get(i));

            for (int j = 0; j < d.size(); ++j) {
                if (i >= d.get(j).getX().size()) {
                    data[i][2 + 4 * j] = "";
                    data[i][3 + 4 * j] = "";
                    data[i][4 + 4 * j] = "";
                    data[i][5 + 4 * j] = "";
                }
                else {
                    data[i][2 + 4 * j] = String.format("%.3f", d.get(j).getX().get(i));
                    data[i][3 + 4 * j] = String.format("%.3f", d.get(j).getY().get(i));
                    data[i][4 + 4 * j] = String.format("%.3f", d.get(j).getNumericalVelocityX().get(i));
                    data[i][5 + 4 * j] = String.format("%.3f", d.get(j).getNumericalVelocityY().get(i));
                }
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
