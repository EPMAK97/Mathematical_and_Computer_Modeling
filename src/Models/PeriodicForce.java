package Models;

public class PeriodicForce {
    private Double A, startTime, period;

    public PeriodicForce(Double A, Double startTime, Double period) {
        this.A = A;
        this.startTime = startTime;
        this.period = period;
    }

    public Double Compute(Double t)
    {
        t -= startTime;
        return A * (1 - Math.cos(2 * Math.PI * t / period)) / 2.0;
    }

    public boolean Finished(Double curTime) {
        return curTime - startTime > period;
    }
}
