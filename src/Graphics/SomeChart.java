package Graphics;

import org.knowm.xchart.internal.chartpart.Chart;

import java.util.ArrayList;

public interface SomeChart<C extends Chart<?, ?>> {
    C getChart(ArrayList<Double> pointsX, ArrayList<ArrayList<Double>> pointsY, ArrayList<String> names);
}

