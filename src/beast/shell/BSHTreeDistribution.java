package beast.shell;

import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.evolution.tree.TreeDistribution;
import bsh.EvalError;
import bsh.Interpreter;

@Description("Substitution model specified using BEASTScript "+
		"calculateLogP(tree, intervals) must be specified")
public class BSHTreeDistribution extends TreeDistribution {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the tree distribution", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a tree distribution"); 

	Interpreter interpreter;

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
	}

	@Override
	public double calculateLogP() {
		logP = 0;
		logP = NamedFunction.evalFunction(interpreter, functionInputs.get(), "calculateLogP", treeInput.get(), treeIntervalsInput.get());
		return logP;
	}
	
	@Override
	protected boolean requiresRecalculation() {
		return true;
	}
}
