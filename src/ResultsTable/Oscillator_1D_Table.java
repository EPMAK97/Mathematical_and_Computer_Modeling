package ResultsTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Oscillator_1D_Table {
    public static ArrayList<JTable> GetTable(ArrayList<ArrayList<Double>> d, ArrayList<ArrayList<Double>> info) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("Number");
        columns.add("Time");

        for (int i = 0; i < d.size(); i++) {
            columns.add(String.format("x_%d", i + 1).replaceAll(",", "."));
            columns.add(String.format("v_%d", i + 1).replaceAll(",", "."));
            columns.add(String.format("E_%d", i + 1).replaceAll(",", "."));
        }

        int rows = d.get(0).size();
        Object[][] data = new Object[rows / 4][d.size() * 3 + 2];
        for (int i = 0, index = 0; i < rows; i += 4, index++) {
            data[index][0] = index;
            data[index][1] = String.format("%.3f", d.get(0).get(i)).replaceAll(",", ".");

            for (int j = 0; j < d.size(); j++) {
                data[index][2 + 3 * j] = String.format("%.3f", d.get(j).get(i == 0 ? 1 : i + 1)).replaceAll(",", ".");
                data[index][3 + 3 * j] = String.format("%.3f", d.get(j).get(i == 0 ? 2 : i + 2)).replaceAll(",", ".");
                data[index][4 + 3 * j] = String.format("%.3f", d.get(j).get(i == 0 ? 3 : i + 3)).replaceAll(",", ".");
            }
        }

        // models info
        ArrayList<String> infoColumns = new ArrayList<>();
        infoColumns.add("Number");
        infoColumns.add("X0");
        infoColumns.add("V0");
        infoColumns.add("M");
        infoColumns.add("K");
        infoColumns.add("Gamma");

        int infoRows = info.size();
        Object[][] infoData = new Object[infoRows][6];

        for (int i = 0, index = 0; i < infoRows; i++, index++) {
            infoData[index][0] = index + 1;
            infoData[index][1] = String.format("%.3f", d.get(i).get(0)).replaceAll(",", ".");
            infoData[index][2] = String.format("%.3f", d.get(i).get(1)).replaceAll(",", ".");
            infoData[index][3] = String.format("%.3f", d.get(i).get(2)).replaceAll(",", ".");
            infoData[index][4] = String.format("%.3f", d.get(i).get(3)).replaceAll(",", ".");
            infoData[index][5] = String.format("%.3f", d.get(i).get(4)).replaceAll(",", ".");

        }
        System.out.println(String.format("%.3f", d.get(0).get(4)));
        ArrayList<JTable> tables = new ArrayList<>();
        tables.add(exportToCSV(infoColumns, infoData, "results", false));
        tables.add(exportToCSV(columns, data, "results", true));

        return tables;
    }

    private static JTable exportToCSV(ArrayList<String> columns, Object[][] data, String name, boolean append) {
        String[] tmp = new String[columns.size()];
        tmp = columns.toArray(tmp);

        JTable table = new JTable(data, tmp);

        try {

            TableModel model = table.getModel();
            FileWriter csv = new FileWriter(new File(name + ".csv"), append);

            if (append) {
                csv.write("\n");
            }

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
