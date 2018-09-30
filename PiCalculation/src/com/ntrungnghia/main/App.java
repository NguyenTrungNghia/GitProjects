package com.ntrungnghia.main;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.ntrungnghia.calculator.Calculator;
import com.ntrungnghia.calculator.CalculatorImpl;
import com.ntrungnghia.exception.PiAppBaseException;
import com.ntrungnghia.formula.Formula;
import com.ntrungnghia.formula.FormulaFactory;
import com.ntrungnghia.formula.FormulaFactory.FormulaType;
import com.ntrungnghia.model.PiResult;
import com.ntrungnghia.service.PiCalculatingService;
import com.ntrungnghia.service.PiCalculatingServiceImpl;

public class App {

	private static final String RESULT = "Result : ";
	private static final String EXECUTING_TIME = "Executing time : ";
	private static final String STOP_POSITION = "Stopping position : ";
	private static final String STATE = "Calculating... ";

	private int numOfThreads;
	private static Scanner input;
	private PiResult pi;
	private PiCalculatingService calculatorService;
	private Formula formula;

	public App() {
		pi = new PiResult();
		numOfThreads = Runtime.getRuntime().availableProcessors();
	}
	
	public static void main(String[] args) {
		String flag = "";
		do {
			App m = new App();
			m.run();
			System.out.println("Do you want to exit the program ? (Y/N)");
			flag = App.input.nextLine();
			if (flag.equalsIgnoreCase("y")) {
				break;
			}
		} while (true);
	}

	public void run() {

		int stop;
		final Calculator calculator = new CalculatorImpl();
		this.calculatorService = new PiCalculatingServiceImpl(calculator);
		App.input = new Scanner(System.in);

		try {

			System.out.println("Pi calculation");
			System.out.println("1. Leibniz formula.");
			System.out.println("2. Nilakantha formula.");
			System.out.println("Please select an option.");

			int c = Integer.parseInt(input.nextLine());

			switch (c) {
			case 1:
				System.out.println("\nLeibniz formula is selected");
				this.formula = FormulaFactory.getFormula(FormulaType.LEIBNIZ);
				break;

			case 2:
				System.out.println("\nNilakantha formula is selected");
				this.formula = FormulaFactory
						.getFormula(FormulaType.NILAKANTHA);
				break;
			default:
				throw new PiAppBaseException(
						"Invalid option, please try again.");
			}

			System.out.print("Input a positive integer : ");
			final long n = Long.parseLong(App.input.nextLine());
			System.out.println("Number of threads (recommend "
					+ this.numOfThreads + " "
					+ (this.numOfThreads > 1 ? "threads" : "thread") + ") : ");
			this.numOfThreads = Integer.parseInt(App.input.nextLine());
			if (this.numOfThreads > 0) {
				calculator.setNumOfThreads(this.numOfThreads);
			}
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						App.this.pi = App.this.calculatorService.calculate(
								App.this.formula, n);
					} catch (PiAppBaseException e) {
						System.err.println(e.getMessage());
					} catch (InterruptedException | ExecutionException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println("\nDone");
					printPi(calculator);
				}
			});
			t.start();

			System.out.println(App.STATE);
			System.out.println("1. Stop the calculation.");
			System.out.println("2. Exit.");
			System.out.println("Please select an option.");
			stop = Integer.parseInt(input.nextLine());
			switch (stop) {
			case 1:
				this.calculatorService.stop();
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

		} catch (NumberFormatException e) {
			System.err.println("Invalid input, please try again.");
		} catch (PiAppBaseException ex) {
			System.err.println(ex.getMessage());
		}

	}

	private void printPi(Calculator calculator) {
		if (calculator != null && this.calculatorService != null) {
			System.out.println(App.RESULT + this.pi.getResult());
			System.out.println(App.EXECUTING_TIME
					+ this.pi.getExecutingTime() + " ms");
			System.out.println(App.STOP_POSITION
					+ calculator.getCurrentPosition());
			System.out.println();
		}
	}
}
