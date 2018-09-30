package com.ntrungnghia.tasks;

import java.util.concurrent.Callable;

import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;

/**
 * This class implements Callable<Double> and stores the start and end index per
 * each segment of n.
 * 
 * @author ntrungnghia
 *
 */
public class PiTask implements Callable<Double> {

	
	// Begin index
	final private long start;

	// End index
	final private long end;

	final private Formula formula;

	public PiTask(long start, long end, Formula formula)
			throws PiAppBaseException {

		if (formula == null) {
			throw new PiAppBaseException("Formula type cannot be null.");
		} else {
			this.start = start;
			this.end = end;
			this.formula = formula;
		}
	}

	@Override
	public Double call() throws PiAppBaseException {
		return this.formula.calculate(this.start, this.end);
	}

}
