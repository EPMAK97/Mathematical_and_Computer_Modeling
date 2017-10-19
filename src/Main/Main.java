package Main;

import Equation.Equation;
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
import Equation.CoffeeCoolingProcess;

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

        CoffeeCoolingProcess coffeeCoolingProcess = new CoffeeCoolingProcess();
        coffeeCoolingProcess.setXStart(x0);
        coffeeCoolingProcess.setXFinish(x1);
        coffeeCoolingProcess.setT0(y0);
        coffeeCoolingProcess.setN(N);

        ArrayList<ArrayList<Double>> solutions = new ArrayList<>();
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for (int i = 2; i <= 4; ++i) {
            Adam_BashforthMethod.Solve(coffeeCoolingProcess, i, N, x0, x1, y0);
            solutions.add(coffeeCoolingProcess.getY());
            names.add(String.format("Adam-Bashforth %d order", i));
            errors.add(coffeeCoolingProcess.getAbsoluteErrors());

            Adam_MoultonMethod.Solve(coffeeCoolingProcess, i, N, x0, x1, y0);
            solutions.add(coffeeCoolingProcess.getY());
            names.add(String.format("Adam-Moulton %d order", i));
            errors.add(coffeeCoolingProcess.getAbsoluteErrors());
        }

        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chartSolutions = chartMatlab.getChart(coffeeCoolingProcess.getX(), solutions, names);
        chartSolutions.setTitle("Adam-Bashforth Method");
        new SwingWrapper<>(chartSolutions).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        XYChart chartErrors = chartMatlab.getChart(coffeeCoolingProcess.getX(), errors, names);
        chartErrors.setTitle("Absolute errors");
        new SwingWrapper<>(chartErrors).displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
