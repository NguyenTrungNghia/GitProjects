package com.ntrungnghia.service;

import java.util.concurrent.ExecutionException;

import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;
import com.ntrungnghia.model.PiResult;

public interface PiCalculatingService {

	
	/**
	 * Calculates and returns pi number that has been calculated according to
	 * the serial. While calculating the pi, if the state of pi calculation is
	 * false is will stop and return the current pi number.
	 * 
	 * @param n
	 *            a serial
	 * @return PiResult has been calculated by n.
	 * @throws PiAppBaseException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * 
	 */
	PiResult calculate(Formula formula, long n) throws PiAppBaseException,
			InterruptedException, ExecutionException;

	/**
	 * Stops the π calculation.
	 */
	void stop();

}
