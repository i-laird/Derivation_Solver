package calculator.util.rules;

import static calculator.util.rules.RuleFactory.makeAdditionRule;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * AdditionRule
 *
 * @author Ian Laird
 *     <p>Represents an ADDITION of terms
 *     <p>d/dx(x + y) = d/dx(x) + d/dx(y
 */
public final class AdditionRule extends DerivationRule {

  /**
   * d/dx(x + y) = d/dx(x) + d/dx(y)
   *
   * @return
   */
  @Override
  public Term getDerivative() {
    return makeAdditionRule(
        terms.stream()
            .map(x -> x.getDerivative())
            .collect(Collectors.toCollection(LinkedList::new)));
  }

  AdditionRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * Adds a term to the addition Rule
   *
   * @param t the term to add
   * @return this
   */
  public AdditionRule addTerm(Term t) {
    this.terms.add(t);
    return this;
  }

  /**
   * evaluates the addition rule by adding each term
   *
   * @param dims the variable values to use when evaulating
   * @return the result
   */
  public double getResult(ImmutableList<Integer> dims) {
    return this.terms.stream().map(x -> x.evaluate(dims)).reduce(0.0, Double::sum);
  }

  /**
   * tostring
   *
   * <p>Calls tostring on each term and joins them together with a + symbol
   */
  @Override
  public String toString() {
    return terms.stream().map(Object::toString).collect(Collectors.joining(" + "));
  }
}
