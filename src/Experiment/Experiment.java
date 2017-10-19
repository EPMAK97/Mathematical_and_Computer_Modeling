package Experiment;

import Equation.CoffeeCoolingProcess;

import java.util.ArrayList;
import java.util.Arrays;

public class Experiment {
    public static Double GetBestR() throws Exception {
        CoffeeCoolingProcess coffeeCoolingProcess = new CoffeeCoolingProcess();
        coffeeCoolingProcess.setT0(83.0);
        coffeeCoolingProcess.setTc(22.0);
        coffeeCoolingProcess.setN(15);
        coffeeCoolingProcess.setXStart(0.0);
        coffeeCoolingProcess.setXFinish(15.0);

        ArrayList<Double> true_values = new ArrayList<>(Arrays.asList(
                83.0, 77.7, 75.1,73.0,
                71.1, 69.4, 67.8, 66.4,
                64.7, 63.4, 62.1, 61.0,
                59.9, 58.7, 57.8, 56.6
        ));

        Double step = 0.001;
        Double minsum = 1e10;
        Double bestR = -1.0;

        for (Double r = step; r < 1.0; r += step) {
            coffeeCoolingProcess.setR(r);
            coffeeCoolingProcess.getAnalyticalSolution().clear();

            Double cursum = 0.0;
            for (int j = 0; j < 16; ++j) {
                Double diff = coffeeCoolingProcess.getAnalyticalSolution().get(j) - true_values.get(j);
                cursum += diff * diff;
            }
            if (cursum < minsum) {
                minsum = cursum;
                bestR = r;
            }
        }

        return bestR;
    }
}
