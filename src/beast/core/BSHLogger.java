package beast.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import bsh.EvalError;
import bsh.Interpreter;

@Description("Logger specified using BEASTScript "+
		"init(out), log(sample, out), close(out) must be specified. ")
public class BSHLogger extends BEASTObject implements Loggable {
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
	
	@Override
	public void init(PrintStream out) throws Exception {
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		setOut(out);
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "init");

	}

	private void setOut(PrintStream out) {
		try {
			interpreter.set("out", out);
		} catch (EvalError e) {
			e.printStackTrace();
			throw new RuntimeException("setOut failed: " + e.getMessage());
		}
	}

	@Override
	public void log(int nSample, PrintStream out) {
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		setOut(out);
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "log", nSample);
	}

	@Override
	public void close(PrintStream out) {
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		setOut(out);
		NamedFunction.evalVoidFunction(interpreter, functionInputs.get(), "close");
	}

}
