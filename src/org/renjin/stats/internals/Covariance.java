package org.renjin.stats.internals;

import java.util.List;




//import org.renjin.eval.EvalException;
//import org.renjin.invoke.annotations.Builtin;
//import org.renjin.invoke.annotations.Internal;
//import org.renjin.sexp.AtomicVector;
//import org.renjin.sexp.DoubleVector;
//import org.renjin.sexp.Vector;

public class Covariance {

//  @Internal
  public static DoubleVector cor(List<Object> x, List<Object> y, int naMethod, boolean kendall) {

    if(kendall) {
      throw new RuntimeException("kendall=true nyi");
    }

    return new VarianceCalculator(x, y, naMethod)
    .withPearsonCorrelation()
    .calculate();
  }


//  @Internal
  public static Vector cov(List<Object> x, List<Object> y, int naMethod, boolean kendall) {
    if(kendall) {
      throw new RuntimeException("kendall=true nyi");
    }

    return new VarianceCalculator(x, y, naMethod)
    .withCovarianceMethod()
    .calculate();
  }

}
