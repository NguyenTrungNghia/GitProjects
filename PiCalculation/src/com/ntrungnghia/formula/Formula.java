package com.ntrungnghia.formula;

import com.ntrungnghia.exception.PiAppBaseException;

public interface Formula {

    /**
     * Calculates a segment of the serial and return the sum of this segment.
     * 
     * @param {@link start} start index
     * @param {@link end} end index
     * @throws {@link PiAppBaseException}
     */
    double calculate(final long start, final long end)
            throws PiAppBaseException;

}
