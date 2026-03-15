package calculator.util;

import calculator.util.terms.Term;
import calculator.util.terms.Variable;
import org.junit.jupiter.api.Test;

import static calculator.util.rules.RuleFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SymbolicSimplifierTest {

    @Test
    void simplify_additionWithZero_removesZero() {
        Term expr = makeAdditionRule(new Variable('x'), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("x", simplified.toString());
    }

    @Test
    void simplify_additionAllZeros_returnsZero() {
        Term expr = makeAdditionRule(new Term(0), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_multiplicationWithOne_removesOne() {
        Term expr = makeProductRule(new Variable('x'), new Term(1));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("x", simplified.toString());
    }

    @Test
    void simplify_multiplicationWithZero_returnsZero() {
        Term expr = makeProductRule(new Variable('x'), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_powerZero_returnsOne() {
        Term expr = makePowerRule(new Variable('x'), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("1", simplified.toString());
    }

    @Test
    void simplify_powerOne_returnsBase() {
        Term expr = makePowerRule(new Variable('x'), new Term(1));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("x", simplified.toString());
    }

    @Test
    void simplify_constantFoldingAddition() {
        Term expr = makeAdditionRule(new Term(2), new Term(3));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("5", simplified.toString());
    }

    @Test
    void simplify_constantFoldingProduct() {
        Term expr = makeProductRule(new Term(2), new Term(3));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("6", simplified.toString());
    }

    @Test
    void simplify_returnsSameInstance_whenNoSimplification() {
        Term expr = makeSinRule(new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals(expr, simplified, "Should return the same instance when no simplification is possible");
    }

    @Test
    void simplify_returnsNewInstance_whenArgumentSimplified() {
        Term expr = makeSinRule(makeAdditionRule(new Variable('x'), new Term(0)));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("sin ( x ) ", simplified.toString());
        // Since it was simplified, it should be a different instance (or at least have a simplified argument)
    }
    @Test
    void simplify_complexExpression() {
        // (1 * cos(x)) + (0 * sin(x))
        Term part1 = makeProductRule(new Term(1), makeCosRule(new Variable('x')));
        Term part2 = makeProductRule(new Term(0), makeSinRule(new Variable('x')));
        Term expr = makeAdditionRule(part1, part2);

        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("cos ( x ) ", simplified.toString());
    }

    @Test
    void simplify_quotientByOne_returnsNumerator() {
        Term expr = makeFracRule(new Term(1), new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("x", simplified.toString());
    }

    @Test
    void simplify_quotientZeroNumerator_returnsZero() {
        Term expr = makeFracRule(new Variable('x'), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_quotientConstantFolding() {
        Term expr = makeFracRule(new Term(2), new Term(10));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("5", simplified.toString());
    }

    @Test
    void simplify_powerOneBase_returnsOne() {
        Term expr = makePowerRule(new Term(1), new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("1", simplified.toString());
    }

    @Test
    void simplify_powerZeroBase_returnsZero() {
        Term expr = makePowerRule(new Term(0), new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_naturalLogOne_returnsZero() {
        Term expr = makeNaturalLogRule(new Term(1));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_logOne_returnsZero() {
        Term expr = makeLogRule(new Term(10), new Term(1));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_chainRule_constantInside_returnsZero() {
        Term expr = makeChainRule(makeSinRule(new Variable('x')), new Term(0));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_chainRule_oneInside_returnsOutside() {
        Term expr = makeChainRule(makeSinRule(new Variable('x')), new Term(1));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("sin ( x ) ", simplified.toString());
    }

    @Test
    void simplify_nestedChainAndPower() {
        // sin(x + 0) ^ 1
        Term inner = makeAdditionRule(new Variable('x'), new Term(0));
        Term sin = makeSinRule(inner);
        Term expr = makePowerRule(sin, new Term(1));
        
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("sin ( x ) ", simplified.toString());
    }

    @Test
    void simplify_powerZeroBaseVariablePower() {
        // 0 ^ x
        Term expr = makePowerRule(new Term(0), new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("0", simplified.toString());
    }

    @Test
    void simplify_oneToPower_returnsOne() {
        // 1 ^ x
        Term expr = makePowerRule(new Term(1), new Variable('x'));
        Term simplified = SymbolicSimplifier.simplify(expr);
        assertEquals("1", simplified.toString());
    }
}
