package Main;

import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Adam_BashforthMethod;
import NumericalMethods.Adam_MoultonMethod;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import Equation.Equation;

import javax.swing.*;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Математическое и компьютерное моделирование");
        Scene scene = new Scene(root, 580, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        //launch(args);
        //
        //try {
        //    System.out.println(Experiment.Experiment.GetBestR());
        //}
        //catch (Exception e){
        //    System.out.println(e.toString());
        //}

        Double x0 = 0.0;
        Double x1 = 5.0;
        Double y0 = 0.0;
        Integer N = 20;

        Equation equation = new Equation();
        equation.setXStart(x0);
        equation.setXFinish(x1);
        equation.setT0(y0);
        equation.setN(N);

        ArrayList<ArrayList<Double>> solutions = new ArrayList<>();
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for (int i = 2; i <= 4; ++i) {
            Adam_BashforthMethod.Solve(equation, i, N, x0, x1, y0);
            solutions.add(equation.getY());
            names.add(String.format("Adam-Bashforth %d order", i));
            errors.add(equation.getAbsoluteErrors());

            Adam_MoultonMethod.Solve(equation, i, N, x0, x1, y0);
            solutions.add(equation.getY());
            names.add(String.format("Adam-Moulton %d order", i));
            errors.add(equation.getAbsoluteErrors());
        }

        //solutions.add(equation.getAnalyticalSolution());
        //names.add("Analytical solution");

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chartSolutions = chartMatlab.getChart(equation.getX(), solutions, names);
        chartSolutions.setTitle("Adam-Bashforth Method");
        new SwingWrapper<>(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        XYChart chartErrors = chartMatlab.getChart(equation.getX(), errors, names);
        chartErrors.setTitle("Absolute errors");
        new SwingWrapper<>(chartErrors).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
