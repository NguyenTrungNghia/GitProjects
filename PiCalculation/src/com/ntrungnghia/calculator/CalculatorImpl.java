package com.ntrungnghia.calculator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;
import com.ntrungnghia.model.PiResult;
import com.ntrungnghia.tasks.PiTask;

public class CalculatorImpl extends Calculator {

    // Number of jobs per task
    public static final long JOB_PER_TASK = 100000;

    public CalculatorImpl() {
        pi = new PiResult();
    }

    /**
     * Returns the stop position if the executing process has been stopped by
     * clients.
     */
    @Override
    public long getCurrentPosition() {
        long stop = this.currentPosition * CalculatorImpl.JOB_PER_TASK;
        return (stop > this.serial ? this.serial : stop);
    }

    @Override
    public PiResult execute(Formula formula, long n)
            throws PiAppBaseException, InterruptedException,
            ExecutionException {

        if (formula == null) {
             throw new PiAppBaseException("Formula type cannot be null ");
        }
        
        Long start;
        Long end;
        PiTask task = null;
        this.serial = n;
        this.currentPosition = 0;
        this.stop = false;
        pi = new PiResult();
        ExecutorService executor = Executors.newFixedThreadPool(this.numOfThreads);
        List<Future<Double>> futures = new LinkedList<>();

        // Calculates number of tasks according to n.
        long numOfTask = this.getNumOfTask(n);

        // Submits PiTask to executor.
        for (long i = 0; i < numOfTask; i++) {

            // Cumulative PI when the Future list has more than 100 elements.
            if (futures.size() >= 100) {
                this.sumFuturesList(this.pi, futures);
            }

            // Checks the stop flag.
            if (this.stop) {
                break;
            } else {

                // Calculates the begin and end index of each task.
                start = i * CalculatorImpl.JOB_PER_TASK;
                end = this.getEndIndex(start, n);
                task = new PiTask(start, end, formula);

                // Submits a task to executor and add to Future list.
                futures.add(executor.submit(task));
            }
        }

        // Shut down executor and calculates pi for the last time and return
        // the final pi number.
        this.finish(executor);
        this.sumFuturesList(this.pi, futures);
        this.pi.setLastTime(System.currentTimeMillis());
        return this.pi;
    }

    /**
     * Calculates and returns number of tasks can be executed.
     * 
     * @param n
     * @return
     * @throws PiAppBaseException
     */
    private long getNumOfTask(long n) throws PiAppBaseException {
        this.isLegal(n);
        return n / CalculatorImpl.JOB_PER_TASK + 1;
    }

    private boolean isLegal(long n) throws PiAppBaseException {
        if (n < 0 || n > Long.MAX_VALUE) {
            throw new PiAppBaseException("Invalid serial, it must be greater than 0.");
        } else {
            return true;
        }
    }

    /**
     * Returns {@link end} index of a segment according to {@link start} and n.
     * 
     * @param start
     *            start index a segment.
     * @param n
     * @return end index.
     * @throws PiAppBaseException
     */
    private long getEndIndex(long start, long n) throws PiAppBaseException {
        this.isLegal(start);
        long end = start + CalculatorImpl.JOB_PER_TASK - 1;

        return (end > n ? n : end);
    }

    /**
     * Checks whether each task element in {@link futures} is done, it will be
     * sum its result to the current pi number and remove from {@link futures}.
     * 
     * @param pi
     *            current pi number.
     * @param futures
     *            list of Future<Double>.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void sumFuturesList(final PiResult pi,
            final List<Future<Double>> futures) throws InterruptedException,
            ExecutionException {

        Runnable sumTask = new Runnable() {

            @Override
            public void run() {
                try {
                	
                	pi.increasePI(calculatePI());
                	
                } catch (InterruptedException | ExecutionException e) {

                    // Stops pi calculation
                    CalculatorImpl.this.stop = true;
                    Thread.currentThread().interrupt();
                }
            }

			private double calculatePI() throws InterruptedException,
                    ExecutionException {
                double r = 0.0;
                List<Future<Double>> removedElements = new LinkedList<>();
                for (Future<Double> f : futures) {
                    if (f.isDone()) {
                        r += f.get();
                        removedElements.add(f);
                        CalculatorImpl.this.currentPosition++;
                    } else {
                        break;
                    }
                }
                futures.removeAll(removedElements);

                return r;
            }
        };
        Thread t = new Thread(sumTask);
        t.start();
        t.join();
    }

    /**
     * Shuts the executor down and blocks executor until all tasks have
     * completed execution.
     * 
     * @param executor
     */
    private void finish(ExecutorService executor) {
        if (executor != null) {
            try {
                executor.shutdown();
                executor.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
