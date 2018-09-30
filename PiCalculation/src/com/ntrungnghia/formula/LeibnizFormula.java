package com.ntrungnghia.formula;

import com.ntrungnghia.exception.PiAppBaseException;

/**
 * The LeibnizTask is a the implementation of Formula interface, override
 * calculate method to compute pi number from a segment of serial by using
 * Leibniz formula and return the sum of this segment.
 */
public class LeibnizFormula implements Formula {

    private LeibnizFormula() {
    }

    private static class Helper {
        private static final LeibnizFormula INSTANCE = new LeibnizFormula();
    }

    @Override
    public double calculate(final long start, final long end)
            throws PiAppBaseException {

        if (start > end) {
            throw new PiAppBaseException("Invalid segment, start index is greater than end index.");
        } else if (start < 0 || end < 0) {
            throw new PiAppBaseException("Invalid segment, start and end index must be positive number.");
        }

        double pi = 0;
        double denominator = (start * 2) + 1;
        long x;

        // Calculating pi for each task from begin to end
        for (x = start; x <= end; x++) {

            if (x % 2 == 0) {

                pi = pi + (1 / denominator);
            } else {

                pi = pi - (1 / denominator);
            }
            denominator += 2;
        }
        // Adding the result to the shared queue
        return pi * 4;
    }

    public static Formula getInstance() {
        return LeibnizFormula.Helper.INSTANCE;
    }

}
