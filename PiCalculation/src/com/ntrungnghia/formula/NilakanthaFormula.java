package com.ntrungnghia.formula;

import com.ntrungnghia.exception.PiAppBaseException;

/**
 * The LeibnizTask is a the implementation of Formula interface, override
 * calculate method to compute pi number from a segment of serial by using
 * Nilakantha formula and return the sum of this segment.
 */
public class NilakanthaFormula implements Formula {

    private NilakanthaFormula() {
    }

    private static class Helper {
        private static final NilakanthaFormula INSTANCE = new NilakanthaFormula();
    }

    @Override
    public double calculate(final long start, final long end)
            throws PiAppBaseException {

    	if (start > end) {
            throw new PiAppBaseException("Invalid segment, start index is greater than end index.");
        } else if (start < 0 || end < 0) {
            throw new PiAppBaseException("Invalid segment, start and end index must be positive number.");
        }

        double pi = 0.0;
        long step = start;
        long j = 0;
        if (step == 0) {
            pi += 3.0;
            j = 2;
        } else {
            j = 2 * step + 2;
        }

        // Calculating pi for each task from begin to end
        while (true) {
            step++;
            if ((step % 2) == 1) {
                pi += 4.0 / (1.0 * j * (j + 1) * (j + 2));
            } else {
                pi -= 4.0 / (1.0 * j * (j + 1) * (j + 2));
            }

            j += 2;

            if (step >= end)
                break;
        }

        // Adding the result to the shared queue
        return pi;
    }

    public static NilakanthaFormula getInstance() {
        return NilakanthaFormula.Helper.INSTANCE;
    }

}
