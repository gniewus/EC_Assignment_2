package de.tub.ise.ec.calculators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Class responsible for staleness calculation.
 * <br>
 * Staleness is defined as the period of time between the commit timestamp of the corresponding write
 * operation (=timestamp at which the write terminates) and the point in time when the last replica is
 * written.
 * <br>
 * Example: A write starts at T=0 and commits at T=2. The first replica is written at T=2, the second at
 * T=5. In that case, staleness would be 3 (=5-2).
 *
 * @author Jacek Janczura
 */
public class StalenessCalculator implements ICalculator{

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
