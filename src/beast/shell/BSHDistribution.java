package beast.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import beast.core.Description;
import beast.core.Distribution;
import beast.core.Function;
import beast.core.Input;
import beast.core.State;
import bsh.Interpreter;

@Description("Distribution specified using BEASTScript "+
		"calculateLogP() must be specified. ")
public class BSHDistribution extends Distribution {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the distirbution", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a distirbution"); 

	Interpreter interpreter;

	@Override
	public void initAndValidate() throws Exception {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		interpreter.eval(script);
	}
	
    public double calculateLogP() throws Exception {
        logP = 0;
        logP = NamedFunction.evalFunction(interpreter, functionInputs.get(), "calculateLogP");
        return logP;
    }

	
	@Override
	public List<String> getArguments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getConditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sample(State state, Random random) {
		// TODO Auto-generated method stub

	}

}
