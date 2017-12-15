package GUI;

import Equation.Equation;
import Equation.ObjectFallingProcess;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Euler_KromerMethod;
import NumericalMethods.Euler_KromerMethodOscillator;
import ResultsTable.FallingBodiesTable;
import Models.Oscillator_1D;

import ResultsTable.Oscillator_1D_Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
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

    public FlowPane flowpaneOscillatorAnimation;

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
    public void initialize(URL location, ResourceBundle resources) throws NumberFormatException {
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_COORDINATE);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_VELOCITY);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_ENERGY);
        addItemToMenu(btnDrawModels.getItems(), PlotType.PT_PHASE_PORTRET);

        btnDrawModels.getItems().add(new MenuItem("Сводная таблица"));
        btnDrawModels.getItems().get(btnDrawModels.getItems().size() - 1).setOnAction(f -> {createSummaryTable();});
    }

    public void drawPlot(PlotType pt) {

        if (modelsData.isEmpty()) return;

        setStartTimes();

        ArrayList<String> names = new ArrayList();
        ArrayList<ArrayList<Double>> px = new ArrayList<>();
        ArrayList<ArrayList<Double>> py = new ArrayList<>();

        for (int i = 0; i < modelsData.size(); ++i) {
            Oscillator_1D cur = modelsData.get(i).clone();
            ArrayList<Double> t_arr = new ArrayList<>();
            ArrayList<Double> x_arr = new ArrayList<>();
            ArrayList<Double> v_arr = new ArrayList<>();
            ArrayList<Double> e_arr = new ArrayList<>();

            for (int j = 0; j <= modelsData.get(i).getN(); ++j) {
                t_arr.add(cur.getT0());
                x_arr.add(cur.getX0());
                v_arr.add(cur.getV0());
                e_arr.add(cur.getEnergy());
                Euler_KromerMethodOscillator.NextValues(cur);
            }

            names.add(String.format("Model № %d", cur.getNumber()));
            if (pt == PlotType.PT_COORDINATE) {
                px.add(t_arr);
                py.add(x_arr);
            }
            else if (pt == PlotType.PT_VELOCITY) {
                px.add(t_arr);
                py.add(v_arr);
            }
            else if (pt == PlotType.PT_ENERGY) {
                px.add(t_arr);
                py.add(e_arr);
            }
            else if (pt == PlotType.PT_PHASE_PORTRET) {
                px.add(x_arr);
                py.add(v_arr);
            }
        }

//        px.add(new ArrayList<Double>(){{add(1.0); add(2.0); add(3.0);}});
//        solutions.add(new ArrayList<Double>(){{add(10.0); add(2.0); add(6.0);}});
//        names.add("Example");

        SomeChart<XYChart> chartMatlab = new MatlabChart();
//        XYChart chartSolutions = chartMatlab.getSizedChart(px, solutions, names,
//                (int)flowpaneOscillatorAnimation.getWidth(),(int)flowpaneOscillatorAnimation.getHeight());
//
//        SwingNode swingNode = new SwingNode();
//        swingNode.setContent(new XChartPanel(chartSolutions));
//        flowpaneOscillatorAnimation.getChildren().add(swingNode);

        XYChart chartSolutions = chartMatlab.getChart(px, py, names);
        chartSolutions.setTitle(plotTypePlotName.get(pt));
        new SwingWrapper(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void setParameters(Oscillator_1D oscillator1D) {
        oscillator1D.setGamma(Double.parseDouble(editFrictionCoeff.getText()) * 9.8); // gamma * g
        oscillator1D.setM(Double.parseDouble(editBodyMass.getText()));
        oscillator1D.setN(Integer.parseInt(editNumberOfCounts.getText()));
        oscillator1D.setT0(Double.parseDouble(editStartTime.getText()));
        oscillator1D.setT1(Double.parseDouble(editFinishTime.getText()));
        oscillator1D.setX0(Double.parseDouble(editStartX.getText()));
        oscillator1D.setV0(Double.parseDouble(editStartVelocity.getText()));
        oscillator1D.setK(Double.parseDouble(editRestitutionCoeff.getText()));
    }

    public void addModel() {
        // Read fields and add it to the table
        Oscillator_1D cur = new Oscillator_1D();
        setParameters(cur);

        ObservableList<TableColumn> columns = tableModels.getColumns();

        columns.get(0).setCellValueFactory(
                new PropertyValueFactory<Oscillator_1D, String>("number"));
        columns.get(1).setCellValueFactory(
                new PropertyValueFactory<Equation, Double>("x0"));
        columns.get(2).setCellValueFactory(
                new PropertyValueFactory<Equation, Double>("v0"));
        columns.get(3).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("m"));
        columns.get(4).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("k"));
        columns.get(5).setCellValueFactory(
                new PropertyValueFactory<Equation, String>("gamma"));
        modelsData.add(cur);
        tableModels.setItems(modelsData);
    }

    public void deleteFromTable() {
        Integer idx = tableModels.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            modelsData.remove(idx, idx + 1);
            tableModels.setItems(modelsData);
        }
    }

    public void createSummaryTable() {
        if (modelsData.isEmpty()) return;
        setStartTimes();
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<ArrayList<Double>> modelInfo = new ArrayList<>();
        for (int i = 0; i < modelsData.size(); ++i) {
            Oscillator_1D cur = modelsData.get(i).clone();
            data.add(new ArrayList<>());
            modelInfo.add(new ArrayList<>());
            modelInfo.get(i).add(modelsData.get(i).getX0());
            modelInfo.get(i).add(modelsData.get(i).getV0());
            modelInfo.get(i).add(modelsData.get(i).getM());
            modelInfo.get(i).add(modelsData.get(i).getK());
            modelInfo.get(i).add(modelsData.get(i).getGamma());
            for (int j = 0; j <= modelsData.get(i).getN(); ++j) {
                data.get(i).add(cur.getT0());
                data.get(i).add(cur.getX0());
                data.get(i).add(cur.getV0());
                data.get(i).add(cur.getEnergy());
                Euler_KromerMethodOscillator.NextValues(cur);
            }
        }
        ArrayList<JTable> tables = Oscillator_1D_Table.GetTable(data, modelInfo);
        for (JTable jtable: tables) {
            JFrame frame = new JFrame("Table");
            frame.add(new JScrollPane(jtable));
            frame.setSize(jtable.getColumnModel().getTotalColumnWidth() + 20, 500);
            frame.setVisible(true);
        }
    }

    public void setStartTimes() throws NumberFormatException {
        for (Oscillator_1D oscillator : modelsData) {
            oscillator.setT0(Double.parseDouble(editStartTime.getText()));
            oscillator.setT1(Double.parseDouble(editFinishTime.getText()));
            oscillator.setN(Integer.parseInt(editNumberOfCounts.getText()));
        }
    }

}
