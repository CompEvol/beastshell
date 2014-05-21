package test.beast.shell;

import java.io.File;

import org.junit.Assert;

public class FailTest extends ShellTest {

	public static junit.framework.Test suite() throws Exception {
		try {
			new TestBshScript(new File("src/test/beast/shell/Fail.bsh")).runTest();
			Assert.fail("Fail.bsh should fail!");
		} catch (final AssertionError e) {
			// expected
		}
		return addTests("src/test/beast/shell/uniform.bsh");
	}
}
