package Main;

import Equation.Equation;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.EulerMethod;
import NumericalMethods.EulerMethodImproved;
import NumericalMethods.Euler_KoshiMethod;
import NumericalMethods.Runge_KuttaMethod;
import ResultsTable.ResultsTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 450, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

//        Equation equation = new Equation();
//        equation.setR(1.0);
//        equation.setT0(100.0);
//        equation.setTc(24.0);
//        equation.setXStart(0.0);
//        equation.setXFinish(2.0);

        //ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        //ArrayList<String> names = new ArrayList<>();
//
        //EulerMethodImproved.Solve(equation,50, equation.getXStart(), equation.getXFinish(), equation.getT0());
        //errors.add(equation.getRelativeErrors());
        //names.add("Runge_Kutta");
//
        //EulerMethodImproved.Solve(equation, 40, equation.getXStart(), equation.getXFinish(), equation.getT0());
        //errors.add(equation.getRelativeErrors());
        //names.add("Euler_Improved");
//
        //Euler_KoshiMethod.Solve(equation, 40, equation.getXStart(), equation.getXFinish(), equation.getT0());
        //errors.add(equation.getRelativeErrors());
        //names.add("Euler_Koshi");
//
        //EulerMethod.Solve(equation, 40, equation.getXStart(), equation.getXFinish(), equation.getT0());
        //errors.add(equation.getRelativeErrors());
        //names.add("Euler");

        //SomeChart<XYChart> chartMatlab = new MatlabChart();
        //XYChart chart1 = chartMatlab.getChart(equation.getX(), errors, names);
        //new SwingWrapper<XYChart>(chart1).displayChart();
    }
}
