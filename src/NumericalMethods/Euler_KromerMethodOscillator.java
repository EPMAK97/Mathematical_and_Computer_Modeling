package NumericalMethods;

import Models.Oscillator_1D;

public class Euler_KromerMethodOscillator {
    // CAUTIOUS: this function changes fields X and V in argument 'osc'
    public static Boolean NextValues(Oscillator_1D osc, double f_impulse, double f_period) {
        try {
            double deltaT = (osc.getT1() - osc.getT0()) / osc.getN();
            double w_0 = Math.sqrt(osc.getK() / osc.getM());
            double a = -w_0 * w_0 * osc.getX0() - osc.getGamma() * osc.getV0() + f_impulse + f_period;

            osc.setV0(osc.getV0() + a * deltaT);
            osc.setX0(osc.getX0() + osc.getV0() * deltaT);

        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    // CAUTIOUS: this function changes fields X and V in argument 'osc'
    public static Boolean NextValues(Oscillator_1D osc) {
        return NextValues(osc, 0, 0);
    }
}
