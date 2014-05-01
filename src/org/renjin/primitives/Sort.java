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

import org.renjin.sexp.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

  //@Internal
  public static List sort(List<String> x, boolean decreasing) {

    String sorted[] = x.toArray(new String[]{});
    if(decreasing) {
      Arrays.sort(sorted, Collections.reverseOrder());
    }else{
      Arrays.sort(sorted);
    }

    return toList(sorted);
  }
  
  static List toList(Object [] obs) {
	  List list = new ArrayList<>(obs.length);
	  for (Object o : obs) {
		  list.add(o);
	  }
	  return list;
  }

  //@Internal
  public static List sort(List<Double> x, boolean decreasing) {

    Double sorted[] = x.toArray(new Double[]{});

    Arrays.sort(sorted);
    if(decreasing) {
      reverse(sorted);
    }

    return (List) toList(sorted);
  }
  
  private static void reverse(Double[] b) {
    int left  = 0;          
    int right = b.length-1; 

    while (left < right) {
      Double temp = b[left]; 
      b[left]  = b[right]; 
      b[right] = temp;

      // move the bounds toward the center
      left++;
      right--;
    }
  }

  private static void reverse(Integer[] b) {
	    int left  = 0;          
	    int right = b.length-1; 

	    while (left < right) {
	      Integer temp = b[left]; 
	      b[left]  = b[right]; 
	      b[right] = temp;

	      // move the bounds toward the center
	      left++;
	      right--;
	    }
	  }

  //@Internal
  public static List sort(List<Integer> x, boolean decreasing) {


	Integer sorted[] = x.toArray(new Integer[]{});
    
    Arrays.sort(sorted);

    if(decreasing) {
      reverse(sorted);
    }

    return toList(sorted);
  }

  //@Internal("is.unsorted")
  public static boolean isUnsorted(List<Comparable> x, boolean strictly) {
    for(int i=1;i<x.size();++i) {
    	int z = x.get(i-1).compareTo(x.get(i));
      if(z > 0) {
        return true;
      } else if(strictly && z == 0) {
        return true;
      }
    }
    return false;
  }
  
  //@Internal("is.unsorted")
//  public static LogicalVector isUnsorted(ListVector x, boolean strictly) {
//    return LogicalVector.NA_VECTOR;
//  }
 
  //@Internal
  public static List<Double> qsort(List<Double> x, List<Integer> returnIndexes) {
    
    Double[] values = x.toArray(new Double[]{});
    Arrays.sort(values);
    
    List<Double> sorted = new DoubleArrayVector(values, x.getAttributes());
    
    // drop the names attributes if present because it will not be sorted
    return (List<Double>)sorted
            .setAttribute(Symbols.NAMES, Null.INSTANCE);  
  }
  
  //@Internal
  public static List<Double> psort(List<Double> x, List indexes) {
    // stub implementation: we just do a full sort
    return qsort(x, LogicalVector.FALSE);
  }

  //@Internal
  public static IntVector qsort(IntVector x, LogicalVector returnIndexes) {

    if(returnIndexes.isElementTrue(0)) {
      throw new EvalException("qsort(indexes=TRUE) not yet implemented");
    }
    
    Integer[] values = x.toArray(new Integer[]{});
    Arrays.sort(values);
    
    IntVector sorted = new IntArrayVector(values, x.getAttributes());
    
    // drop the names attributes if present because it will not be sorted
    return (IntVector)sorted
            .setAttribute(Symbols.NAMES, Null.INSTANCE);  
  }
  
  //@Internal
  public static IntVector psort(IntVector x, List indexes) {
    return qsort(x, LogicalVector.FALSE);
  }
  
  private static void reverse(int[] b) {
    int left  = 0;          
    int right = b.length-1; 

    while (left < right) {
      int temp = b[left]; 
      b[left]  = b[right]; 
      b[right] = temp;

      // move the bounds toward the center
      left++;
      right--;
    }
  }
  
  /**
   * Returns a permutation which rearranges its first argument into ascending or
   * descending order, breaking ties by further arguments.
   *
   * <p>This function is like a spreadsheet sort function.
   * Each argument is a column.
   *
   * @param columns
   * @return
   */
  //@Internal
  public static List order(boolean naLast, final boolean decreasing, @ArgumentList final ListVector columns) {
        
    if (columns.length() == 0) {
      return Null.INSTANCE;
    }
    
    int numRows = columns.getElementAsSEXP(0).length();

    for (int i = 0; i != columns.length(); ++i) {
      if (columns.getElementAsSEXP(i).length() != numRows) {
        throw new EvalException("argument lengths differ");
      }
    }

    List<Integer> ordering = Lists.newArrayListWithCapacity(numRows);
    for (int i = 0; i != numRows; ++i) {
      ordering.add(i);
    }

    Collections.sort(ordering, new Comparator<Integer>() {

      @Override
      public int compare(Integer row1, Integer row2) {
        int col = 0;
        int rel;
        while ((rel = compare(row1, row2, col)) == 0) {
          col++;
          if (col == columns.length()) {
            return 0;
          }
        }
        return decreasing ? -rel : rel;
      }

      private int compare(Integer row1, Integer row2, int col) {
        return ((AtomicVector)columns.get(col)).compare(row1, row2);
      }
    });

    IntArrayVector.Builder result = new IntArrayVector.Builder();
    for (Integer index : ordering) {
      result.add(index + 1);
    }

    return result.build();
  }   

  //@Internal("which.min")
  public static IntVector whichMin(List v) {
    if (v.length() == 0) {
      IntArrayVector.Builder b = new IntArrayVector.Builder();
      return (b.build());
    }
    int minIndex = 0;
    double globalMin = v.getElementAsDouble(0);
    //this loop would be started from 1 but it needs more code. I think this is fine.
    for (int i = 0; i < v.length(); i++) {
      if (v.getElementAsDouble(i) < globalMin) {
        globalMin = v.getElementAsDouble(i);
        minIndex = i;
      }
    }
    return (new IntArrayVector(minIndex + 1));
  }

  //@Internal("which.max")
  public static IntVector whichMax(List v) {
    if (v.length() == 0) {
      IntArrayVector.Builder b = new IntArrayVector.Builder();
      return (b.build());
    }
    int maxIndex = 0;
    double globalMax = v.getElementAsDouble(0);
    for (int i = 0; i < v.length(); i++) {
      if (v.getElementAsDouble(i) > globalMax) {
        globalMax = v.getElementAsDouble(i);
        maxIndex = i;
      }
    }
    return (new IntArrayVector(maxIndex + 1));
  }
  
  //@Builtin
  //@Generic
  public static SEXP xtfrm(@Current Context context, SEXP x) {
    FunctionCall defaultCall = FunctionCall.newCall(Symbol.get("xtfrm.default"), x);
    return context.evaluate(defaultCall);
  }
}
