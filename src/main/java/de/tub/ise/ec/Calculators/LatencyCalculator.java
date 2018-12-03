package de.tub.ise.ec.Calculators;


/**
 * Class responsible for latency calculation.
 * <br>
 * Latency is defined as the period of time between the start and the commit timestamp of a write
 * operation.
 * @author Jacek Janczura
 */
public class LatencyCalculator implements ICalculator {
    @Override
    public double min() {
        return 0;
    }

    @Override
    public double max() {
        return 0;
    }

    @Override
    public double avg() {
        return 0;
    }
}
