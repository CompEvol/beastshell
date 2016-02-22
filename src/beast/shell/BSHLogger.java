package beast.shell;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.Loggable;
import bsh.EvalError;
import bsh.Interpreter;

@Description("Logger specified using BEASTScript "+
		"init(out), log(sample, out), close(out) must be specified. ")
public class BSHLogger extends BEASTObject implements Loggable {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the distirbution", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a distirbution"); 

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
	public void init(PrintStream out) {
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "init", out);

	}

	@Override
	public void log(int nSample, PrintStream out) {
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "log", nSample, out);
	}

	@Override
	public void close(PrintStream out) {
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "close", out);
	}

}
