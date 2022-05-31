package calculator.util;

import calculator.util.token.AbstractMath;
import calculator.util.token.Negative;
import lombok.*;

/**
 * Wrapper
 *
 * Holds a {@link AbstractMath} as well as a {@link Negative}
 */
@Data
@AllArgsConstructor
public class Wrapper {

    // the math term being held
    @NonNull
    private AbstractMath am;

    // a possible negation
    private Negative n = null;

    public Wrapper(AbstractMath am){
        this.am = am;
        this.n = null;
    }

}
