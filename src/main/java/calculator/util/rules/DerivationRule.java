package calculator.util.rules;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

/**
 * DerivationRule
 *
 * @author Ian Laird
 *     <p>The basis for finding derivatives.
 *     <p>Derivatives are found by finding the derivative of each term and then calling
 *     putTogether().
 */
public abstract sealed class DerivationRule extends Rule
    permits AdditionRule,
        QuotientRule,
        ProductRule,
        PowerRule,
        PowerFracRule,
        ChainRule,
        NaturalLogRule,
        LogRule {

  // the terms held in the rule
  protected LinkedList<Term> terms;

  public LinkedList<Term> getTerms() {
    return this.terms;
  }

  public DerivationRule(LinkedList<Term> l) {
    this.terms = l;
  }

  @Override
  public double evaluate(ImmutableList<Integer> dims) {
    double toReturn = this.getResult(dims);
    if (this.negative) {
      toReturn = toReturn * -1.0;
    }
    return toReturn;
  }

  public DerivationRule() {}
}
