package GUI;

import Equation.Equation;
import Equation.ObjectFallingProcess;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Euler_KromerMethod;
import ResultsTable.FallingBodiesTable;
import Models.Oscillator_1D;

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
import java.util.*;

public class Oscillator_1DController implements Initializable {
    // model parameters
    public TextField editStartX;
    public TextField editStartVelocity;
    public TextField editBodyMass;
    public TextField editRestitutionCoeff;
    public TextField editFrictionCoeff;

    // time parameters
    public TextField editStartTime;
    public TextField editFinishTime;
    public TextField editNumberOfCounts;

    // animation parameters
    public TextField editImpulse;
    public TextField editPeriodicAmplitute;
    public TextField editPeriodicPeriod;
    public Button btnToogleAnimation;
    public Button btnImpulse;
    public Button btnPeriodic;

    // put models into table
    public Button btnAddModel;
    public Button btnDeleteModel;
    public SplitMenuButton btnDrawModels;
    public javafx.scene.control.TableView tableModels;
    private ObservableList<Oscillator_1D> modelsData = FXCollections.observableArrayList();

    public AnchorPane paneMainMenu;
    public AnchorPane paneAnimation;

    // animation plots
    public TabPane tabPaneAnimationPlots;
    public Tab tabAnimationCoordinates;
    public Tab tabAnimationVelocity;
    public Tab tabAnimationEnergy;
    public Tab tabAnimationPhasePortret;

    private enum PlotType {PT_COORDINATE, PT_VELOCITY, PT_ENERGY, PT_PHASE_PORTRET};
    private static HashMap<PlotType, String> plotTypePlotName;

    static {
        plotTypePlotName = new HashMap<PlotType, String>() {{
            put(PlotType.PT_COORDINATE, "Координаты X(t)");
            put(PlotType.PT_VELOCITY, "Скорость V(t)");
            put(PlotType.PT_ENERGY, "Полная энергия E(t)");
            put(PlotType.PT_PHASE_PORTRET, "Фазовый портрет V(X)");
        }};
    }

    public void addItemToMenu(ObservableList<MenuItem> items, PlotType pt) {
        items.add(new MenuItem(plotTypePlotName.get(pt)));
        items.get(items.size() - 1).setOnAction(f -> {drawPlot(pt);});
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_COORDINATE);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_VELOCITY);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_ENERGY);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_PHASE_PORTRET);

        btnDrawModels.getItems().add(new MenuItem("Сводная таблица"));
        btnDrawModels.getItems().get(btnDrawModels.getItems().size() - 1).setOnAction(f -> {createSummaryTable();});
    }

    public void drawPlot(PlotType pt) {

//        if () return;
//
//        ArrayList<ArrayList<Double>> solutions = new ArrayList();
//        ArrayList<String> names = new ArrayList();
//        ArrayList<ArrayList<Double>> px = new ArrayList<>();
//
//        for (int i = 0; i < data.size(); ++i) {
//            Euler_KromerMethod.Solve(data.get(i), data.get(i).getN(),
//                    data.get(i).getXStart(), data.get(i).getXFinish(), data.get(i).getY0());
//
//            px.add(data.get(i).getX());
//            names.add(String.format("Model № %d", data.get(i).getNumber()));
//            if (pt == PlotType.PT_COORDINATE) {
//                solutions.add(data.get(i).getY());
//            }
//            else if (pt == PlotType.PT_VELOCITY) {
//                solutions.add(data.get(i).getNumericalVelocity());
//            }
//            else if (pt == PlotType.PT_ACCELERATION) {
//                solutions.add(data.get(i).getNumericalAcceleration());
//            }
//        }
//
//        SomeChart<XYChart> chartMatlab = new MatlabChart();
//        XYChart chartSolutions = chartMatlab.getChart(px, solutions, names);
//        chartSolutions.setTitle(plotTypePlotName.get(pt));
//        new SwingWrapper(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    private void setParameters(ObjectFallingProcess equation) {
//        equation.setY0(Double.parseDouble(heightBody.getText()));
//        equation.setV0(Double.parseDouble(speedBody.getText()));
//        equation.setXStart(Double.parseDouble(startTime.getText()));
//        equation.setXFinish(Double.parseDouble(finishTime.getText()));
//        equation.setN(Integer.parseInt(numberCounts.getText()));
//
//        equation.setEnvironment(environment.getValue());
//        equation.setMaterialBody(materialBody.getValue());
//        equation.setRadius(Double.parseDouble(radiusBody.getText()));
    }

    public void addModel() {
        // Read fields and add it to the table
//        Oscillator_1D cur = new Oscillator_1D();
//        setParameters(cur);
//
//        ObservableList<TableColumn> columns = table.getColumns();
//
//        columns.get(0).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("number"));
//        columns.get(1).setCellValueFactory(
//                new PropertyValueFactory<Equation, Double>("y0"));
//        columns.get(2).setCellValueFactory(
//                new PropertyValueFactory<Equation, Double>("v0"));
//        columns.get(3).setCellValueFactory(
//                new PropertyValueFactory<Equation, Double>("radius"));
//        columns.get(4).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("environment"));
//        columns.get(5).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("materialBody"));
//
//        ObservableList<TableColumn> forceColumns = columns.get(6).getColumns();
//
//        forceColumns.get(0).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("constGravity"));
//        forceColumns.get(1).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("F_A"));
//        forceColumns.get(2).setCellValueFactory(
//                new PropertyValueFactory<Equation, String>("F_C1"));
//
//        data.add(equation);
//        table.setItems(data);
    }

    public void createSummaryTable() {
//        if (data.isEmpty()) return;
//
//        for (int i = 0; i < data.size(); ++i) {
//            Euler_KromerMethod.Solve(data.get(i), data.get(i).getN(),
//                    data.get(i).getXStart(), data.get(i).getXFinish(), data.get(i).getY0());
//
//        }
//
//        JTable table = FallingBodiesTable.GetTable(new ArrayList<ObjectFallingProcess>(data));
//        JFrame frame = new JFrame("Table");
//        frame.add(new JScrollPane(table));
//        frame.setSize(table.getColumnModel().getTotalColumnWidth() + 20, 500);
//        frame.setVisible(true);
    }
}
