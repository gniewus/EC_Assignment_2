package de.tub.ise.ec.calculators;


/**
 * Class responsible for staleness calculation.
 * <br>
 * Staleness is defined as the period of time between the commit timestamp of the corresponding write
 * operation (=timestamp at which the write terminates) and the point in time when the last replica is
 * written.
 * <br>
 * Example: A write starts at T=0 and commits at T=2. The first replica is written at T=2, the second at
 * T=5. In that case, staleness would be 3 (=5-2).
 * @author Jacek Janczura
 */
public class StalenessCalculator implements ICalculator{
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
