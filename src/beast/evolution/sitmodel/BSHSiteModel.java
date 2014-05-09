package beast.evolution.sitmodel;


import java.util.ArrayList;
import java.util.List;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.NamedFunction;
import beast.evolution.sitemodel.SiteModelInterface;
import beast.evolution.tree.Node;
import bsh.EvalError;
import bsh.Interpreter;

@Description("Sitemodel specified using BEASTScript "+
		"getCategoryCount(), getCategoryOfSite(site, node), getRateForCategory(category, node), getCategoryRates(node), getProportionForCategory(category, node), getCategoryProportions(node)  must be specified. ")
public class BSHSiteModel extends SiteModelInterface.Base {
	public Input<List<Function>> functionInputs = new Input<List<Function>>("x", "function values used by the site model", new ArrayList<Function>()); 
	public Input<String> valueInput = new Input<String>("value", "script specifying the various functions required for a site model"); 

	Interpreter interpreter;

	@Override
	public void initAndValidate() throws Exception {
		interpreter = new Interpreter();
		NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
		String script = valueInput.get();
		interpreter.eval(script);
	}

	@Override
	public boolean integrateAcrossCategories() {
		return false;
	}

	@Override
	public int getCategoryCount() {
		return (int) NamedFunction.evalFunction(interpreter, functionInputs.get(), "getCategoryCount");
	}

	@Override
	public int getCategoryOfSite(int site, Node node) {
		return (int) NamedFunction.evalFunction(interpreter, functionInputs.get(), "getCategoryOfSite", site, node);
	}

	@Override
	public double getRateForCategory(int category, Node node) {
		return NamedFunction.evalFunction(interpreter, functionInputs.get(), "getRateForCategory", category, node);
	}

	@Override
	public double[] getCategoryRates(Node node) {
		return call(node, "getRateForCategory");
	}
	
	@Override
	public double getProportionForCategory(int category, Node node) {
		return NamedFunction.evalFunction(interpreter, functionInputs.get(), "getProportionForCategory", category, node);
	}

	@Override
	public double[] getCategoryProportions(Node node) {
		return call(node, "getCategoryProportions");
	}

	double[] call(Node node, String method) {
		try {
			interpreter.set("node", node);
			NamedFunction.evalFunctionInputs(interpreter, functionInputs.get());
			Object o = interpreter.eval(method + "()");
			if (o.getClass().isArray()) {
				return (double[]) o;
			} else if (o instanceof List) {
				double [] result = new double[((List)o).size()];
				for (int i = 0; i < result.length; i++) {
					result[i] = ((Number)((List)o).get(i)).doubleValue();
				}
				return result;
			} else {
				throw new RuntimeException(method + " failed: expected a number as result");
			}
		} catch (EvalError e) {
			throw new RuntimeException(method + " failed: " + e.getMessage());
		}
	}
}
