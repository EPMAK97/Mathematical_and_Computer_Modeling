package Graphics;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;

public class MatlabChart implements SomeChart<XYChart> {
    @Override
    public XYChart getChart(ArrayList<ArrayList<Double>> pointsX, ArrayList<ArrayList<Double>> pointsY, ArrayList<String> names) {
        // Create Chart
        //XYChart chart = new XYChartBuilder().width(800).height(600).theme(Styler.ChartTheme.Matlab).title("").xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart = new XYChartBuilder().width(800).height(600).theme(Styler.ChartTheme.Matlab).title("").build();

        // Customize Chart
        //chart.getStyler().setPlotGridLinesVisible(false);

        //chart.getStyler().setXAxisTickMarkSpacingHint(100);
        //chart.getStyler().setToolTipsEnabled(true);

        //        chart.getStyler().setYAxisLogarithmic(true);

//        px.remove(0);
        for (int i = 0; i < pointsY.size(); i++) {
            ArrayList<Double> px = new ArrayList<>(pointsX.get(i));
            ArrayList<Double> py = new ArrayList<>(pointsY.get(i));
//            py.remove(0);
            XYSeries series = chart.addSeries(names.get(i), px, py);
            //series.setMarker(SeriesMarkers.NONE);
        }

        //chart.getStyler().setYAxisGroupPosition(1, Styler.YAxisPosition.Right);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        //chart.getStyler().setYAxisDecimalPattern("0.0000");
        return chart;
    }

    public XYChart getSizedChart(ArrayList<ArrayList<Double>> pointsX, ArrayList<ArrayList<Double>> pointsY, ArrayList<String> names,
                                 Integer width, Integer height)
    {
        // Create Chart
        //XYChart chart = new XYChartBuilder().width(800).height(600).theme(Styler.ChartTheme.Matlab).title("").xAxisTitle("X").yAxisTitle("Y").build();
        XYChart chart = new XYChartBuilder().width(width).height(height).theme(Styler.ChartTheme.Matlab).title("").build();

        // Customize Chart
        //chart.getStyler().setPlotGridLinesVisible(false);

        //chart.getStyler().setXAxisTickMarkSpacingHint(100);
        //chart.getStyler().setToolTipsEnabled(true);

        //        chart.getStyler().setYAxisLogarithmic(true);

//        px.remove(0);
        for (int i = 0; i < pointsY.size(); i++) {
            ArrayList<Double> px = new ArrayList<>(pointsX.get(i));
            ArrayList<Double> py = new ArrayList<>(pointsY.get(i));
//            py.remove(0);
            XYSeries series = chart.addSeries(names.get(i), px, py);
            //series.setMarker(SeriesMarkers.NONE);
        }

        //chart.getStyler().setYAxisGroupPosition(1, Styler.YAxisPosition.Right);
        //chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        //chart.getStyler().setYAxisDecimalPattern("0.0000");
        return chart;
    }
}
