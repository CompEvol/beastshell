package test.beast.shell;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import beast.app.beauti.BeautiDoc;
import bsh.Interpreter;

import java.io.File;
import java.io.InputStream;

/**
 * based on bsh.OldScriptsTest.java
 */
public class ShellTest {

	// create testsuite and add test(s) to a suite
	static public junit.framework.TestSuite addTests(File base) {
		final TestSuite suite = new TestSuite();
		return addTests(base, suite);
	}
	
	
	// create testsuite and add test(s) to a suite
	static public junit.framework.TestSuite addTests(String fileName) {
		final TestSuite suite = new TestSuite();
		return addTests(new File(fileName), suite);
	}

	// create testsuite and add test(s) to a suite
	static public junit.framework.TestSuite addTests(String fileName, TestSuite suite) {
		return addTests(new File(fileName), suite);
	}

	// add test(s) to a suite
	static public junit.framework.TestSuite addTests(File base, TestSuite suite) {
		if (base.isFile()) {
			suite.addTest(new TestBshScript(base));
			return suite;
		}
		final File[] files = base.listFiles();
		if (files != null) {
			for (final File file : files) {
				final String name = file.getName();
				if (file.isFile() && name.endsWith(".bsh") && ! "TestHarness.bsh".equals(name) && ! "RunAllTests.bsh".equals(name) && ! "Fail.bsh".equals(name)) {
					suite.addTest(new TestBshScript(file));
				}
			}
		}
		return suite;
	}
	


	static class TestBshScript extends TestCase {

		private File _file;

		public TestBshScript(final File file) {
			_file = file;
		}


		@Override
		public String getName() {
			return _file.getName();
		}


		@Override
		public void runTest() throws Exception {
			System.out.println("file is " + _file.getAbsolutePath());
			final bsh.Interpreter interpreter = new bsh.Interpreter();
			
			InputStream in = Interpreter.class.getResourceAsStream("/test/beast/shell/TestHarness.bsh");
    		if (in == null) {
    			throw new RuntimeException("Cannot find test.beast.shell.TestHarness.bsh");
    		}
			StringBuffer buf = new StringBuffer();
			char ch = ' ';
			while (in.available() > 0) {
				ch = (char) in.read();
				buf.append(ch);
			}
			interpreter.eval(buf.toString());
			final String path = '\"' + _file.getParentFile().getAbsolutePath().replace('\\', '/') + '\"';
			interpreter.eval("path=" + path + ';');
			interpreter.eval("cd(" + path + ");");
			String testScript = BeautiDoc.load(_file);
			testScript += ";complete();";
			interpreter.eval(testScript);
			assertEquals("'test_completed' flag check", Boolean.TRUE, interpreter.get("test_completed"));
			assertEquals("'test_failed' flag check", Boolean.FALSE, interpreter.get("test_failed"));
		}

	}
}
