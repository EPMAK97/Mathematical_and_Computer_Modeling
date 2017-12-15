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

    private static Integer slidingWindowWidth = 60;

    public enum AnimationState {AS_IDLE, AS_ACTIVE, AS_PAUSE};
    private AnimationState state;
    private FunctionGetModels<ArrayList<Oscillator_1D>> functionCurrentModel;
    private Button btnToogle, btnStop;
    private ArrayList<Oscillator_1D> models, curs;

    private ArrayList<ArrayList<Double>> px, pt, pv, pe;
    XYChart chart_XT, chart_VT, chart_ET, chart_VX;
    TabPane tabPaneAnimationPlots;

    AnimationOscillator_1D(Button btnToogle, Button btnStop, FunctionGetModels<ArrayList<Oscillator_1D>> f,
                           XYChart chart_XT, XYChart chart_VT, XYChart chart_ET, XYChart chart_VX,
                           TabPane tabPaneAnimationPlots)
    {
        this.chart_XT = chart_XT;
        this.chart_VT = chart_VT;
        this.chart_ET = chart_ET;
        this.chart_VX = chart_VX;
        this.tabPaneAnimationPlots = tabPaneAnimationPlots;

        this.btnToogle = btnToogle;
        this.btnStop = btnStop;
        state = AnimationState.AS_IDLE;
        functionCurrentModel = f;
    }

    public void OnStartBtnClick() {
        if (state == AnimationState.AS_IDLE) {
            px = new ArrayList<>();
            pt = new ArrayList<>();
            pv = new ArrayList<>();
            pe = new ArrayList<>();

            models = functionCurrentModel.GetModels();
            for (int i = 0; i < models.size(); ++i) {
                curs.add(models.get(i).clone());
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

    public void OnStopBtnClick() {
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
        MySwingWorker mySwingWorker = new AnimationOscillator_1D.MySwingWorker();
        mySwingWorker.execute();
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {

        public MySwingWorker() {}

        @Override
        protected Boolean doInBackground() throws Exception {

            while (state == AnimationState.AS_ACTIVE) {

                for (int i = 0; i < models.size(); ++i) {
                    pt.get(i).add(curs.get(i).getT0());
                    px.get(i).add(curs.get(i).getX0());
                    pv.get(i).add(curs.get(i).getV0());
                    pe.get(i).add(curs.get(i).getEnergy());
                }

                if (curs.get(0).getN() == 0) {
                    state = AnimationState.AS_IDLE;
                }

                for (int i = 0; i < models.size(); ++i) {
                    Euler_KromerMethodOscillator.NextValues(curs.get(i));
                }

                if (pt.get(0).size() > slidingWindowWidth) {
                    for (int i = 0; i < models.size(); ++i) {
                        pt.get(i).remove(0);
                        px.get(i).remove(0);
                        pv.get(i).remove(0);
                        pe.get(i).remove(0);
                    }
                }

                chart_XT.getSeriesMap().clear();
                chart_VT.getSeriesMap().clear();
                chart_ET.getSeriesMap().clear();
                chart_VX.getSeriesMap().clear();

                for (int i = 0; i < models.size(); ++i) {
                    chart_XT.addSeries("", pt.get(i), px.get(i));
                    chart_VT.addSeries("", pt.get(i), pv.get(i));
                    chart_ET.addSeries("", pt.get(i), pe.get(i));
                    chart_VX.addSeries("", px.get(i), pv.get(i));
                }

                process(null);

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

//            SwingWrapper<XYChart> tmp;

            tabPaneAnimationPlots.requestLayout();
//            sw.repaintChart();

            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(80 - duration); // 40 ms ==> 25fps
                // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
            } catch (InterruptedException e) {
            }

        }
    }
}
