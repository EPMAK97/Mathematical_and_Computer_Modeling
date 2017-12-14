package Models;

public class Oscillator_1D {
    private Double t0, t1, N;
    private Double x0, v0;
    private Double m, k, gamma;
    private int number;
    private static int modelCounter = 1;

    public Oscillator_1D() { this.number = modelCounter++; }

    public void setT0(Double t0) { this.t0 = t0; }

    public void setT1(Double t1) { this.t1 = t1; }

    public void setN(Double n) { N = n; }

    public void setX0(Double x0) { this.x0 = x0; }

    public void setV0(Double v0) { this.v0 = v0; }

    public void setM(Double m) { this.m = m; }

    public void setK(Double k) { this.k = k; }

    public void setGamma(Double gamma) { this.gamma = gamma; }

    public Double getT0() { return t0; }

    public Double getT1() { return t1; }

    public Double getN() { return N; }

    public Double getX0() { return x0; }

    public Double getV0() { return v0; }

    public Double getM() { return m; }

    public Double getK() { return k; }

    public Double getGamma() { return gamma; }

    public Integer getNumber() { return number; }
}
