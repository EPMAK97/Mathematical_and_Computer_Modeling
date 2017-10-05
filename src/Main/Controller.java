package Main;

import Equation.Equation;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Runge_KuttaMethod;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

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

    public void setDataButtonClick() {
        try {
            equation.setT0(Double.parseDouble(textT0.getText()));
            equation.setTc(Double.parseDouble(textTc.getText()));
            equation.setR(Double.parseDouble(textR.getText()));
            equation.setXStart(Double.parseDouble(textX0.getText()));
            equation.setXFinish(Double.parseDouble(textX.getText()));
            equation.setN(Integer.parseInt(textCountIteration.getText()));

        } catch (NumberFormatException e) {

            // Надо добавить окно которое выводит сообщения об ошибках на форму1!!!!!

            System.out.println("WRONG DATA");
        }
        System.out.println(equation.getR());
    }

    public void getSolveGraphButtonClick() {
    }

    public void getErrorsButtonClick() {
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if (checkEuler.isSelected()) {
            EulerMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            errors.add(equation.getAbsoluteErrors());
            names.add("Euler relative error");
            names.add("Euler absolute error");
        }
        if (checkEulerImproved.isSelected()) {
            EulerMethodImproved.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            errors.add(equation.getAbsoluteErrors());
            names.add("Euler improved relative error");
            names.add("Euler improved absolute error");
        }
        if (checkRungeKutta.isSelected()) {
            Runge_KuttaMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getT0());
            errors.add(equation.getRelativeErrors());
            errors.add(equation.getAbsoluteErrors());
            names.add("Runge-Kutte relative error");
            names.add("Runge-Kutte absolute error");
        }
        if (checkAnalytical.isSelected()) {
            ArrayList<Double> analiticalSolution = equation.getAnaliticalSolution();
            errors.add(analiticalSolution);
            names.add("Analitical solution");
        }


        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chart1 = chartMatlab.getChart(equation.getX(), errors, names);
        new SwingWrapper<>(chart1).displayChart();
    }

    public void getTableButtonClick() {

    }
}
