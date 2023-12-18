/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.makeAdditionRule;
import static calculator.util.rules.RuleFactory.makeProductRule;

import calculator.util.ast.Tokenizer;
import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * PRODUCT RULE
 *
 * <p>this is used when multiple terms are multiplied together.
 *
 * <p>This class supports a product rule of more than two terms but {@link Tokenizer} will not
 * construct AST that have more than two kid
 */
public final class ProductRule extends DerivationRule {

  /** d/dx( f(x) * g(x) ) = f(x) * g'(x) + f'(x) * g(x) */
  @Override
  public Term getDerivative() {
    LinkedList<Term> addTerms = new LinkedList<>();
    for (int i = 0; i < this.terms.size(); i++) {
      LinkedList<Term> multTerms = new LinkedList<>();
      for (int j = 0; j < this.terms.size(); j++) {
        if (i != j) {
          multTerms.add(this.terms.get(j));
        } else {
          multTerms.add(this.terms.get(j).getDerivative());
        }
      }
      addTerms.add(makeProductRule(multTerms));
    }
    AdditionRule toReturn = makeAdditionRule(addTerms);
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  ProductRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * Adds a term to the product Rule
   *
   * @param t the term to add
   * @return this
   */
  public ProductRule addTerm(Term t) {
    this.terms.add(t);
    return this;
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return this.terms.stream().map(x -> x.evaluate(dims)).reduce((x, y) -> x * y).get();
  }

  @Override
  public String toString() {
    return terms.stream().map(Object::toString).collect(Collectors.joining(" * "));
  }
}
