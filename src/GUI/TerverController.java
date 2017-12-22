package GUI;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class TerverController implements Initializable {

    // parameters
    public TextField countExperiments;
    public Label evaluationTime;
    public Label trueProbability;
    public Label evaluationError;
    public Label meanError;
    public Label standardDeviation1;
    public Label standardDeviation2;
    public Label probabilityEvaluation;

    public Integer count;
    HashMap<TaskType, Double> taskTrueAnswer = new HashMap<>();
    private Double probabilityFirstTask = 0.01543209876;
    private Double probabilitySecondTaskA = 0.16;
    private Double probabilitySecondTaskB = 0.6;
    private static Random rand = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ...
        taskTrueAnswer.put(TaskType.first, probabilityFirstTask);
        taskTrueAnswer.put(TaskType.secondA, probabilitySecondTaskA);
        taskTrueAnswer.put(TaskType.secondB, probabilitySecondTaskB);
        // ...
    }

    public interface MagicFunction {
        boolean run();
    }


    static boolean FirstExperiment() {
        HashMap<Integer, Integer> map = new HashMap<>();

        boolean sequence = true;
        for (int j = 0; j < 6; j++) {
            int rnd = rand.nextInt((6 - 1) + 1) + 1;
            map.put(rnd, map.containsKey(rnd) ? map.get(rnd) + 1 : 1);

            if (map.get(rnd) > 1) {
                sequence = false;
                break;
            }
        }

        return sequence;
    }

    static boolean SecondExpimentA() {
        while (true) {
            Double x = -5 + 10 * Math.random();
            Double y = -5 + 10 * Math.random();
            Double r = Math.sqrt(x * x + y * y);
            if (r < 5)
                return r <= 2;
        }
    }

    static boolean SecondExpimentB() {
        while (true) {
            Double x = -5 + 10 * Math.random();
            Double y = -5 + 10 * Math.random();
            Double r = Math.sqrt(x * x + y * y);
            if (r < 5)
                return (r <= 1 || 2 <= r && r <= 3 || 4 <= r && r <= 5);
        }
    }

    class ExperimentResult {
        public ArrayList<Double> probabilities = new ArrayList<>();
        public ArrayList<Double> errors = new ArrayList<>();

        private Double probability = 0.0;
        private Double sumProbabilities = 0.0;
        private Double sumErrors = 0.0;
        private Double meanValueError = 0.0;
        private Double standartDev1 = 0.0;
        private Double standartDev2 = 0.0;
        private Date timeStart;
        private Long time = 0L;

        ExperimentResult(int countExperiments, int count, MagicFunction magicFunction, TaskType taskType) {
            timeStart = new Date();

            for (int k = 0; k < countExperiments; k++) {
                int result = 0;
                for (int i = 0; i < count; i++) {

                    if (magicFunction.run())
                        result++;

                }
                probabilities.add((double)result / count);
                errors.add(Math.abs(probabilities.get(probabilities.size() - 1) - taskTrueAnswer.get(taskType)));
            }

            time = new Date().getTime() - timeStart.getTime();

            for (int i = 0; i < countExperiments; i++) {
                sumProbabilities += Math.pow(probabilities.get(i), 2) / countExperiments;
                sumErrors += errors.get(i);
            }
            probability = Math.sqrt(sumProbabilities);
            meanValueError = sumErrors / countExperiments;
            for (int i = 0; i < countExperiments; i++) {
                standartDev1 += Math.pow((errors.get(i) - meanValueError), 2);
                standartDev2 += Math.pow(meanValueError, 2);
            }
            standartDev1 /= countExperiments;
            standartDev2 /= countExperiments;
        }
    }

    enum TaskType {
        first, secondA, secondB
    }

    public void setResultsFromFirstTask(ExperimentResult res, TaskType taskType) {
        //trueProbability.setText(String.format("%.6f", taskType == TaskType.first ? probabilityFirstTask :
        //taskType == TaskType.secondA ? probabilitySecondTaskA : probabilitySecondTaskB));
        trueProbability.setText(String.format("%.6f", taskTrueAnswer.get(taskType)));
        probabilityEvaluation.setText(String.format("%.6f", res.probability));
        evaluationError.setText(String.format("%.6f", Math.abs(res.probability - taskTrueAnswer.get(taskType))));
        evaluationTime.setText(String.format("%.3f", (double)res.time / 1000));
        meanError.setText(String.format("%.6f", res.meanValueError));
        standardDeviation1.setText(String.format("%.8f", res.standartDev1));
        standardDeviation2.setText(String.format("%.8f", res.standartDev2));
    }



    public void evaluateFirstTask() {
        if (countExperiments.getText().isEmpty()) return;
        count = Integer.parseInt(countExperiments.getText());
        ExperimentResult experimentFirstTask = new ExperimentResult(10, count,
                () -> FirstExperiment(), TaskType.first);
        setResultsFromFirstTask(experimentFirstTask, TaskType.first);
    }

    public void evaluateSecondTaskA() {
        if (countExperiments.getText().isEmpty()) return;
        count = Integer.parseInt(countExperiments.getText());
        ExperimentResult experimentSecondTaskA = new ExperimentResult(10, count,
                () -> SecondExpimentA(), TaskType.secondA);
        setResultsFromFirstTask(experimentSecondTaskA, TaskType.secondA);
    }

    public void evaluateSecondTaskB() {
        if (countExperiments.getText().isEmpty()) return;
        count = Integer.parseInt(countExperiments.getText());
        ExperimentResult experimentSecondTaskB = new ExperimentResult(10, count,
                () -> SecondExpimentB(), TaskType.secondB);
        setResultsFromFirstTask(experimentSecondTaskB, TaskType.secondB);
    }

}
