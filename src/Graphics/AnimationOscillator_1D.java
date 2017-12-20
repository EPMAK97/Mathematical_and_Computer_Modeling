package Graphics;

import Models.Oscillator_1D;
import NumericalMethods.Euler_KromerMethodOscillator;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.TabPane;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;

public class AnimationOscillator_1D {
    public interface FunctionGetModels<T> {
        public T GetModels();
    }

    public enum AnimationState {AS_IDLE, AS_ACTIVE, AS_PAUSE};

    public static final Integer framesPerSec = 25;
    public static final Integer maxPointsOnChart = 500;

    private Double timeAxisWidth;
    private Integer pointsPerTick;

    private ArrayList<Double> impulseForces;
    private ArrayList<Euler_KromerMethodOscillator.PeriodicForce> periodicForces;

    private AnimationState state;
    private FunctionGetModels<ArrayList<Oscillator_1D>> functionGetModels;
    private Button btnToogle, btnStop;
    private ArrayList<Oscillator_1D> models, curs;

    private ArrayList<ArrayList<Double>> px, pt, pv, pe;
    private ArrayList<ArrayList<Double>> px_full, pv_full;
    XChartPanel<XYChart> chartPanel_XT, chartPanel_VT, chartPanel_ET, chartPanel_VX;
    private boolean seriesAdded;
    Runnable functionUpdateCharts;

    public AnimationOscillator_1D(Button btnToogle, Button btnStop, FunctionGetModels<ArrayList<Oscillator_1D>> f,
                           XChartPanel<XYChart> chartPanel_XT, XChartPanel<XYChart> chartPanel_VT,
                                  XChartPanel<XYChart> chartPanel_ET, XChartPanel chartPanel_VX,
                                  Runnable functionUpdateCharts)
    {
        this.chartPanel_XT = chartPanel_XT;
        this.chartPanel_VT = chartPanel_VT;
        this.chartPanel_ET = chartPanel_ET;
        this.chartPanel_VX = chartPanel_VX;

        this.btnToogle = btnToogle;
        this.btnStop = btnStop;
        state = AnimationState.AS_IDLE;
        functionGetModels = f;
        this.functionUpdateCharts = functionUpdateCharts;
    }

    public void Start(Double timeAxisWidth, Integer pointsPerTick) {
        if (state == AnimationState.AS_IDLE) {
            px = new ArrayList<>();
            pt = new ArrayList<>();
            pv = new ArrayList<>();
            pe = new ArrayList<>();

            px_full = new ArrayList<>();
            pv_full = new ArrayList<>();

            impulseForces = new ArrayList<>();
            periodicForces = new ArrayList<>();

            seriesAdded = false;
            this.timeAxisWidth = timeAxisWidth;
            this.pointsPerTick = pointsPerTick;

            chartPanel_XT.getChart().getSeriesMap().clear();
            chartPanel_VT.getChart().getSeriesMap().clear();
            chartPanel_ET.getChart().getSeriesMap().clear();
            chartPanel_VX.getChart().getSeriesMap().clear();

            models = functionGetModels.GetModels();
            curs = new ArrayList<Oscillator_1D>();
            for (int i = 0; i < models.size(); ++i) {
                curs.add(models.get(i).clone());
                px.add(new ArrayList<>());
                pt.add(new ArrayList<>());
                pv.add(new ArrayList<>());
                pe.add(new ArrayList<>());

                px_full.add(new ArrayList<>());
                pv_full.add(new ArrayList<>());
            }

            btnToogle.setText("Пауза");
            btnStop.setDisable(false);
            state = AnimationState.AS_ACTIVE;
            StartAnimation();
        }
        else if (state == AnimationState.AS_ACTIVE) {
            btnToogle.setText("Старт");
            state = AnimationState.AS_PAUSE;
        }
        else if (state == AnimationState.AS_PAUSE) {
            btnToogle.setText("Пауза");
            state = AnimationState.AS_ACTIVE;
            StartAnimation();
        }
    }

    public void Stop() {
        if (state == AnimationState.AS_IDLE) {
            return;
        }
        else if (state == AnimationState.AS_ACTIVE) {
            btnToogle.setText("Старт");
        }
        state = AnimationState.AS_IDLE;
        btnStop.setDisable(true);
    }

    private void StartAnimation() {
        models = functionGetModels.GetModels();
        MySwingWorker mySwingWorker = new AnimationOscillator_1D.MySwingWorker();
        mySwingWorker.execute();
    }

    public void AddImpulseForce(Double force) {
        impulseForces.add(force);
    }

    public void AddPeriodicForce(Double amplitude, Double period) {
        periodicForces.add(new Euler_KromerMethodOscillator.PeriodicForce(amplitude, curs.get(0).getT0(), period));
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        public MySwingWorker() {}

        private void UpdateChartLimitX(XChartPanel<XYChart> chart, Double x) {
            Double curMinX = chart.getChart().getStyler().getXAxisMin();
            Double curMaxX = chart.getChart().getStyler().getXAxisMax();
            chart.getChart().getStyler().setXAxisMin(Math.min(curMinX, x));
            chart.getChart().getStyler().setXAxisMax(Math.max(curMaxX, x));
        }

        private void UpdateChartLimitY(XChartPanel<XYChart> chart, Double y) {
            Double curMinY = chart.getChart().getStyler().getYAxisMin();
            Double curMaxY = chart.getChart().getStyler().getYAxisMax();
            chart.getChart().getStyler().setYAxisMin(Math.min(curMinY, y));
            chart.getChart().getStyler().setYAxisMax(Math.max(curMaxY, y));
        }

        @Override
        protected Boolean doInBackground() throws Exception {

            while (state == AnimationState.AS_ACTIVE) {

                if (curs.get(0).getN() == 0) {
                    Stop();
                }

                for (int z = 0; curs.get(0).getN() != 0 && z < pointsPerTick; ++z) {
                    for (int i = 0; i < models.size(); ++i) {
                        pt.get(i).add(curs.get(i).getT0());
                        px.get(i).add(curs.get(i).getX0());
                        pv.get(i).add(curs.get(i).getV0());
                        pe.get(i).add(curs.get(i).getEnergy());

                        px_full.get(i).add(curs.get(i).getX0());
                        pv_full.get(i).add(curs.get(i).getV0());

                        UpdateChartLimitX(chartPanel_XT, curs.get(i).getT0());
                        UpdateChartLimitX(chartPanel_VT, curs.get(i).getT0());
                        UpdateChartLimitX(chartPanel_ET, curs.get(i).getT0());

                        UpdateChartLimitY(chartPanel_XT, curs.get(i).getX0());
                        UpdateChartLimitY(chartPanel_VT, curs.get(i).getV0());
                        UpdateChartLimitY(chartPanel_ET, curs.get(i).getEnergy());

                        UpdateChartLimitX(chartPanel_VX, curs.get(i).getX0());
                        UpdateChartLimitY(chartPanel_VX, curs.get(i).getV0());
                    }

                    for (int i = 0; i < models.size(); ++i) {
                        Euler_KromerMethodOscillator.NextValues(curs.get(i), impulseForces, periodicForces);
                    }

                    impulseForces.clear();
                    for (int i = 0; i < periodicForces.size(); ++i) {
                        if (periodicForces.get(i).Finished(curs.get(i).getT0())) {
                            periodicForces.remove(i);
                        }
                    }
                }

                while (pt.get(0).size() > maxPointsOnChart) {
                    for (int i = 0; i < models.size(); ++i) {
                        pt.get(i).remove(0);
                        px.get(i).remove(0);
                        pv.get(i).remove(0);
                        pe.get(i).remove(0);
                    }
                }

                chartPanel_XT.getChart().getStyler().setXAxisMin(pt.get(0).get(0));
                chartPanel_VT.getChart().getStyler().setXAxisMin(pt.get(0).get(0));
                chartPanel_ET.getChart().getStyler().setXAxisMin(pt.get(0).get(0));

                process(null);
            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {
            if (!seriesAdded) {
                seriesAdded = true;
                for (int i = 0; i < models.size(); ++i) {
                    String name = models.get(i).getNumber().toString();
                    chartPanel_XT.getChart().addSeries(name, pt.get(i), px.get(i));
                    chartPanel_VT.getChart().addSeries(name, pt.get(i), pv.get(i));
                    chartPanel_ET.getChart().addSeries(name, pt.get(i), pe.get(i));
                    chartPanel_VX.getChart().addSeries(name, px_full.get(i), pv_full.get(i));
                }
            }

            for (int i = 0; i < models.size(); ++i) {
                String name = models.get(i).getNumber().toString();
                chartPanel_XT.getChart().updateXYSeries(name, pt.get(i), px.get(i), null);
                chartPanel_VT.getChart().updateXYSeries(name, pt.get(i), pv.get(i), null);
                chartPanel_ET.getChart().updateXYSeries(name, pt.get(i), pe.get(i), null);
                chartPanel_VX.getChart().updateXYSeries(name, px_full.get(i), pv_full.get(i), null);
            }

            functionUpdateCharts.run();

            try {
                Thread.sleep(1000 / framesPerSec);
            } catch (InterruptedException e) {
            }

        }
    }
}
