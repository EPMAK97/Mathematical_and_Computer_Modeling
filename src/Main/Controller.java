package Main;

import Equation.Equation;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;
import ResultsTable.ResultsTable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.util.ArrayList;

public class Controller {
    public Equation equation = new Equation();

    public Button setData;
    public Button getSolveGraph;
    public Button getErrorsGraph;
    public Button getTable;

    public TextField textT0;
    public TextField textR;
    public TextField textTc;
    public TextField textX0;
    public TextField textX;
    public TextField textCountIteration;

    public CheckBox checkEuler;
    public CheckBox checkEulerImproved;
    public CheckBox checkRungeKutta;
    public CheckBox checkAnalytical;

    public boolean setData() {
        try {
            equation.setT0(Double.parseDouble(textT0.getText()));
            equation.setTc(Double.parseDouble(textTc.getText()));
            equation.setR(Double.parseDouble(textR.getText()));
            equation.setXStart(Double.parseDouble(textX0.getText()));
            equation.setXFinish(Double.parseDouble(textX.getText()));
            equation.setN(Integer.parseInt(textCountIteration.getText()));
            equation.setStep((equation.getXFinish() - equation.getXStart()) / equation.getN());

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Введены некорректные данные");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void getSolveGraphButtonClick() {
        // ALERT COPY-PASTE (DIS IS BAD(actually code is bad, we need one loop, but we've no time))
        if (!setData()) return;
        ArrayList<ArrayList<Double>> solutions = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (checkEuler.isSelected()) {
            EulerMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            solutions.add(equation.getY());
            names.add("Euler method");
        }
        if (checkEulerImproved.isSelected()) {
            EulerMethodImproved.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            solutions.add(equation.getY());
            names.add("Euler improved method");
        }
        if (checkRungeKutta.isSelected()) {
            Runge_KuttaMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            solutions.add(equation.getY());
            names.add("Runge-Kutte method");
        }
        if (checkAnalytical.isSelected()) {
            ArrayList<Double> analiticalSolution = equation.getAnalyticalSolution();
            solutions.add(analiticalSolution);
            names.add("Analytical solution");
        }

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chart1 = chartMatlab.getChart(equation.getX(), solutions, names);
        chart1.setTitle("Solutions");
        new SwingWrapper<>(chart1).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void getAbsErrorsButtonClick() {
        if (!setData()) return;
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (checkEuler.isSelected()) {
            EulerMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getAbsoluteErrors());
            names.add("Euler absolute error");
        }
        if (checkEulerImproved.isSelected()) {
            EulerMethodImproved.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getAbsoluteErrors());
            names.add("Euler improved absolute error");
        }
        if (checkRungeKutta.isSelected()) {
            Runge_KuttaMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getAbsoluteErrors());
            names.add("Runge-Kutte absolute error");
        }

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chart1 = chartMatlab.getChart(equation.getX(), errors, names);
        chart1.setTitle("Absolute errors");
        new SwingWrapper<>(chart1).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void getRelErrorsButtonClick() {
        if (!setData()) return;
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (checkEuler.isSelected()) {
            EulerMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            names.add("Euler relative error");
        }
        if (checkEulerImproved.isSelected()) {
            EulerMethodImproved.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            names.add("Euler improved relative error");
        }
        if (checkRungeKutta.isSelected()) {
            Runge_KuttaMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            names.add("Runge-Kutte relative error");
        }

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chart1 = chartMatlab.getChart(equation.getX(), errors, names);
        chart1.setTitle("Relative errors");
        new SwingWrapper<>(chart1).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void getTableButtonClick() {
        if (!setData()) return;
        JTable table = ResultsTable.GetTable(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
        JFrame frame = new JFrame("Table");
        frame.add(new JScrollPane(table));
        frame.setSize(table.getColumnModel().getTotalColumnWidth() + 20, 500);
        frame.setVisible(true);
    }
}
