package test.beast.math.distributions;

import java.io.FileReader;

import javax.script.ScriptEngine;

import org.junit.Test;

import bsh.BshScriptEngineFactory;
import junit.framework.TestCase;

public class DistributionsTest extends TestCase {
	

    @Test
	public void testMean() throws Exception {
        ScriptEngine engine = new BshScriptEngineFactory().getScriptEngine();
        engine.eval(new FileReader("test/beast/math/distributions/testDistributions.bsh"));
    }


}
