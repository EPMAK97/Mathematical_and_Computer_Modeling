package Main;

import Equation.Equation;
import NumericalMethods.Runge_KuttaMethod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

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

        double[] pointsX = new double[10];
        double[] pointsY = new double[10];

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

        if (Runge_KuttaMethod.Solve(equation, 0.1, 10, 0, 1)) {
            XYChart chart = QuickChart.getChart("Runge_KuttaMethod", "X", "Y", "y(x)", Equation.getX(), Equation.getY());
            //jFrame.add(new SwingWrapper(chart1).displayChart());
            new SwingWrapper(chart).displayChart();
        }
    }
}
