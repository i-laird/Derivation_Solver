package DerivationSolver;

import DerivationSolver.Enums.AbstractMath;
import DerivationSolver.Enums.Negative;

public class Wrapper {
    private AbstractMath am = null;
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
