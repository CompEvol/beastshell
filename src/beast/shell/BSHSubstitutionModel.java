package beast.shell;

import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.evolution.datatype.DataType;
import beast.evolution.substitutionmodel.EigenDecomposition;
import beast.evolution.substitutionmodel.SubstitutionModel;
import beast.evolution.substitutionmodel.SubstitutionModel.Base;
import beast.evolution.tree.Node;
import bsh.EvalError;
import bsh.Interpreter;


@Description("Substitution model specified using BEASTScript "+
		"getTransitionProbabilities(node, fStartTime, fEndTime, fRate, matrix) must be specified. ")
public class BSHSubstitutionModel extends SubstitutionModel.Base {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the substitution model", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a substitution model"); 

	Interpreter interpreter;

	@Override
	public void initAndValidate() throws Exception {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		interpreter.eval(script);
	}

	@Override
	public void getTransitionProbabilities(Node node, double fStartTime, double fEndTime, double fRate, double[] matrix) {
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "getTransitionProbabilities", node, fStartTime, fEndTime, fRate, matrix);
	}

	@Override
	public EigenDecomposition getEigenDecomposition(Node node) {
		return null;
	}

	@Override
	public boolean canHandleDataType(DataType dataType) {
		return true;
	}

}
