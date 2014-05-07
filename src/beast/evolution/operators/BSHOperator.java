package beast.evolution.operators;


import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.NamedFunction;
import beast.core.Operator;
import bsh.Interpreter;

@Description("Operator specified using BEASTScript " +
		"double proposal() must be specified.")
public class BSHOperator extends Operator {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the operator", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for an operator"); 

	Interpreter interpreter;
	
	@Override
	public void initAndValidate() throws Exception {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		interpreter.eval(script);
	}

	@Override
	public double proposal() {
		return NamedFunction.evalFunction(interpreter, functionInputs.get(), "proposal");
	}

}
