package calculator.util.rules;

import java.util.LinkedList;

import calculator.util.terms.Term;

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

  public DerivationRule(LinkedList<Term> l) {
    this.terms = l;
  }

  public DerivationRule() {}
}
