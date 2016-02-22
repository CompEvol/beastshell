package beast.shell;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;

import bsh.EvalError;
import bsh.Interpreter;
import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.math.distributions.ParametricDistribution;

@Description("Parmateric distribution (continuous, not integer) specified using BEASTScript "+
		"desnity(x), cumulativeProbability(x) and inverseCumulativeProbability(p) must be specified. ")
public class BSHParametricDistribution extends ParametricDistribution {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the parametric distribution", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a parametric distribution"); 

	Interpreter interpreter;
	Distribution dist;
	
	@Override
	public void initAndValidate() {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		try {
			interpreter.eval(script);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}

		dist = new ContinuousDistribution() {
			@Override
			public double logDensity(double x) {
				return Math.log(density(x));
			}
			
			@Override
			public double inverseCumulativeProbability(double p) throws MathException {
				return NamedFunction.evalFunction(interpreter, functionInputs.get(), "inverseCumulativeProbability", p);
			}
			
			@Override
			public double density(double x) {
				return NamedFunction.evalFunction(interpreter, functionInputs.get(), "density", x);
			}

			@Override
			public double cumulativeProbability(double x) throws MathException {
				return NamedFunction.evalFunction(interpreter, functionInputs.get(), "cumulativeProbability", x);
			}
			
			@Override
			public double cumulativeProbability(double x0, double x1) throws MathException {
				return cumulativeProbability(x1) - cumulativeProbability(x0);
			}
		};
	}

	
	@Override
	public org.apache.commons.math.distribution.Distribution getDistribution() {
		return dist;
	}
	
}
