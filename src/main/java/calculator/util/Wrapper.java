package calculator.util;

import calculator.util.token.AbstractMath;
import calculator.util.token.Negative;

/**
 * Wrapper
 *
 * Holds a {@link AbstractMath} as well as a {@link Negative}
 */
public class Wrapper {

    // the math term being held
    private AbstractMath am = null;

    // a possible negation
    private Negative n = null;

    public AbstractMath getAm() {
        return am;
    }

    public void setAm(AbstractMath am) {
        this.am = am;
    }

    public Negative getN() {
        return n;
    }

    public void setN(Negative n) {
        this.n = n;
    }

    public Wrapper(AbstractMath am) {
        this.am = am;
    }

    public Wrapper(AbstractMath am, Negative n) {
        this.am = am;
        this.n = n;
    }
}
