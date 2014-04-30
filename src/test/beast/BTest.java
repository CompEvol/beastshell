package test.beast;

public class BTest {
	
	public static void assertEquals(Object expected, Object actual) throws Exception {
		if (actual instanceof Double && Double.isNaN((Double) actual)) {
			if (!Double.isNaN((Double) expected)) {
				throw new Exception("Expected " + expected + " but got " + actual);
			}
			return;
		}
		if (!expected.equals(actual)) {
			throw new Exception("Expected " + expected + " but got " + actual);
		}
	}

	public static void assertEquals(double expected, double actual) throws Exception {
		if (Double.isNaN(actual)) {
			if ( !Double.isNaN(expected)) {
				throw new Exception("Expected " + expected + " but got " + actual);
			}
			return;
		}
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
		if (Double.isNaN(actual)) {
			if ( !Double.isNaN(expected)) {
				throw new Exception("Expected " + expected + " but got " + actual);
			}
			return;
		}
		if (Math.abs(expected - actual) > tolerance) {
			throw new Exception("Expected " + expected + " but got " + actual);
		}
	}

}
