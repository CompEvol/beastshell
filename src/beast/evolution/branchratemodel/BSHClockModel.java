package beast.evolution.branchratemodel;

import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.NamedFunction;
import beast.evolution.branchratemodel.BranchRateModel.Base;
import beast.evolution.tree.Node;
import bsh.EvalError;
import bsh.Interpreter;

@Description("Branch rate model specified using BEASTScript "+
		"getRateForBranch() must be specified, node and meanrate are globals. ")
public class BSHClockModel extends Base {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the clock model", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for an clock model"); 

	Interpreter interpreter;

	@Override
	public void initAndValidate() throws Exception {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		interpreter.eval(script);
	}

	@Override
	public double getRateForBranch(Node node) {
		try {
			interpreter.set("node", node);
			interpreter.set("meanrate", meanRateInput.get().getValue());
			return NamedFunction.evalFunction(interpreter, functionInputs.get(), "getRateForBranch");
		} catch (EvalError e) {
			throw new RuntimeException("getProportionForCategory failed: " + e.getMessage());
		}
	}

}
