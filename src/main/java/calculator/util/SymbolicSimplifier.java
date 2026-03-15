package calculator.util;

import calculator.util.rules.*;
import calculator.util.terms.Term;
import calculator.util.terms.Variable;

import java.util.LinkedList;
import java.util.List;

import static calculator.util.rules.RuleFactory.*;

/**
 * SymbolicSimplifier
 *
 * <p>Provides methods to simplify mathematical expressions represented as Terms.
 */
public class SymbolicSimplifier {

    /**
     * Simplifies the given Term recursively.
     *
     * @param term the Term to simplify
     * @return the simplified Term
     */
    public static Term simplify(Term term) {
        if (term == null) {
            return null;
        }

        Term simplified;
        if (term instanceof AdditionRule) {
            simplified = simplifyAddition((AdditionRule) term);
        } else if (term instanceof ProductRule) {
            simplified = simplifyProduct((ProductRule) term);
        } else if (term instanceof QuotientRule) {
            simplified = simplifyQuotient((QuotientRule) term);
        } else if (term instanceof PowerRule) {
            simplified = simplifyPower((PowerRule) term);
        } else if (term instanceof ChainRule) {
            simplified = simplifyChain((ChainRule) term);
        } else if (term instanceof TrigRule) {
            simplified = simplifyTrig((TrigRule) term);
        } else if (term instanceof NaturalLogRule) {
            simplified = simplifyNaturalLog((NaturalLogRule) term);
        } else if (term instanceof LogRule) {
            simplified = simplifyLog((LogRule) term);
        } else {
            // Base cases: Term (constant) or Variable
            return term;
        }

        // If the simplified result is a different instance and it is a rule, 
        // we might need to preserve the negative flag if the original had it.
        // However, many simplifyXXX methods already handle negation or return new terms.
        // Base Term and Variable handle their own signs.
        // Let's ensure rules created by factories in simplifyXXX don't double-negate.
        
        return simplified;
    }

    /**
     * Simplifies an addition rule by applying simplification rules to each term and combining them.
     * Since the order of the rules does not matter, we can add together the constant terms.
     * @param rule The rule to simplify
     * @return The simplified rule
     */
    private static Term simplifyAddition(AdditionRule rule) {
        LinkedList<Term> simplifiedTerms = new LinkedList<>();
        for (Term t : rule.getTerms()) {
            Term simplified = simplify(t);
            // If it is a zero, remove this term since x + 0 = x
            if (isConstant(simplified, 0)) {
                continue;
            }
            simplifiedTerms.add(simplified);
        }

        if (simplifiedTerms.isEmpty()) {
            return new Term(0);
        }
        if (simplifiedTerms.size() == 1) {
            return simplifiedTerms.get(0);
        }

        // Constant folding for addition
        int constantSum = 0;
        LinkedList<Term> nonConstantTerms = new LinkedList<>();
        for (Term t : simplifiedTerms) {
            if (t.getClass() == Term.class) {
                constantSum += t.getNum();
            } else {
                nonConstantTerms.add(t);
            }
        }

        if (nonConstantTerms.isEmpty()) {
            return new Term(constantSum);
        }
        if (constantSum != 0) {
            nonConstantTerms.add(new Term(constantSum));
        }

        if (nonConstantTerms.size() == 1) {
            return nonConstantTerms.get(0);
        }

        return makeAdditionRule(nonConstantTerms);
    }

    private static Term simplifyProduct(ProductRule rule) {
        LinkedList<Term> simplifiedTerms = new LinkedList<>();
        for (Term t : rule.getTerms()) {
            Term simplified = simplify(t);
            // Anything multiplied by zero is zero.
            if (isConstant(simplified, 0)) {
                return new Term(0);
            }
            // Multiplying by one is not necessary.
            if (isConstant(simplified, 1)) {
                continue;
            }
            simplifiedTerms.add(simplified);
        }

        if (simplifiedTerms.isEmpty()) {
            return new Term(1);
        }
        if (simplifiedTerms.size() == 1) {
            return simplifiedTerms.get(0);
        }

        // Since the order of the rules does not matter, we can multiply together the constant terms.
        int constantProduct = 1;
        LinkedList<Term> nonConstantTerms = new LinkedList<>();
        for (Term t : simplifiedTerms) {
            if (t.getClass() == Term.class) {
                constantProduct *= t.getNum();
            } else {
                nonConstantTerms.add(t);
            }
        }
        
        if (nonConstantTerms.isEmpty()) {
            return new Term(constantProduct);
        }
        if (constantProduct != 1) {
            nonConstantTerms.add(0, new Term(constantProduct));
        }

        if (nonConstantTerms.size() == 1) {
            return nonConstantTerms.get(0);
        }

        return makeProductRule(nonConstantTerms);
    }

    private static Term simplifyQuotient(QuotientRule rule) {
        List<Term> terms = rule.getTerms();
        Term originalNumerator = terms.get(QuotientRule.NUMERATOR_POS);
        Term originalDenominator = terms.get(QuotientRule.DENOM_POS);
        Term simplifiedNumerator = simplify(originalNumerator);
        Term simplifiedDenominator = simplify(originalDenominator);

        // 0 / x = 0
        if (isConstant(simplifiedNumerator, 0)) {
            return new Term(0);
        }
        // x / 1 = x
        if (isConstant(simplifiedDenominator, 1)) {
            return simplifiedNumerator;
        }

        // If the numerator and denominator simplify to terms, return a simple division.
        if (simplifiedNumerator.getClass() == Term.class && simplifiedDenominator.getClass() == Term.class) {
            int n = simplifiedNumerator.getNum();
            int d = simplifiedDenominator.getNum();
            if (d != 0 && n % d == 0) {
                return new Term(n / d);
            }
        }

        if (originalDenominator == simplifiedDenominator && originalNumerator == simplifiedNumerator) {
            return rule;
        }

        return makeFracRule(simplifiedNumerator, simplifiedDenominator);
    }

    private static Term simplifyPower(PowerRule rule) {
        List<Term> terms = rule.getTerms();
        Term originalBase = terms.get(PowerRule.BASE_POS);
        Term originalPower = terms.get(PowerRule.POW_POS);
        Term simplifiedBase = simplify(originalBase);
        Term simplifiedPower = simplify(originalPower);

        // Anything raised to the zero is a one.
        if (isConstant(simplifiedPower, 0)) {
            return new Term(1);
        }
        // Anything raised to the one is itself.
        if (isConstant(simplifiedPower, 1)) {
            return simplifiedBase;
        }
        // One to any power is one.
        if (isConstant(simplifiedBase, 1)) {
            return new Term(1);
        }
        // 0 ^ x = 0 (assuming x > 0)
        if (isConstant(simplifiedBase, 0)) {
            return new Term(0);
        }

        if (originalBase == simplifiedBase && originalPower == simplifiedPower) {
            return rule;
        }

        return makePowerRule(simplifiedBase, simplifiedPower);
    }

    private static Term simplifyChain(ChainRule rule) {
        List<Term> terms = rule.getTerms();
        Term outside = simplify(terms.get(0));
        Term inside = simplify(terms.get(1));

        if (isConstant(inside, 1)) {
            return outside;
        }
        if (isConstant(inside, 0)) {
            return new Term(0);
        }

        if (outside == terms.get(0) && inside == terms.get(1)) {
            return rule;
        }

        return makeChainRule(outside, inside);
    }

    private static Term simplifyTrig(TrigRule rule) {
        Term originalArgument = rule.getTerm();
        Term simplifiedArgument = simplify(originalArgument);
        
        if (originalArgument == simplifiedArgument) {
            return rule;
        }

        if (rule instanceof SinRule) return makeSinRule(simplifiedArgument);
        if (rule instanceof CosRule) return makeCosRule(simplifiedArgument);
        if (rule instanceof TanRule) return makeTanRule(simplifiedArgument);
        if (rule instanceof SecRule) return makeSecRule(simplifiedArgument);
        if (rule instanceof CscRule) return makeCscRule(simplifiedArgument);
        if (rule instanceof CotRule) return makeCotRule(simplifiedArgument);
        if (rule instanceof SinhRule) return makeSinhRule(simplifiedArgument);
        if (rule instanceof CoshRule) return makeCoshRule(simplifiedArgument);
        if (rule instanceof TanhRule) return makeTanhRule(simplifiedArgument);
        if (rule instanceof SechRule) return makeSechRule(simplifiedArgument);
        if (rule instanceof CschRule) return makeCschRule(simplifiedArgument);
        if (rule instanceof CothRule) return makeCothRule(simplifiedArgument);
        if (rule instanceof ArcSinRule) return makeArcSinRule(simplifiedArgument);
        if (rule instanceof ArcCosRule) return makeArcCosRule(simplifiedArgument);
        if (rule instanceof ArcTanRule) return makeArcTanRule(simplifiedArgument);
        if (rule instanceof ArcCotRule) return makeArcCotRule(simplifiedArgument);
        if (rule instanceof ArcSecRule) return makeArcSecRule(simplifiedArgument);
        if (rule instanceof ArcCscRule) return makeArcCscRule(simplifiedArgument);

        return rule;
    }

    private static Term simplifyNaturalLog(NaturalLogRule rule) {
        Term originalArgument = rule.getTerms().get(NaturalLogRule.ARGUMENT_INDEX);
        Term simplifiedArgument = simplify(originalArgument);
        // e ^ 0 = 1.
        if (isConstant(simplifiedArgument, 1)) {
            return new Term(0);
        }
        if (originalArgument == simplifiedArgument) {
            return rule;
        }
        return makeNaturalLogRule(simplifiedArgument);
    }

    private static Term simplifyLog(LogRule rule) {
        List<Term> terms = rule.getTerms();
        Term originalBase = terms.get(LogRule.BASE_INDEX);
        Term originalArgument = terms.get(LogRule.ARGUMENT_INDEX);
        Term simplifiedBase = simplify(originalBase);
        Term simplifiedArgument = simplify(originalArgument);
        if (isConstant(simplifiedArgument, 1)) {
            return new Term(0);
        }
        if (originalBase == simplifiedBase && originalArgument == simplifiedArgument) {
            return rule;
        }
        return makeLogRule(simplifiedBase, simplifiedArgument);
    }

    /**
     * Checks if the given term is a constant with the given value.
     * @param term the term to check
     * @param value the value to check against
     * @return true if the term is a constant with the given value, false otherwise
     */
    private static boolean isConstant(Term term, int value) {
        return term != null && term.getClass() == Term.class && term.getNum() == value;
    }
}
