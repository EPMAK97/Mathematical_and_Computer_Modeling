package GUI;

import Equation.Equation;
import Equation.ObjectFallingProcess;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Euler_KromerMethod;
import ResultsTable.ComparisonTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FallingBodiesController implements Initializable {
    public TextField heightBody;
    public TextField speedBody;
    public TextField startTime;
    public TextField finishTime;
    public TextField numberCounts;

    public TextField densityBody;
    public TextField densityEnvironment;
    public TextField radiusBody;

    public ComboBox<String> environment;
    public ComboBox<String> materialBody;

    public SplitMenuButton menuButton;

    public CheckBox F_A;
    //public CheckBox F_C1;
    //public CheckBox F_C2;
    public CheckBox checkBoxConstGravity;
    public javafx.scene.control.TableView table;

    public RadioButton F_C1;
    public RadioButton F_C2;
    public RadioButton notF;

    public AnchorPane paneTable;
    public AnchorPane paneMainMenu;

    public VBox vboxTable;
    public TableView summaryTable;

    private static HashMap<String, Double> materialDensity;
    private static HashMap<String, Double> environmentViscosity;

    private ObservableList<ObjectFallingProcess> data = FXCollections.observableArrayList();

    private enum PlotType {PT_COORDINATE, PT_VELOCITY, PT_ACCELERATION};
    private static HashMap<PlotType, String> plotTypePlotName;

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

        // dynamic viscosity of different environments
        environmentViscosity = new HashMap<String, Double>() {{
            put("Вакуум",   0.0);
            put("Воздух",   18.27);
            put("Водород",  8.76);
            put("Вода",     1002.0);
            put("Гелий",    19.0);
            put("Глицерин", 1480.0 * 1000.0);
            put("Оливковое масло", 84.0 * 1000.0);
            put("Поливинилхлорид", 2.0 * 1e6);
        }};

        // convert value to SI
        for (Map.Entry<String, Double> entry: environmentViscosity.entrySet()) {
            entry.setValue(entry.getValue() * 1e-4);
        }

        plotTypePlotName = new HashMap<PlotType, String>() {{
            put(PlotType.PT_COORDINATE, "Координаты");
            put(PlotType.PT_VELOCITY, "Скорость");
            put(PlotType.PT_ACCELERATION, "Ускорение");
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

        heightBody.setText("0");
        speedBody.setText("0");
        startTime.setText("0");
        finishTime.setText("20");
        numberCounts.setText("1000");

        environment.setValue("Вода");
        setEnvironment();

        materialBody.setValue("Камень");
        setMaterialBody();

        radiusBody.setText("1.0");

        // Здесь добавить рисовку Y и скорости и еще чего нибудь, вот так
        ObservableList<MenuItem> items = menuButton.getItems();

        items.get(0).setOnAction(f -> {drawPlot(PlotType.PT_COORDINATE);});
        items.get(1).setOnAction(f -> {drawPlot(PlotType.PT_VELOCITY);});
        items.get(2).setOnAction(f -> {drawPlot(PlotType.PT_ACCELERATION);});
        items.get(3).setOnAction(f -> {createSummaryTable();});
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
            equation.setConstGravity("учтено");
            constGravity = 0;
            variableGravity = 1;
        }

        Integer buoyantForce = 0;
        if (F_A.isSelected()) {
            equation.setF_A("учтено");
            buoyantForce = 1;
            equation.setBuoyantCoeff(Double.parseDouble(densityBody.getText()), Double.parseDouble(densityEnvironment.getText()));
        }

        Integer linearAcc = 0, squareAcc = 0;
        if (F_C1.isSelected()) {
            equation.setF_C1("учтено");
            linearAcc = 1;
            Double vz = environmentViscosity.get(environment.getValue());
            Double p_env = Double.parseDouble(densityEnvironment.getText());
            Double p_body = Double.parseDouble(densityBody.getText());
            Double R = Double.parseDouble(radiusBody.getText());
            equation.setLinearResistanceCoeff(vz, p_env, p_body, R);
        }
        if (F_C2.isSelected()) {
            equation.setF_C2("учтено");
            squareAcc = 1;
            Double vz = environmentViscosity.get(environment.getValue());
            Double p_env = Double.parseDouble(densityEnvironment.getText());
            Double p_body = Double.parseDouble(densityBody.getText());
            Double R = Double.parseDouble(radiusBody.getText());
            equation.setSquareResistanceCoeff(p_env, p_body, R);
        }

        equation.setCoefficients(constGravity, variableGravity, buoyantForce, linearAcc, squareAcc);
    }

    private void setParameters(ObjectFallingProcess equation) {
        equation.setY0(Double.parseDouble(heightBody.getText()));
        equation.setV0(Double.parseDouble(speedBody.getText()));
        equation.setXStart(Double.parseDouble(startTime.getText()));
        equation.setXFinish(Double.parseDouble(finishTime.getText()));
        equation.setN(Integer.parseInt(numberCounts.getText()));

        equation.setEnvironment(environment.getValue());
        equation.setMaterialBody(materialBody.getValue());
        equation.setRadius(Double.parseDouble(radiusBody.getText()));
    }

    public void drawPlot(PlotType pt) {

        if (data.isEmpty()) return;

        ArrayList<ArrayList<Double>> solutions = new ArrayList();
        ArrayList<String> names = new ArrayList();

        for (int i = 0; i < data.size(); ++i) {
            Euler_KromerMethod.Solve(data.get(i), data.get(i).getN(),
                    data.get(i).getXStart(), data.get(i).getXFinish(), data.get(i).getY0());

            names.add(String.format("Model № %d", data.get(i).getNumber()));
            if (pt == PlotType.PT_COORDINATE) {
                solutions.add(data.get(i).getY());
            }
            else if (pt == PlotType.PT_VELOCITY) {
                solutions.add(data.get(i).getNumericalVelocity());
            }
            else if (pt == PlotType.PT_ACCELERATION) {
                solutions.add(data.get(i).getNumericalAcceleration());
            }
        }

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chartSolutions = chartMatlab.getChart(data.get(0).getX(), solutions, names);
        chartSolutions.setTitle(plotTypePlotName.get(pt));
        new SwingWrapper(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    public void addData() {
        // Считать все и внести в таблицу
        ObjectFallingProcess equation = new ObjectFallingProcess();
        setCoefficients(equation);
        setParameters(equation);

        ObservableList<javafx.scene.control.TableColumn> columns = table.getColumns();

        columns.get(0).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("number"));
        columns.get(1).setCellValueFactory(
                new PropertyValueFactory<Equation, Double>("y0"));
        columns.get(2).setCellValueFactory(
                new PropertyValueFactory<Equation, Double>("v0"));
        columns.get(3).setCellValueFactory(
                new PropertyValueFactory<Equation, Double>("radius"));
        columns.get(4).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("environment"));
        columns.get(5).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("materialBody"));

        ObservableList<javafx.scene.control.TableColumn> forceColumns = columns.get(6).getColumns();

        forceColumns.get(0).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("constGravity"));
        forceColumns.get(1).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("F_A"));
        forceColumns.get(2).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("F_C1"));

        data.add(equation);
        table.setItems(data);
    }

    public void clickRadioButtonF_C1() {
        F_C1.setSelected(true);
        F_C2.setSelected(false);
        notF.setSelected(false);
    }

    public void clickRadioButtonF_C2() {
        F_C2.setSelected(true);
        F_C1.setSelected(false);
        notF.setSelected(false);
    }

    public void clickRadioButtonNotF() {
        notF.setSelected(true);
        F_C1.setSelected(false);
        F_C2.setSelected(false);
    }

    public void deleteFromTable() {
        Integer idx = table.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            data.remove(idx, idx + 1);
            table.setItems(data);
        }
    }

    public void createSummaryTable() {
        if (data.isEmpty()) return;

        for (int i = 0; i < data.size(); ++i) {
            Euler_KromerMethod.Solve(data.get(i), data.get(i).getN(),
                    data.get(i).getXStart(), data.get(i).getXFinish(), data.get(i).getY0());

        }

        JTable table = ResultsTable.ComparisonTable.GetTable(new ArrayList<ObjectFallingProcess>(data));
        JFrame frame = new JFrame("Table");
        frame.add(new JScrollPane(table));
        frame.setSize(table.getColumnModel().getTotalColumnWidth() + 20, 500);
        frame.setVisible(true);
    }

    public void backToMainMenuClickButton() {
        paneMainMenu.setVisible(true);
        vboxTable.setVisible(false);
    }
}
