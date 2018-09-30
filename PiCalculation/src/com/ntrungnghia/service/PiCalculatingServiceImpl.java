package com.ntrungnghia.service;

import java.util.concurrent.ExecutionException;

import com.ntrungnghia.calculator.Calculator;
import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;
import com.ntrungnghia.model.PiResult;

public class PiCalculatingServiceImpl implements PiCalculatingService {

	
	private Calculator calculator;

	public PiCalculatingServiceImpl(Calculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public PiResult calculate(Formula formula, long n)
			throws PiAppBaseException, InterruptedException,
			ExecutionException {
		return this.calculator.execute(formula, n);
	}

	@Override
	public void stop() {
	    this.calculator.stop();
	}

}
