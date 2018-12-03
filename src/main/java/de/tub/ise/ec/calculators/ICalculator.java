package de.tub.ise.ec.calculators;

/**
 * Implementations of this interface can calculate min, max and average value.
 *
 * @author Jacek Janczura
 */
public interface ICalculator {

    public double min();

    public double max();

    public double avg();
}
