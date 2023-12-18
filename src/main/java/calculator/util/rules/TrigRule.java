package calculator.util.rules;

import calculator.util.terms.Term;

import java.util.LinkedList;

public abstract sealed class TrigRule extends Rule
    permits SinRule,
        CosRule,
        TanRule,
        SecRule,
        CscRule,
        CotRule,
        SinhRule,
        CoshRule,
        TanhRule,
        SechRule,
        CschRule,
        CothRule,
        ArcSinRule,
        ArcCosRule,
        ArcTanRule,
        ArcCotRule {

  protected Term t;

  public TrigRule(LinkedList<Term> l) {
    this.t = l.get(0);
  }

  @Override
  public Term getDerivative() {
    Term t = this.getDerivPart();
    if (this.negative) {
      t.flipSign();
    }
    return t;
  }

  public abstract Term getDerivPart();

  @Override
  public String toString() {
    return functionName() + " ( " + t + " ) ";
  }

  /**
   * Function name that is to be used in the tostring representation.
   *
   * <p>Assumptions: function name must not have a space in it function name must match up with the
   * name used for parsing
   *
   * @return function name
   */
  public abstract String functionName();
}
