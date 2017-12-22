package NumericalMethods;

import Models.Oscillator_1D;
import Models.PeriodicForce;

import java.util.ArrayList;
import java.util.Collections;

public class Euler_KromerMethodOscillator {
    // CAUTIOUS: this function changes fields X0, T0, V0 and N in argument 'osc'
    public static Boolean NextValues(Oscillator_1D osc, ArrayList<Double> impulseForces, ArrayList<PeriodicForce> periodicForces) {
        try {
            if (osc.getN() == 0) return true;

            double curTime = osc.getT0();
            double deltaT = (osc.getT1() - osc.getT0()) / osc.getN();
            osc.setT0(osc.getT0() + deltaT);
            osc.setN(osc.getN() - 1);

            double a_impulse = 0.0;
            for (Double impulse : impulseForces)
                a_impulse += impulse / osc.getM();

            double a_periodic = 0.0;
            for (PeriodicForce force : periodicForces)
                a_periodic += force.Compute(curTime) / osc.getM();

            double w_0 = Math.sqrt(osc.getK() / osc.getM());
            double a = -w_0 * w_0 * osc.getX0() - osc.getGamma() * osc.getV0() + a_impulse + a_periodic;

            osc.setV0(osc.getV0() + a * deltaT);
            osc.setX0(osc.getX0() + osc.getV0() * deltaT);

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    // CAUTIOUS: this function changes fields X0, T0, V0 and N in argument 'osc'
    public static Boolean NextValues(Oscillator_1D osc) {
        return NextValues(osc, new ArrayList<>(), new ArrayList<>());
    }
}
