package Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * Creates a real-time chart using SwingWorker
 */
public class SwingWorkerRealTime {

    MySwingWorker mySwingWorker;
    SwingWrapper<XYChart> sw;
    XYChart chart;
    ArrayList<String> names;
    ArrayList<ArrayList<Double>> pointsX, pointsY;
    ArrayList<ArrayList<Double>> curPointsX, curPointsY;
    Integer ticksPeriod, plotsCnt;

    SwingWorkerRealTime(XYChart chart, ArrayList<String> names, ArrayList<ArrayList<Double>> px, ArrayList<ArrayList<Double>> py)
    {
        this.chart = chart;
        chart.getStyler().setPlotGridLinesVisible(false);
        plotsCnt = px.size();
        ticksPeriod = 0;
        for (int i = 0; i < plotsCnt; ++i) ticksPeriod = Math.max(ticksPeriod, px.get(i).size());

        pointsX = px;
        pointsY = py;
        this.names = names;
        curPointsX = new ArrayList<>(plotsCnt);
        curPointsY = new ArrayList<>(plotsCnt);
        while (curPointsX.size() < plotsCnt) curPointsX.add(new ArrayList<>());
        while (curPointsY.size() < plotsCnt) curPointsY.add(new ArrayList<>());
    }

    public static void main(XYChart chart, ArrayList<String> names, ArrayList<ArrayList<Double>> px, ArrayList<ArrayList<Double>> py) throws Exception {
        SwingWorkerRealTime swingWorkerRealTime = new SwingWorkerRealTime(chart, names, px, py);
        swingWorkerRealTime.go();
    }

    private void go() throws Exception {
        // Create Chart
        //chart.getStyler().setLegendVisible(false);
        //chart.getStyler().setXAxisTicksVisible(false);
        //chart.getStyler().setYAxisTicksVisible(false);

        // Show it
        sw = new SwingWrapper<XYChart>(chart);
        sw.displayChart().setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        Integer curCounter = 0;

        public MySwingWorker() {

        }

        @Override
        protected Boolean doInBackground() throws Exception {

            while (!isCancelled()) {

                if (curCounter == ticksPeriod - 1) {
                    for (int i = 0; i < plotsCnt; ++i) {
                        curPointsX.get(i).clear();
                        curPointsY.get(i).clear();
                    }
                    curCounter = 0;
                    Thread.sleep(200);
                }

                for (int i = 0; i < plotsCnt; ++i) {
                    if (curCounter < pointsX.get(i).size()) {
                        curPointsX.get(i).add(pointsX.get(i).get(curCounter));
                        curPointsY.get(i).add(pointsY.get(i).get(curCounter));
                    }
                }

                ++curCounter;
                chart.getSeriesMap().clear();

                for (int i = 0; i < plotsCnt; ++i) {
                    chart.addSeries(names.get(i), curPointsX.get(i), curPointsY.get(i));
                }

                process(null);
                // publish(array);

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }

            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {

            //double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

            //chart.updateXYSeries(, null, mostRecentDataSet, null);

            //double arr[] = new double[1];
            //chart.updateXYSeries("1", new ArrayList<Double> (), new ArrayList<Double> (), null);

            long start = System.currentTimeMillis();

            sw.repaintChart();

            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(80 - duration); // 40 ms ==> 25fps
                // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
            } catch (InterruptedException e) {
            }

        }
    }
}