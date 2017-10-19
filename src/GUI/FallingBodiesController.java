package GUI;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class FallingBodiesController implements Initializable {
    public TextField heightBody;
    public TextField speedBody;
    public TextField startTime;
    public TextField finishTime;
    public TextField numberCounts;
    public TextField stepCounts;

    public TextField densityBody;
    public TextField densityMedium;
    public TextField massBody;
    public TextField volumeBody;
    public TextField radiusBody;

    public ComboBox<String> medium;
    public ComboBox<String> materialBody;

    public Button compute;

    private static HashMap<String, Double> materialDensity;

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
           put("Камни",  2200.0);
           put("Сталь",  7800.0);
           put("Алюминий", 2700.0);
           put("Свинец",   11300.0);
           put("Серебро",  10600.0);
           put("Золото",   19300.0);
        }};
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        medium.getItems().setAll("Воздух",
                "Водород",
                "Вода",
                "Гелий",
                "Глицерин",
                "Оливковое масло",
                "Поливинилхлорид");

        materialBody.getItems().setAll("Дерево",
                "Стекло",
                "Резина",
                "Камни",
                "Сталь",
                "Алюминий",
                "Свинец",
                "Серебро",
                "Золото");

    }

    public void setDensityMedium(String s) {
        densityMedium.setText(s);
    }

    public void setDensityBody(String s) {
        densityBody.setText(s);
    }

    public void setMaterialBody() {
        if (materialBody.getValue() != null) {
            if (materialDensity.containsKey(materialBody.getValue()))
                setDensityMedium(String.valueOf(materialDensity.get(materialBody.getValue())));
        }
    }

    public void setMedium() {
        if (medium.getValue() != null) {
            if (materialDensity.containsKey(medium.getValue()))
                setDensityBody(String.valueOf(materialDensity.get(medium.getValue())));
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

    public void computeFromParameters() {
        // Считать все и посчитать
    }

}
