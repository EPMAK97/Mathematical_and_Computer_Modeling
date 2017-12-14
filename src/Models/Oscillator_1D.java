package Models;

public class Oscillator_1D {
    private Double t0, t1;
    private Integer N;
    private Double x0, v0;
    private Double m, k, gamma;
    private int number;
    private static int modelCounter = 1;

    public Oscillator_1D() { this.number = modelCounter++; }

    public Oscillator_1D(double t0, double t1, int N, double x0, double v0, double m, double k, double gamma, int number) {
        this.t0 = t0;
        this.t1 = t1;
        this.N = N;
        this.x0 = x0;
        this.v0 = v0;
        this.m = m;
        this.k = k;
        this.gamma = gamma;
        this.number = number;
    }

    public Oscillator_1D clone() {
        return new Oscillator_1D(t0, t1, N, x0, v0, m, k, gamma, number);
    }

    public Double getEnergy() { return 0.5 * m * Math.pow(v0, 2) + 0.5 * k * Math.pow(x0, 2); }

    public void setT0(Double t0) { this.t0 = t0; }

    public void setT1(Double t1) { this.t1 = t1; }

    public void setN(Integer n) { N = n; }

    public void setX0(Double x0) { this.x0 = x0; }

    public void setV0(Double v0) { this.v0 = v0; }

    public void setM(Double m) { this.m = m; }

    public void setK(Double k) { this.k = k; }

    public void setGamma(Double gamma) { this.gamma = gamma; }

    public Double getT0() { return t0; }

    public Double getT1() { return t1; }

    public Integer getN() { return N; }

    public Double getX0() { return x0; }

    public Double getV0() { return v0; }

    public Double getM() { return m; }

    public Double getK() { return k; }

    public Double getGamma() { return gamma; }

    public Integer getNumber() { return number; }
}
