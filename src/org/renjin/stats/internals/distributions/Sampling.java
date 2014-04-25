package org.renjin.stats.internals.distributions;

//import org.renjin.eval.EvalException;
//import org.renjin.eval.Session;
//import org.renjin.invoke.annotations.Current;
//import org.renjin.invoke.annotations.Builtin;
//import org.renjin.invoke.annotations.Internal;
import java.util.ArrayList;

//import org.renjin.sexp.DoubleVector;
//import org.renjin.sexp.IntArrayVector;
import java.util.List;
//import org.renjin.sexp.SEXP;


public class Sampling {

  /*
   * Nice function name, hah? :-) 
   * A big reference goes to Holland and Goldberg
   */
  public static int RouletteWheel(double[] cumulativeDist, double rand) {
    int result = 0;
    for (int i = 0; i < cumulativeDist.length - 1; i++) {
      if (rand > cumulativeDist[i] && rand < cumulativeDist[i + 1]) {
        return (i + 1);
      }
    }
    return (result);
  }

  public static List<Integer> sampleWithReplacement(/*Session context,*/ int size, double[] prob) {
    double[] cumProbs = new double[prob.length];
    List<Integer> resultb = new ArrayList<Integer>(size);
    cumProbs[0] = prob[0];
    for (int i = 1; i < cumProbs.length; i++) {
      cumProbs[i] = prob[i] + cumProbs[i - 1];
    }
    for (int i = 0; i < size; i++) {
      double arand = context.rng_unif_rand();
      int index = RouletteWheel(cumProbs, arand);
      resultb.add(index + 1);
    }
    return resultb;
  }

  /*
   * This algorithm is my own choice and somebody must speed it up at next level.
   * I plan to select the first index from {1,2,3,4,...,N} and
   * select the second one from {1,2,3,4,...,N-1} - {1st} and
   * select the third one from {1,2,3,4,...,N-2} - {1st, 2nd} and so on.
   * this operation requires more array copying but operations reduce geometrically by iterations.
   * Because of the first stage aim, I am leaving it as a running but in-efficient algorithm.
   * Tests were passed :)
   */
  public static java.util.List<Integer> sampleWithoutReplacement(/*Session context,*/ int size, double[] prob) {
    double[] cumProbs = new double[prob.length];
    int[] selectedIndices = new int[prob.length];
    int numItems = 0;
    List<Integer> resultb = new ArrayList<Integer>();
    cumProbs[0] = prob[0];
    for (int i = 1; i < cumProbs.length; i++) {
      cumProbs[i] = prob[i] + cumProbs[i - 1];
    }

    while (numItems < size) {
      double arand = context.rng_unif_rand();
      int index = RouletteWheel(cumProbs, arand);
      if (selectedIndices[index] == 0) {
        selectedIndices[index] = 1;
        resultb.add(index + 1);
        numItems++;
      }
    }
    return resultb;
  }

  //@Internal
  public static List<Integer> sample(/*Session context,*/ int x, int size, boolean replace, double [] prob) {
    double[] probs = new double[x];
    int mysize = size;

    if (prob != null) {
      if (prob.length != x) {
        throw new RuntimeException("Length of x and probs are not equal");
      }
    }


    for (int i = 0; i < x; i++) {
      if (prob == null) {
        probs[i] = 1.0 / probs.length;
      } else {
        probs[i] = prob[i];
      }
    }


    if (replace) {
      return (sampleWithReplacement(/*context,*/ mysize, probs));
    } else {
      return (sampleWithoutReplacement(/*context,*/ mysize, probs));
    }


  }
}
