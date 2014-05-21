package test.beast.shell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;

import junit.framework.TestCase;

public class UniformTest extends TestCase {

	@Test
	public void testUniform() throws FileNotFoundException, EvalError {
		
		Interpreter interpreter = new Interpreter();
		File bshfile = new File("src/test/beast/shell/uniform.bsh");
		interpreter.eval(new FileReader(bshfile));
	}		
	@Test
	public void testFail() throws FileNotFoundException, EvalError {
		try {
			Interpreter interpreter = new Interpreter();
			File bshfile = new File("src/test/beast/shell/Fail.bsh");
			interpreter.eval(new FileReader(bshfile));
			assert(false); // should not get here
		} catch (Exception e) {
			// should be ok
		}
	}
}
