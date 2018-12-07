package de.tub.ise.ec.calculators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class responsible for latency calculation.
 * <br>
 * Latency is defined as the period of time between the start and the commit timestamp of a write
 * operation.
 *
 * @author Jacek Janczura
 */
public class LatencyCalculator implements ICalculator {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


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
