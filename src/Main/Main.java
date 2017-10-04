package Main;

import Equation.Equation;
import Graphics.MatlabChart;
import Graphics.SomeChart;
import NumericalMethods.Runge_KuttaMethod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import sun.jvm.hotspot.jdi.ArrayReferenceImpl;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);

        Equation equation = new Equation();
        equation.setR(1.0);
        equation.setT0(100.0);
        equation.setTc(24.0);
        equation.setStep((Equation.getT0() - Equation.getTc()) / 50);

        Runge_KuttaMethod.Solve(equation, Equation.getStep(), 50, Equation.getT0(), Equation.getTc());
        ArrayList<ArrayList<Double>> errors = new ArrayList<>();
        errors.add(equation.getAbsoluteErrors());
        errors.add(equation.getRelativeErrors());

        ArrayList<String> names = new ArrayList<>();
        names.add("Absolute error");
        names.add("Relative error");


        SomeChart<XYChart> chartMatlab = new MatlabChart();
        XYChart chart = chartMatlab.getChart(equation.getPointsX(), errors, names);
        new SwingWrapper<XYChart>(chart).displayChart();

//        double[] pointsX = new double[10];
//        double[] pointsY = new double[10];

//        double step = 0.1;
//        for (int i = 0; i < 10; i++, step += 0.1) {
//            pointsX[i] = step;
//            System.out.println(pointsX[i]);
//            pointsY[i] = 3/4 * Math.expm1(- 2 * step)
//                    + 1 / 2 * step * step - 1 / 2 * step + 1 / 4;
//            System.out.println(pointsY[i]);
//
//        }

        //XYChart chart1 = QuickChart.getChart("Runge_KuttaMethod", "X", "Y", "y(x)", pointsX, pointsY);
        //new SwingWrapper(chart1).displayChart();

        //System.out.println(pointsX.length == 0 ? "AAAA" : "BBBB");
//
//        if (Runge_KuttaMethod.Solve(equation, 0.1, 10, 0, 1)) {
//            XYChart chart = QuickChart.getChart("Runge_KuttaMethod", "X", "Y", "y(x)", Equation.getX(), Equation.getY());
//            //jFrame.add(new SwingWrapper(chart1).displayChart());
//            new SwingWrapper(chart).displayChart();
//        }


    }
}
