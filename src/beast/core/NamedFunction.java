package beast.core;

import java.util.List;

import beast.core.Input.Validate;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.Primitive;
import bsh.This;

@Description("Wraps a function in a name, so it can be identified in a BEASTShell script object")
public class NamedFunction extends CalculationNode implements Function {
	public Input<String> nameInput = new Input<String>("term","name used to identify this function", Validate.REQUIRED);
	public Input<Function> functionInput = new Input<Function>("function" , "function identified by this term");

	Function f;
	@Override
	public void initAndValidate() throws Exception {
		f = functionInput.get();
	}
	
	@Override
	public int getDimension() {
		return f.getDimension();
	}

	@Override
	public double getArrayValue() {
		return f.getArrayValue();
	}

	@Override
	public double getArrayValue(int iDim) {
		return f.getArrayValue(iDim);
	}
	
	public String getName() {
		return nameInput.get();
	}

	public Object getFunction() {
		return f;
	}

	public static void evalFunctionInputs(Interpreter interpreter, List<Function> functions) {
		try {
			for (Function function : functions) {
				String id = ((BEASTObject) function).getID();
				if (function instanceof NamedFunction) {
					id = ((NamedFunction)function).getName();
					interpreter.set(id, ((NamedFunction)function).getFunction());
				} else {
					interpreter.set(id, function);
				}
			}
		} catch (EvalError e) {
			throw new RuntimeException("Error in BSHOperator: " + e.getMessage());
		}
	}

	
//	public static double evalFunction(Interpreter interpreter, List<Function> functions, String name, double arg) {
//		NamedFunction.evalFunctionInputs(interpreter, functions);
//		try {
//			double result = - 1;
//			Object o = interpreter.eval(name + "(" + arg + ")");
//			if (o instanceof Number) {
//				result = ((Number) o).doubleValue();
//			} else {
//				throw new RuntimeException(name + " failed: expected a number as result");
//			}
//			return result;
//		} catch (EvalError e) {
//			throw new RuntimeException(name + " failed: " + e.getMessage());
//		}
//	}

	public static double evalFunction(Interpreter interpreter, List<Function> functions, String name, Object... args) {
		NamedFunction.evalFunctionInputs(interpreter, functions);
		try {
			double result = - 1;
			This _this = interpreter.getNameSpace().getThis(interpreter);
			Object o = _this.invokeMethod(name, args);
			if (o instanceof Primitive) {
				o = ((Primitive)o).getValue();
			}
			if (o instanceof Number) {
				result = ((Number) o).doubleValue();
			} else {
				throw new RuntimeException(name + " failed: expected a number as result");
			}
			return result;
		} catch (EvalError e) {
			throw new RuntimeException(name + " failed: " + e.getMessage());
		}

	}

//	public static void evalVoidFunction(Interpreter interpreter, List<Function> functions, String name, double arg) {
//		NamedFunction.evalFunctionInputs(interpreter, functions);
//		try {
//			interpreter.eval(name + "(" + arg + ")");
//		} catch (EvalError e) {
//			throw new RuntimeException(name + " failed: " + e.getMessage());
//		}
//	}

	public static void evalVoidFunction(Interpreter interpreter, List<Function> functions, String name, Object... args) {
		NamedFunction.evalFunctionInputs(interpreter, functions);
		try {
			This _this = interpreter.getNameSpace().getThis(interpreter);
			_this.invokeMethod(name, args);
		} catch (EvalError e) {
			throw new RuntimeException(name + " failed: " + e.getMessage());
		}

	}
}
