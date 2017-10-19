package Main;

import Equation.CoffeeCoolingProcess;
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

import javax.swing.*;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/CoffeeCooling.fxml"));
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
    }
}
