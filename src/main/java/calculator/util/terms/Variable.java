/* (C)2022 */
package calculator.util.terms;

import calculator.util.token.AbstractMath;
import com.google.common.collect.ImmutableList;

public class Variable extends Term implements AbstractMath {

  // local variables
  private Character variableSymbol = null;

  public Variable(char c) {
    this.variableSymbol = c;
  }

  @Override
  public Term getDerivative() {
    // the derivative of x with respect to x is simply 1
    return (!this.negative ? new Term(1) : new Term(-1));
  }

  public Term getTermFromOp(Term one, Term two) {
    return this;
  }

  public double evaluate(ImmutableList<Integer> dims) {
    return (this.negative ? dims.get(0) * -1 : dims.get(0));
  }

  @Override
  public String toString() {
    return String.valueOf(this.variableSymbol);
  }
}
