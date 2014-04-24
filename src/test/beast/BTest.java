package test.beast;

public class BTest {
	
	public static void assertEquals(Object expected, Object actual) throws Exception {
		if (!expected.equals(actual)) {
			throw new Exception("Expected " + expected + " but got " + actual);
		}
	}

	public static void assertEquals(double expected, double actual) throws Exception {
		if (expected != actual) {
			throw new Exception("Expected " + expected + " but got " + actual);
		}
	}

	public static void assertEquals(Object expected, Object actual, Object msg) throws Exception {
		if (!expected.equals(actual)) {
			throw new Exception("Expected " + expected + " but got " + actual + " -- " + msg);
		}
	}

	public static void assertEquals(double expected, double actual, double tolerance) throws Exception {
		if (Math.abs(expected - actual) > tolerance) {
			throw new Exception("Expected " + expected + " but got " + actual);
		}
	}

}
