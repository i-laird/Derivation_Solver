/* (C)2022 */
package calculator.util.terms;

import calculator.util.token.AbstractMath;
import java.util.Map;

public class Variable extends Term implements AbstractMath {

  // local variables
  private Character variableSymbol = null;

  public Variable(char c) {
    this.variableSymbol = c;
  }

  @Override
  public Term getDerivative(char withRespectTo) {
    if (variableSymbol == withRespectTo) {
      return (!this.negative ? new Term(1) : new Term(-1));
    }
    return new Term(0);
  }

  public Term getTermFromOp(Term one, Term two) {
    return this;
  }

  public double evaluate(Map<Character, Integer> dims) {
    int value = dims.getOrDefault(variableSymbol, 0);
    return this.negative ? value * -1.0 : value;
  }

  @Override
  public String toString() {
    return String.valueOf(this.variableSymbol);
  }
}
