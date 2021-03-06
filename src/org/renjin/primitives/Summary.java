/*
 * R : A Computer Language for Statistical Data Analysis
 * Copyright (C) 1995, 1996  Robert Gentleman and Ross Ihaka
 * Copyright (C) 1997--2008  The R Development Core Team
 * Copyright (C) 2003, 2004  The R Foundation
 * Copyright (C) 2010 bedatadriven
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.renjin.primitives;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Summary group functions of vectors such as min, max, sum, etc.
 */
public class Summary {
	
	private static List rmNaN(Collection<Number> obs) {
		List<Number> list = new ArrayList<>();
		for (Number o : obs) {
			if (!Double.isNaN(o.doubleValue())) {
				list.add(o);
			}
		}
		return list;
	}
	private static List rmNaN(Number[] obs) {
		List<Number> list = new ArrayList<>();
		for (Number o : obs) {
			if (!Double.isNaN(o.doubleValue())) {
				list.add(o);
			}
		}
		return list;
	}
	private static List rmBNaN(List<Boolean> obs) {
		List<Boolean> list = new ArrayList<>();
		for (Boolean o : obs) {
			if (o != null) {
				list.add(o);
			}
		}
		return list;
	}
	private static int[] rmNaN(int[] x) {
		return x;
	}
	private static double[] rmNaN(double[] x) {
		List<Number> list = new ArrayList<>();
		for (Number o : x) {
			if (!Double.isNaN(o.doubleValue())) {
				list.add(o);
			}
		}
		Double [] y = list.toArray(new Double[]{});
		double [] result = new double[y.length];
		for (int i = 0; i  < y.length; i++) {
			result[i] = y[i];
		}
		return result;
	}

	
	
	static public Number sum(Collection<Number> obs, boolean na_rm) {
		if (na_rm) {
			return sum(rmNaN(obs), false);
		}
		double sum = 0;for (Number o : obs){sum += o.doubleValue();}return sum;
	};
	static public int sum(int[]x, boolean na_rm) {
		if (na_rm) {
			return sum(rmNaN(x), false);
		}
		int sum = 0; for (int i : x) sum +=i; return sum;
	}
	static public double sum(double[]x, boolean na_rm) {
		if (na_rm) {
			return sum(rmNaN(x), false);
		}
		double sum = 0; for (double i : x) sum +=i; return sum;
	}
	static public double sum(Number[]x, boolean na_rm) {
		if (na_rm) {
			return sum(rmNaN(x), false).doubleValue();
		}
		double sum = 0; for (Number i : x) sum +=i.doubleValue(); return sum;
	}

	
	static public double mean(Collection<Number> obs, boolean na_rm) {
		if (na_rm) {
			return mean(rmNaN(obs), false);
		}
		double sum = 0;for (Number o : obs){sum += o.doubleValue();}return sum/obs.size();
		};
	static public double mean(int[] x, boolean na_rm) {
		if (na_rm) {
			return mean(rmNaN(x), false);
		}
		int sum = 0; for (int i : x) sum +=i; return sum/x.length;
		};
	static public double mean(double[]x, boolean na_rm) {
		if (na_rm) {
			return mean(rmNaN(x), false);
		}
		double sum = 0; for (double i : x) sum +=i; return sum/x.length;
		};
	static public double mean(Number[]x, boolean na_rm) {
		if (na_rm) {
			return mean(rmNaN(x), false);
		}
		double sum = 0; for (Number i : x) sum +=i.doubleValue(); return sum/x.length;
		};
	
	static public double min(Collection obs, boolean na_rm) {
		if (na_rm) {
			return min(rmNaN(obs), false);
		}
		return ((Number) Collections.min(obs)).doubleValue();
		};
	static public int min(int[]x, boolean na_rm) {
		if (na_rm) {
			return min(rmNaN(x), false);
		}
		int min = x[0];for (int i : x) {min = Math.min(min, i);}return min;
		};
	static public double min(double[]x, boolean na_rm) {
		if (na_rm) {
			return min(rmNaN(x), false);
		}
		double min = x[0];for (double i : x) {min = Math.min(min, i);}return min;
		};
	static public double min(Number[]x, boolean na_rm) {
		if (na_rm) {
			return min(rmNaN(x), false);
		}
		double min = x[0].doubleValue();for (Number i : x) {min = Math.min(min, i.doubleValue());}return min;
		};
	
	static public double max(Collection obs, boolean na_rm) {
		if (na_rm) {
			return max(rmNaN(obs), false);
		}
		return ((Number) Collections.max(obs)).doubleValue();
		};
	static public int max(int[]x, boolean na_rm) {
		if (na_rm) {
			return max(rmNaN(x), false);
		}
		int max = x[0];for (int i : x) {max = Math.max(max, i);}return max;
		};
	static public double max(double[]x, boolean na_rm) {
		if (na_rm) {
			return max(rmNaN(x), false);
		}
		double max = x[0];for (double i : x) {max = Math.max(max, i);}return max;
		};
	static public double max(Number[]x, boolean na_rm) {
		if (na_rm) {
			return max(rmNaN(x), false);
		}
		Number max = x[0];for (Number i : x) {max = Math.max(max.doubleValue(), i.doubleValue());}return max.doubleValue();
	};
	
	static public Number prod(Collection<Number> obs, boolean na_rm) {
		if (na_rm) {
			return prod(rmNaN(obs), false);
		}
		double prod = 1;for (Number o : obs){prod *= o.doubleValue();}return prod;
		};
	static public int prod(int[]x, boolean na_rm) {
		if (na_rm) {
			return prod(rmNaN(x), false);
		}
		int prod = 1; for (int i : x) prod *=i; return prod;
		};
	static public double prod(double[]x, boolean na_rm) {
		if (na_rm) {
			return prod(rmNaN(x), false);
		}
		double prod = 1; for (double i : x) prod *=i; return prod;
		};
	static public double prod(Number[]x, boolean na_rm) {
		if (na_rm) {
			return prod(rmNaN(x), false).doubleValue();
		}
		double prod = 1; for (Number i : x) prod *=i.doubleValue(); return prod;
		};
	
	static public List<Number> range(Collection<Number> obs, boolean na_rm) {
		if (na_rm) {
			return range(rmNaN(obs), false);
		}
		List<Number> result = new ArrayList<>();
		result.add(min(obs, false));
		result.add(max(obs, false));
		return result;
	};
	static public List<Number> range(int[]x, boolean na_rm) {
		if (na_rm) {
			return range(rmNaN(x), false);
		}
		List<Number> result = new ArrayList<>();
		result.add(min(x, false));
		result.add(max(x, false));
		return result;
	};
	static public List<Number> range(double[]x, boolean na_rm) {
		if (na_rm) {
			return range(rmNaN(x), false);
		}
		List<Number> result = new ArrayList<>();
		result.add(min(x, false));
		result.add(max(x, false));
		return result;
	};
	static public List<Number> range(Number[]x, boolean na_rm) {
		if (na_rm) {
			return range(rmNaN(x), false);
		}
		List<Number> result = new ArrayList<>();
		result.add(min(x, false));
		result.add(max(x, false));
		return result;
	};
	
	
	static public List<Number> cumsum(List<Number> obs, boolean na_rm) {
		if (na_rm) {
			return cumsum(rmNaN(obs), false);
		}
		List<Number> result = new ArrayList<>();
		double sum = 0;
		for (Number o : obs){
			sum += o.doubleValue();
			result.add(sum);
		}
		return result;
	};
	static public int [] cumsum(int [] x, boolean na_rm) {
		if (na_rm) {
			return cumsum(rmNaN(x), false);
		}
		int [] result = new int[x.length];
		int sum = 0;
		int k = 0;
		for (int i : x){
			sum += i;
			result[k++] = sum;
		}
		return result;
	};
	static public double[] cumsum(double [] x, boolean na_rm) {
		if (na_rm) {
			return cumsum(rmNaN(x), false);
		}
		double [] result = new double[x.length];
		double sum = 0;
		int k = 0;
		for (double i : x){
			sum += i;
			result[k++] = sum;
		}
		return result;
	};
	static public Number[] cumsum(Number[] x, boolean na_rm) {
		if (na_rm) {
			return (Number[]) cumsum(rmNaN(x), false).toArray(new Number[0]);
		}
		Double [] result = new Double[x.length];
		double sum = 0;
		int k = 0;
		for (Number i : x){
			sum += i.doubleValue();
			result[k++] = sum;
		}
		return result;
	};

	static public List<Number> cumprod(List<Number> obs, boolean na_rm) {
		if (na_rm) {
			return cumprod(rmNaN(obs), false);
		}
		List<Number> result = new ArrayList<>();
		double prod = 1;
		for (Number o : obs){
			prod *= o.doubleValue();
			result.add(prod);
		}
		return result;
	};
	static public int [] cumprod(int [] x, boolean na_rm) {
		if (na_rm) {
			return cumprod(rmNaN(x), false);
		}
		int [] result = new int[x.length];
		int prod = 0;
		int k = 0;
		for (int i : x){
			prod *= i;
			result[k++] = prod;
		}
		return result;
	};
	static public double[] cumprod(double [] x, boolean na_rm) {
		if (na_rm) {
			return cumprod(rmNaN(x), false);
		}
		double [] result = new double[x.length];
		double prod = 0;
		int k = 0;
		for (double i : x){
			prod *= i;
			result[k++] = prod;
		}
		return result;
	};
	static public Number[] cumprod(Number[] x, boolean na_rm) {
		if (na_rm) {
			return (Number[]) cumprod(rmNaN(x), false).toArray(new Number[0]);
		}
		Double [] result = new Double[x.length];
		double prod = 0;
		int k = 0;
		for (Number i : x){
			prod *= i.doubleValue();
			result[k++] = prod;
		}
		return result;
	};
	
	static public List<Number> cummax(List<Number> obs, boolean na_rm) {
		if (na_rm) {
			return cummax(rmNaN(obs), false);
		}
		List<Number> result = new ArrayList<>();
		double max = obs.get(0).doubleValue();
		for (Number o : obs){
			max = Math.max(max, o.doubleValue());
			result.add(max);
		}
		return result;
	};
	static public int [] cummax(int [] x, boolean na_rm) {
		if (na_rm) {
			return cummax(rmNaN(x), false);
		}
		int [] result = new int[x.length];
		int max = x[0];
		int k = 0;
		for (int i : x){
			max = Math.max(i, max);
			result[k++] = max;
		}
		return result;
	};
	static public double[] cummax(double [] x, boolean na_rm) {
		if (na_rm) {
			return cummax(rmNaN(x), false);
		}
		double [] result = new double[x.length];
		double max = x[0];
		int k = 0;
		for (double i : x){
			max = Math.max(i, max);
			result[k++] = max;
		}
		return result;
	};
	static public Number[] cummax(Number[] x, boolean na_rm) {
		if (na_rm) {
			return (Number[]) cummax(rmNaN(x), false).toArray(new Number[0]);
		}
		Double [] result = new Double[x.length];
		double max = x[0].doubleValue();
		int k = 0;
		for (Number i : x){
			max = Math.max(i.doubleValue(), max);
			result[k++] = max;
		}
		return result;
	};
	
	static public List<Number> cummin(List<Number> obs, boolean na_rm) {
		if (na_rm) {
			return cummin(rmNaN(obs), false);
		}
		List<Number> result = new ArrayList<>();
		double min = obs.get(0).doubleValue();
		for (Number o : obs){
			min = Math.min(min, o.doubleValue());
			result.add(min);
		}
		return result;
	};
	static public int [] cummin(int [] x, boolean na_rm) {
		if (na_rm) {
			return cummin(rmNaN(x), false);
		}
		int [] result = new int[x.length];
		int min = x[0];
		int k = 0;
		for (int i : x){
			min = Math.min(i, min);
			result[k++] = min;
		}
		return result;
	};
	static public double[] cummin(double [] x, boolean na_rm) {
		if (na_rm) {
			return cummin(rmNaN(x), false);
		}
		double [] result = new double[x.length];
		double min = x[0];
		int k = 0;
		for (double i : x){
			min = Math.min(i, min);
			result[k++] = min;
		}
		return result;
	};
	static public Number[] cummin(Number[] x, boolean na_rm) {
		if (na_rm) {
			return (Number[]) cummin(rmNaN(x), false).toArray(new Number[0]);
		}
		Double [] result = new Double[x.length];
		double min = x[0].doubleValue();
		int k = 0;
		for (Number i : x){
			min = Math.min(i.doubleValue(), min);
			result[k++] = min;
		}
		return result;
	};


	static public boolean all(List<Boolean> obs) {
		return all(obs, false);
	}
	static public boolean all(List<Boolean> obs, boolean na_rm) {
		if (na_rm) {
			return all(rmBNaN(obs), false);
		}
		for (Boolean o : obs){if (!o) return false;} return true;
	};
	static public boolean any(List<Boolean> obs) {
		return any(obs, false);
	}
	static public boolean any(List<Boolean> obs, boolean na_rm) {
		if (na_rm) {
			return any(rmBNaN(obs), false);
		}
		for (Boolean o : obs){if (o) return true;} return false;
	};

		
//  //@Internal
//  public static List<Double> pmin(boolean naRm, /*@ArgumentList*/ List<?> vectors) {
//    ParallelProcessor processor = new ParallelProcessor(naRm, vectors) {
//      @Override
//      boolean predicate(Number x, Number y) {
//        return ((Comparable)x).compareTo(y) > 0;
//      }
//    };
//    return processor.compute();
//  }
//  
//  //@Internal
//  public static List<Double> pmax(boolean naRm, /*@ArgumentList*/ List<?> vectors) {
//    ParallelProcessor processor = new ParallelProcessor(naRm, vectors) {
//      @Override
//      boolean predicate(Number x, Number y) {
//        return ((Comparable)x).compareTo(y) < 0;
//      }
//    };
//    return processor.compute();
//  }  
//  
 }
