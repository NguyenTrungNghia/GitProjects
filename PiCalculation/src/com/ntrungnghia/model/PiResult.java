package com.ntrungnghia.model;

/**
 * This class is stored the pi number, the creating time and the latest time
 * update of pi.
 * 
 * @author ntrungnghia
 *
 */
public class PiResult {

	private double result = 0.0;
	private long beginTime = System.currentTimeMillis();
	private long lastTime = 0;

	public double getResult() {
		return this.result;
	}
	
	/**
	 * Adds an element into current pi number.
	 * @param element
	 */
	public synchronized void increasePI(double element) {
		this.result += element;
	}

	public long getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(long b) {
		this.beginTime = b;
	}

	/**
	 * Returns the executing time when its instance has the final result.
	 * 
	 * @return {@link result}
	 */
	public long getExecutingTime() {
		long r = this.lastTime - this.beginTime;
		return r >= 0 ? r : 0;
	}

	public long getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

}
