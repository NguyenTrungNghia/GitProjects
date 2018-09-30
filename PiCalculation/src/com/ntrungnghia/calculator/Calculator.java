package com.ntrungnghia.calculator;

import java.util.concurrent.ExecutionException;

import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;
import com.ntrungnghia.model.PiResult;

/**
 * Provides some behavior of the pi calculation like getting the state of
 * calculation, current position, calculate pi by n.
 * 
 * @author ntrungnghia
 *
 */
public abstract class Calculator {

    protected int numOfThreads = Runtime.getRuntime().availableProcessors();
    protected PiResult pi;

    // The state of pi calculation.
    protected boolean stop;
    protected long currentPosition;
    protected long serial;

    public Calculator() {
        this.pi = new PiResult();
    }

    /**
     * Sets the number of threads have been used for the pi calculation.
     * 
     * @param numOfThreads
     *            number of threads.
     * @throws {@link PiAppBaseException}
     */
    public void setNumOfThreads(int numOfThreads) throws PiAppBaseException {
        if (numOfThreads <= 0) {
            throw new PiAppBaseException("Invalid number of threads, it must be greater than 0.");
        } else {
            this.numOfThreads = numOfThreads;
        }
    }

    /**
     * Returns {@link pi} number at the current time.
     */
    public PiResult getPi() {
        return this.pi;
    }

    /**
     * Returns current state of pi calculation, return true if it's have stopped
     * and false if it is calculating pi number.
     * 
     */
    public boolean isStop() {
        return this.stop;
    }

    /**
     * Stop the pi calculation.
     */
    public void stop() {
        this.stop = true;
    }

    /**
     * Returns the current position, if the calculation has been done, it will
     * return n otherwise it will return the current position in the serial.
     * 
     * @return current position.
     */
    public abstract long getCurrentPosition();

    /**
     * Calculates and returns pi number that has been calculated according to
     * the serial. While calculating the pi, if the state of pi calculation is
     * false is will stop and return the current pi number.
     * 
     * @param formula
     * @param n
     *            a serial.
     * @return pi
     * @throws {@link PiAppBaseException}
     * @throws {@link ExecutionException}
     * @throws {@link InterruptedException}
     */
    public abstract PiResult execute(Formula formula, long n)
            throws PiAppBaseException, InterruptedException,
            ExecutionException;

}
