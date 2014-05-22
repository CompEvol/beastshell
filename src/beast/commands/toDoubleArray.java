package beast.commands;

import java.util.Collection;
import java.util.List;

import bsh.CallStack;
import bsh.Interpreter;

public class toDoubleArray {
	public static double [] invoke(Interpreter env, CallStack callstack) {
		return new double[0];
	}

	/**
	 * Implement toDoubleArray(double[]) command.
	 */
	public static double [] invoke(Interpreter env, CallStack callstack, double [] x) {
		return x;
	}

	public static double [] invoke(Interpreter env, CallStack callstack, int [] x) {
		double [] d = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			d[i] = x[i];
		}
		return d;
	}

	public static double [] invoke(Interpreter env, CallStack callstack, Number [] x) {
		double [] d = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			d[i] = x[i].doubleValue();
		}
		return d;
	}

	public static double [] invoke(Interpreter env, CallStack callstack, Collection<Number> x) {
		double [] d = new double[x.size()];
		int i = 0;
		for (Number value : x) {
			d[i++] = value.doubleValue();
		}
		return d;
	}

}
