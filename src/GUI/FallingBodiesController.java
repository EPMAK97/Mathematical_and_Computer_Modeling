package GUI;

import Equation.ObjectFallingProcess;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Euler_KromerMethod;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FallingBodiesController implements Initializable {
    public TextField heightBody;
    public TextField speedBody;
    public TextField startTime;
    public TextField finishTime;
    public TextField numberCounts;

    public TextField densityBody;
    public TextField densityEnvironment;
    public TextField massBody;
    public TextField crossSectionBody;
    public TextField radiusBody;

    public ComboBox<String> environment;
    public ComboBox<String> materialBody;

    public Button compute;

    public CheckBox F_A;
    public CheckBox F_C1;
    public CheckBox F_C2;
    public CheckBox checkBoxConstGravity;

    private static HashMap<String, Double> materialDensity;
    private static HashMap<String, Double> environmentViscosity;

    static {
        materialDensity = new HashMap<String, Double>() {{
           put("Воздух",    1.2);
           put("Водород",   0.09);
           put("Гелий",     0.18);
           put("Глицерин",  1260.0);
           put("Оливковое масло", 920.0);
           put("Поливинилхлорид", 1400.0);
           put("Вода",      1000.0);

           put("Дерево", 500.0);
           put("Стекло", 1200.0);
           put("Резина", 900.0);
           put("Камень",  2200.0);
           put("Сталь",  7800.0);
           put("Алюминий", 2700.0);
           put("Свинец",   11300.0);
           put("Серебро",  10600.0);
           put("Золото",   19300.0);
           put("Вакуум", 0.0);
           put("Материальная точка", 0.0);
        }};

        environmentViscosity = new HashMap<String, Double>() {{
            put("Вакуум",   0.0);
            put("Воздух",   1812.0);
            put("Водород",  880.0);
            put("Вода",     1.0);
            put("Гелий",    1946.0);
            put("Глицерин", 1480.0);
            put("Оливковое масло", 84.0);
            put("Поливинилхлорид", 2.0);
        }};
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        environment.getItems().setAll(
                "Вакуум",
                "Воздух",
                "Водород",
                "Вода",
                "Гелий",
                "Глицерин",
                "Оливковое масло",
                "Поливинилхлорид");

        materialBody.getItems().setAll(
                "Материальная точка",
                "Дерево",
                "Стекло",
                "Резина",
                "Камень",
                "Сталь",
                "Алюминий",
                "Свинец",
                "Серебро",
                "Золото");

        heightBody.setText("100");
        speedBody.setText("30");
        startTime.setText("0");
        finishTime.setText("10");
        numberCounts.setText("20");

        environment.setValue("Воздух");
        setEnvironment();

        materialBody.setValue("Камень");
        setMaterialBody();

        massBody.setText("1.0");
        radiusBody.setText("1.0");
    }

    public void setDensityEnvironment(String s) {
        densityEnvironment.setText(s);
    }

    public void setDensityBody(String s) {
        densityBody.setText(s);
    }

    public void setMaterialBody() {
        if (materialBody.getValue() != null) {
            if (materialDensity.containsKey(materialBody.getValue()))
                setDensityBody(String.valueOf(materialDensity.get(materialBody.getValue())));
        }
    }

    public void setEnvironment() {
        if (environment.getValue() != null) {
            if (materialDensity.containsKey(environment.getValue()))
                setDensityEnvironment(String.valueOf(materialDensity.get(environment.getValue())));
        }
    }

    public void checkBoxSelectedF_A() {
        // Сила Архимеда

    }

    public void checkBoxSelectedF_C1() {
        // Сила с линейной зависимостью
    }

    public void checkBoxSelectedF_C2() {
        // Сила с квадратичной зависимостью
    }

    private void setCoefficients(ObjectFallingProcess equation) {
        Integer constGravity = 1;
        Integer variableGravity = 0;
        if (checkBoxConstGravity.isSelected()) {
            constGravity = 0;
            variableGravity = 1;
        }

        Integer buoyantForce = 0;
        if (F_A.isSelected()) {
            buoyantForce = 1;
            equation.setBuoyantCoeff(1.0 - Double.parseDouble(densityBody.getText()) / Double.parseDouble(densityEnvironment.getText()));
        }

        Integer linearAcc = 0, squareAcc = 0;
        if (F_C1.isSelected()) {
            linearAcc = 1;
            Double vz = 1.0;
            Double p = Double.parseDouble(densityEnvironment.getText());
            Double R = Double.parseDouble(radiusBody.getText());
            Double k_1 = 6.0 * Math.PI * vz * p * R;
            equation.setLinearResistanceCoeff(k_1);
        }
        if (F_C2.isSelected()) {
            squareAcc = 1;
            Double p = Double.parseDouble(densityEnvironment.getText());
            Double R = Double.parseDouble(radiusBody.getText());
            Double k_2 = p * Math.PI * Math.pow(R, 2.0);
        }

        equation.setCoefficients(constGravity, variableGravity, buoyantForce, linearAcc, squareAcc);
    }

    private void setParameters(ObjectFallingProcess equation) {
        equation.setY0(Double.parseDouble(heightBody.getText()));
        equation.setV0(Double.parseDouble(speedBody.getText()));
        equation.setXStart(Double.parseDouble(startTime.getText()));
        equation.setXFinish(Double.parseDouble(finishTime.getText()));
        equation.setN(Integer.parseInt(numberCounts.getText()));

        equation.setMass(Double.parseDouble(massBody.getText()));
        equation.setRadius(Double.parseDouble(radiusBody.getText()));
    }

    public void computeFromParameters() {
        // Считать все и посчитать
        ObjectFallingProcess equation = new ObjectFallingProcess();
        setCoefficients(equation);
        setParameters(equation);

        ArrayList<ArrayList<Double>> solutions = new ArrayList();
        ArrayList<String> names = new ArrayList();

        Euler_KromerMethod.Solve(equation, equation.getN(), equation.getXStart(), equation.getXFinish(), equation.getY0());
        solutions.add(equation.getY());
        names.add("Test");

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chartSolutions = chartMatlab.getChart(equation.getX(), solutions, names);
        chartSolutions.setTitle("Test");
        new SwingWrapper(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    public void clickF_A() {
        densityBody.setDisable(!F_A.isSelected());
        densityEnvironment.setDisable(!F_A.isSelected());
        densityBody.setEditable(F_A.isSelected());
        densityEnvironment.setEditable(F_A.isSelected());
    }

    public void selectableF_C() {
        boolean selectable = F_C1.isSelected() || F_C2.isSelected();

        massBody.setDisable(!selectable);
        massBody.setEditable(selectable);

        radiusBody.setDisable(!selectable);
        radiusBody.setEditable(selectable);

        massBody.setDisable(!selectable);
        massBody.setEditable(selectable);

        radiusBody.setDisable(!selectable);
        radiusBody.setEditable(selectable);
    }

    public void clickF_C1() {
        selectableF_C();
    }

    public void clickF_C2() {
        selectableF_C();
    }
}
