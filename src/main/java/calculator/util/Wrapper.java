package calculator.util;

import calculator.util.token.AbstractMath;
import calculator.util.token.Negative;
import java.util.Objects;

/**
 * Wrapper
 *
 * <p>Holds a {@link AbstractMath} as well as a {@link Negative}
 */
public class Wrapper {

  // the math term being held
  private final AbstractMath am;

  // a possible negation
  private Negative n;

  public Wrapper(AbstractMath am) {
    this.am = Objects.requireNonNull(am);
    this.n = null;
  }

  public Wrapper(AbstractMath am, Negative n) {
    this.am = Objects.requireNonNull(am);
    this.n = n;
  }

  public AbstractMath getAm() {
    return am;
  }

  public Negative getN() {
    return n;
  }

  public void setN(Negative n) {
    this.n = n;
  }
}
