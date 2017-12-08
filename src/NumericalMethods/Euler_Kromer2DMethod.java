package NumericalMethods;

import Equation.ObjectFalling2DProcess;

public class Euler_Kromer2DMethod {
    public static Boolean Solve(ObjectFalling2DProcess equation) {
        try {
            double t0 = equation.getXStart();
            double t1 = equation.getXFinish();
            double step = (t1 - t0) / equation.getN();

            double t = t0;
            double x = equation.getX0();
            double y = equation.getY0();

            double vx = equation.getV0_X();
            double vy = equation.getV0_Y();

            equation.Clear();
            equation.addPoint(t, x, y, vx, vy);

            for (int i = 1; i <= equation.getN(); ++i) {
                vx += step * equation.computeAccelerationX(t, x, vx);
                vy += step * equation.computeAccelerationY(t, y, vy);
                x += step * vx;
                y += step * vy;
                t += step;
                equation.addPoint(t, x, y, vx, vy);
                if (y < 0) break;
            }
        } catch (Exception e) {
            System.out.println("Euler_KromerMethod fail");
            return false;
        }
        return true;
    }
}
