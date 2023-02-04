package calculator.util.ast;

import calculator.exception.ParseError;
import calculator.util.Wrapper;
import calculator.util.terms.Term;
import calculator.util.terms.Variable;
import calculator.util.token.AbstractMath;
import calculator.util.token.Function;
import calculator.util.token.Negative;
import calculator.util.token.Num;
import calculator.util.token.Operator;
import calculator.util.token.Paren;
import java.util.*;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * @author Laird
 *
 *     <p>Tokenizes an input line to create the operation/function tree.
 *
 *     <p>STEPS: 1) Uses the shunting yard algorithm to convert mathematical expression from infix
 *     to postfix notation. -order of operations is taken into account when parsing -I had to modify
 *     the standard algorithm to account for unary operators (i.e. sin() cos()) 2) Creates AST from
 *     the postfix expression.
 *
 * @see Term
 */
public class Tokenizer {
  private boolean operatorOrFunctionSeen = true;

  /**
   * tokenize the expression
   *
   * @param expression the expression to tokenize
   * @return in order syntax and numbers
   */
  List<AbstractMath> tokenizeExpression(@NonNull String expression) {

    // split line by white space
    String[] parts = expression.split("\\s+");

    // this step further splits if the string does not delimit by white space
    List<String> cleanedInput = cleanInput(parts);

    // map each token to its associated mathematical operation
    List<AbstractMath> mappedParts =
        cleanedInput.stream()
            .map(this::getMappedPart) // converts to syntax enum i.e. sin -> SIN
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    // remove subtraction
    // i.e. SUBTRACT becomes ADD NEGATIVE
    List<AbstractMath> subtractionRemoved = removeSubtraction(mappedParts);

    // remove excessive negatives
    // i.e. NEGATIVE NEGATIVE 5 -> 5
    List<AbstractMath> negativesRemoved = removeMultipleNegatives(subtractionRemoved);

    // remove negated variables
    // i.e. -x becomes -1 * x
    List<AbstractMath> negatedVariablesRemoved = removeNegatedVariables(negativesRemoved);

    // add parens after unary operators as necessary
    List<AbstractMath> unaryParensAdded =
        addParenAfterUnary(new LinkedList<>(), negatedVariablesRemoved, 0, 0);
    return unaryParensAdded;
  }

  /**
   * @param list an array of strings to be cleaned
   * @return splits monomials
   *     <p>example: 3x into 3 * x
   */
  private static List<String> cleanInput(@NonNull String[] list) {

    // create a stack that will hold the items that need to be processed
    Stack<String> processStack = new Stack<>();

    for (int i = list.length - 1; i >= 0; i--) {
      processStack.push(list[i]);
    }

    List<String> returnList = new LinkedList<>();

    // one at a time evaluate the elements of the stack to see if they can be further subdivided
    // for example 5+ should become 5 +
    //             5x should become 5 * x
    // once the token is split push all the subtokens on the stack where they will be checked for
    // further splits
    // loop stops when there are no more tokens left to evaluate
    while (!processStack.isEmpty()) {
      String s = processStack.pop();
      String[] parsedParts = null;

      // add space before parentheses
      if (splitByRegex(s, null, "((?<=[\\(\\)\\*\\^])|(?=[\\(\\)\\*\\^]))", processStack, null)) {
        continue;
      }

      // turn something like 3x into 3 * x
      if (s.matches(".*[0-9][a-z].*")) {
        splitByRegex(s, 2, "(?=[a-z])", processStack, "*");
        continue;
      }

      // add space NEGATIVE signs
      if (splitByRegex(s, null, "((?=-)|(?<=-))", processStack, null)) {
        continue;
      }

      // add space before ADD signs
      if (splitByRegex(s, null, "((?=\\+)|(?<=\\+))", processStack, null)) {
        continue;
      }

      // add space before MULTIPLY signs
      if (splitByRegex(s, null, "((?=\\*)|(?<=\\*))", processStack, null)) {
        continue;
      }

      // add space before DIVIDE signs
      if (splitByRegex(s, null, "((?=/)|(?<=/))", processStack, null)) {
        continue;
      }

      returnList.add(s);
    }
    return returnList;
  }

  /**
   * @param toSplit the string to split
   * @param splitLimit maximum number of substring to create from the base string
   * @param regex regular expression to use for splitting
   * @param processStack holds the items that need to be processed
   * @param separator if a non empty string is pushed between every element that is added to the
   *     stack
   * @return if the string was split into at least two substrings
   *     <p>ASSUMPTION: if a split is not possible (i.e. after using regex there is still only one
   *     string) then nothing is added to the process stack and false is returned
   */
  private static boolean splitByRegex(
      @NonNull String toSplit,
      Integer splitLimit,
      @NonNull String regex,
      @NonNull Stack<String> processStack,
      String separator) {
    if (Objects.nonNull(splitLimit) && splitLimit < 1) {
      return false;
    }
    String[] parsedParts =
        Objects.nonNull(splitLimit) ? toSplit.split(regex, splitLimit) : toSplit.split(regex);

    // if number of substring is more than 1 then push all substrings to the stack with separator
    if (parsedParts.length > 1) {
      for (int i = parsedParts.length - 1; i >= 0; i--) {
        processStack.push(parsedParts[i]);
        if (i > 0 && Objects.nonNull(separator) && !separator.equals("")) {
          processStack.push(separator);
        }
      }
      return true;
    }
    return false;
  }

  /**
   * @author laird
   * @param s the string to be evaluated
   * @return the abstract math part corresponding to the string
   *     <p>caution: if s is only white space or empty null is returned
   */
  private AbstractMath getMappedPart(@NonNull String s) {
    // get rid of anything that is just white space
    if (s.matches("\\s+") || s.isEmpty()) {
      return null;
    }
    if (s.matches("\\(")) {
      operatorOrFunctionSeen = true;
      return Paren.LEFT_PAREN;
    }
    if (s.matches("\\)")) {
      return Paren.RIGHT_PAREN;
    }
    AbstractMath returnThing = null;
    // see if it is an integer
    if (s.matches("[+-]?\\d+")) {
      operatorOrFunctionSeen = false;
      return new Num(Integer.parseInt(s));
    }
    // see if it is a negative sign
    if (operatorOrFunctionSeen && s.equals("-")) {
      operatorOrFunctionSeen = false;
      return new Negative();
    }
    returnThing = Operator.getOp(s);
    if (returnThing == null) {
      returnThing = Function.getFunc(s);
    }
    if (returnThing != null) {
      operatorOrFunctionSeen = true;
      return returnThing;
    }
    if (s.length() != 1) {
      throw new ParseError("Invalid term seen: " + s);
    }
    operatorOrFunctionSeen = false;
    return new Variable(s.charAt(0));
  }

  /**
   * @param l the list of math terms in order
   * @return double negatives are removed, and negated subtraction becomes addition
   *     <p>example: NEGATIVE NEGATIVE 5 -> 5 5 PLUS NEGATIVE 5 -> 5 MINUS 5
   */
  private static List<AbstractMath> removeMultipleNegatives(List<AbstractMath> l) {
    List<AbstractMath> returnList = new LinkedList<>();

    // holds all plus and minus signs encountered that have not been processed yet
    Queue<AbstractMath> plusMinusToProcess = new LinkedList<>();
    for (AbstractMath am : l) {

      // IF the token being analyzed is not a minus or plus sign
      // it is time to clear out the queue of the unprocessed + and - signs
      if (am.getClass() != Negative.class && am != Operator.ADD) {
        if (!plusMinusToProcess.isEmpty()) {
          int minusCount = 0;
          AbstractMath topElem = plusMinusToProcess.peek();

          // count the number of - signs (negative and subtraction)
          while (!plusMinusToProcess.isEmpty()) {
            AbstractMath toEval = plusMinusToProcess.remove();
            if (toEval.getClass() == Negative.class) {
              ++minusCount;
            }
          }

          // handle addition / subtraction case
          if (topElem == Operator.ADD) {
            returnList.add(Operator.ADD);
            if(minusCount % 2 == 1) {
              returnList.add(new Negative());
            }
          }

          // handle negation case
          else if (topElem.getClass() == Negative.class) {
            if (minusCount % 2 == 1) {
              returnList.add(new Negative());
            }
          }
        }
        returnList.add(am);
      } else {
        plusMinusToProcess.add(am);
      }
    }
    return returnList;
  }

  public List<AbstractMath> removeNegatedVariables(List<AbstractMath> tokenList) {
    List<AbstractMath> returnList = new LinkedList<>();

    for(int i = 0; i < tokenList.size(); i++){
      if (i < tokenList.size() - 1 && tokenList.get(i).getClass() == Negative.class && tokenList.get(i + 1).getClass() == Variable.class){
        returnList.add(new Num(-1));
        returnList.add(Operator.MULTIPLY);
        returnList.add(tokenList.get(i + 1));
        ++i;
      } else {
        returnList.add(tokenList.get(i));
      }
    }
    return returnList;
  }

  public List<AbstractMath> removeSubtraction(List<AbstractMath> tokenList) {
    List<AbstractMath> returnList = new LinkedList<>();
    for (AbstractMath am : tokenList) {
      if (am == Operator.SUBTRACT) {
        returnList.add(Operator.ADD);
        returnList.add(new Negative());
      }
      else {
        returnList.add(am);
      }
    }
    return returnList;
  }

  /**
   * @param returnList list after parens have been added after all unary operators
   * @param l list to evaluate
   * @param index index to start evaluation at (0 for root case)
   * @param rightParenToAdd the number of ummatched left parens encountered so far (0 for root case)
   * @returnlist after parens have been added after all unary operators for all elements of l after
   *     and including index index
   */
  public static List<AbstractMath> addParenAfterUnary(
      @NonNull List<AbstractMath> returnList,
      @NonNull List<AbstractMath> l,
      int index,
      int rightParenToAdd) {
    while (index < l.size()) {
      AbstractMath current = l.get(index);
      returnList.add(current);

      // if a unary operator
      if (current.getClass() == Function.class) {
        index++;
        AbstractMath next = l.get(index);

        // if not a left paren
        if (!(next.getClass() == Paren.class && next == Paren.LEFT_PAREN)) {
          returnList.add(Paren.LEFT_PAREN);

          // finding where to put the closing parenthesis
          while (true) {
            returnList.add(next);
            if (next.getClass() == Negative.class) {
              index++;
              next = l.get(index);
              continue;
            }
            if (next.getClass() == Function.class) {
              returnList.addAll(addParenAfterUnary(returnList, l, index + 1, rightParenToAdd + 1));
              return returnList;
            }
            break;
          }
          returnList.add(Paren.RIGHT_PAREN);
          ++index;
        }
      } else {
        for (int i = 0; i < rightParenToAdd; i++) {
          returnList.add(Paren.RIGHT_PAREN);
          rightParenToAdd = 0;
        }
        ++index;
      }
    }
    return returnList;
  }

  /**
   * converts a list of tokens to postfix notation
   *
   * @param tokens the tokens
   * @return post fix notation
   */
  static Queue<Wrapper> convertToPostFix(@NonNull List<AbstractMath> tokens) {
    int numRightParenEncountered = 0;
    Queue<Wrapper> outputParts = new LinkedList<>();
    Negative negative = null;

    // used to iterate over the tokens
    ListIterator<AbstractMath> iter = tokens.listIterator();

    // used to find when to end the jurisdiction of unary operators
    // i.e. for sin(x) a mapping will be stored from sin to the end paren
    Map<Wrapper, List<Integer>> functionToLastAppliedTerm = new HashMap<>();

    Stack<Wrapper> stack = new Stack<>();

    while (iter.hasNext()) {
      AbstractMath am = iter.next();

      // if this is a NEGATIVE remember it and go to next token
      if (am.getClass() == Negative.class) {
        if (Objects.nonNull(negative)) {
          throw new ParseError("Double negative encountered after token simplification");
        }
        negative = (Negative) am;
        continue;
      }

      // if token is a constant or a variable
      // negate if necessary and then output to output queue
      if (am.getClass() == Num.class || am.getClass() == Variable.class) {

        outputParts.add(new Wrapper(am, negative));
        negative = null;
      }

      // if it is a unary operator figure out when the operator stops applying
      else if (am.getClass() == Function.class) {
        int tempRightParenCount = 0;
        ListIterator<AbstractMath> iter2 = tokens.listIterator(iter.nextIndex());

        AbstractMath next = iter2.next();

        // if the immediate following is an OPEN PAREN look for CLOSE PAREN
        if (next.getClass() == Paren.class && next == Paren.LEFT_PAREN) {

          // stores the number of OPEN PAREN seen
          int leftParenCount = 0;
          while (true) {
            if (next.getClass() == Paren.class) {
              if (next == Paren.LEFT_PAREN) {
                leftParenCount++;
              } else {
                ++tempRightParenCount;
                if (leftParenCount > 1) {
                  leftParenCount--;
                } else {
                  break;
                }
              }
            }
            next = iter2.next();
          }
        }
        Wrapper wrapped = new Wrapper(am, negative);
        negative = null;
        // TODO when would this be absent?
        functionToLastAppliedTerm.putIfAbsent(wrapped, new LinkedList<>());
        List<Integer> valueList = functionToLastAppliedTerm.get(wrapped);
        valueList.add(numRightParenEncountered + tempRightParenCount);
      }

      // if the token is a paren
      else if (am.getClass() == Paren.class) {

        // if it is an opening paren push it onto the stack
        if (am == Paren.LEFT_PAREN) {
          stack.push(new Wrapper(am));
        }

        // if it is a RIGHT paren
        else {
          ++numRightParenEncountered;

          //  keep popping from the stack until a LEFT paren is encountered
          while ((stack.peek()).getAm() != Paren.LEFT_PAREN) {
            outputParts.add(stack.pop());
          }

          // get rid of the left paran that matches up with this right paren
          stack.pop();

          AbstractMath toRemove = null;

          // check if any unary operators domain ends here
          checkUnaryEnd(functionToLastAppliedTerm, numRightParenEncountered, outputParts);
        }
      }

      // if an operator
      else {

        while (true) {
          if (stack.empty()) {
            break;
          }
          try {

            // shunting yard algorithm stuff
            if ((stack.peek()).getAm().getClass() == Paren.class
                || ((((Operator) (stack.peek()).getAm())).precedence < ((Operator) am).precedence)
                || (((Operator) (stack.peek()).getAm()).precedence == ((Operator) am).precedence)
                    && ((Operator) (stack.peek()).getAm()).associativity
                        == Operator.Associativity.LEFT) {
              break;
            }

            // account for natural log being weird
            // TODO check to see if this works
            if (am == Operator.NAT_LOG) {
              outputParts.add(new Wrapper(new Num(1)));
            }
          } catch (ClassCastException e) {
            System.err.println(e);
          }
          outputParts.add(stack.pop());
        }
        stack.push(new Wrapper(am));
      }

      // if negative preceded this negate the top of the stack
      if (negative != null) {
        Wrapper popped = stack.pop();
        popped.setN(negative);
        negative = null;
        stack.push(popped);
      }
    }

    // if there are things left on the stack put them all into the queue
    while (!stack.empty()) {
      Wrapper part = stack.pop();
      if (part.getAm().getClass() != Paren.class) {
        try {
          if (part.getAm() == Operator.NAT_LOG) {
            outputParts.add(new Wrapper(new Num(1)));
          }
        } catch (ClassCastException e) {
        }
        outputParts.add(part);
      }
    }
    return outputParts;
  }

  public static void checkUnaryEnd(
      @NonNull Map<Wrapper, List<Integer>> functionToLastAppliedTerm,
      int numRightParenEncountered,
      @NonNull Queue<Wrapper> outputParts) {
    Wrapper toRemove = null;

    // see if a unary operator ended at this point
    for (Map.Entry<Wrapper, List<Integer>> k : functionToLastAppliedTerm.entrySet()) {
      List<Integer> valueList = k.getValue();
      for (int i = 0; i < valueList.size(); i++) {
        Integer val = valueList.get(i);
        if (val == numRightParenEncountered) {
          outputParts.add(k.getKey());
          valueList.remove(i);
          if (valueList.isEmpty()) {
            toRemove = k.getKey();
          }
          break;
        }
      }
    }
    functionToLastAppliedTerm.remove(toRemove);
  }
}
