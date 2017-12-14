package Graphics;

import org.knowm.xchart.internal.chartpart.Chart;

import java.util.ArrayList;

public interface SomeChart<C extends Chart<?, ?>> {
    C getChart(ArrayList<ArrayList<Double>> pointsX, ArrayList<ArrayList<Double>> pointsY, ArrayList<String> names);
    C getSizedChart(ArrayList<ArrayList<Double>> pointsX, ArrayList<ArrayList<Double>> pointsY, ArrayList<String> names,
                    Integer width, Integer height);
}

