package Main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Математическое и компьютерное моделирование");
        Scene scene = new Scene(root, 450, 400);
        scene.getStylesheets().add("Form.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mathematical and Computer Modeling");
        primaryStage.setScene(new Scene(root, 580, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
