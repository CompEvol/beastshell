package beast.shell;

import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.evolution.branchratemodel.BranchRateModel;
import beast.evolution.branchratemodel.BranchRateModel.Base;
import beast.evolution.tree.Node;
import bsh.EvalError;
import bsh.Interpreter;

@Description("Branch rate model specified using BEASTScript "+
		"getRateForBranch(node, meanrate) must be specified. ")
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
		return NamedFunction.evalFunction(interpreter, functionInputs.get(), "getRateForBranch", node, meanRateInput.get().getValue());
	}

}
