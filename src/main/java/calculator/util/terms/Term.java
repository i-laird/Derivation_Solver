package calculator.util.terms;

import com.google.common.collect.ImmutableList;

public class Term {
  protected boolean negative = false;
  private int num;

  public Term(int num) {
    this.num = num;
  }

  public Term() {}

  public int getNum() {
    return num * (negative ? -1 : 1);
  }

  /**
   * Sets the number
   *
   * @param num to set
   */
  public void setNum(int num) {
    this.num = num;
    this.negative = false;
  }

  /**
   * Calculates the antiderivative of the given expression.
   *
   * @return the antiderivative of the {@link Term}.
   */
  public Term getDerivative() {
    // in this case it is simply a number
    return new Term(0);
  }

  /**
   * Negates the expression
   *
   * @return negated expression
   */
  public Term flipSign() {
    negative = !negative;
    return this;
  }

  /**
   * Gets the value of the Term and negates it if necessary.
   *
   * @param dims evaluation dims
   * @return the evaluated expression
   */
  public double evaluate(ImmutableList<Integer> dims) {
    return this.getNum();
  }

  /**
   * Returns a string representation that will match accepted mathematical notation.
   *
   * @return string representation
   */
  public String toString() {
    return Integer.toString(negative ? num * -1 : num);
  }
}
