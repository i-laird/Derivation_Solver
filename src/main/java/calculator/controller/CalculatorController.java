package calculator.controller;

import calculator.DTO.DerivativeRequest;
import calculator.DTO.DerivativeResponse;
import calculator.exception.ParseError;
import calculator.service.CalculatorService;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Controller handling calculator requests. */
@RestController
public final class CalculatorController {

  private final CalculatorService calculatorServiceImpl;

  public CalculatorController(CalculatorService calculatorServiceImpl) {
    this.calculatorServiceImpl = calculatorServiceImpl;
  }

  /**
   * Calculates the anti-derivative of a mathematical expression and then evaluates it at a specific
   * point.
   *
   * @param request The request.
   * @return the anti-derivative and result at a specific point.
   */
  @PostMapping(value = "/derivative")
  @ResponseStatus(HttpStatus.OK)
  public DerivativeResponse generateDerivative(@Valid @RequestBody DerivativeRequest request) {
    if (request.withRespectTo() == null || request.withRespectTo().length() != 1
            || !Character.isLetter(request.withRespectTo().charAt(0))) {
      throw new ParseError("withRespectTo must be a single letter variable name");
    }
    char wrt = request.withRespectTo().charAt(0);
    Map<Character, Integer> evalPoints = request.points() == null
        ? Map.of()
        : request.points().entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().charAt(0),
                Map.Entry::getValue));
    return calculatorServiceImpl.evaluateDerivative(request.expression(), wrt, evalPoints);
  }

  /**
   * Evaluates a mathematical expression.
   *
   * @param request the request.
   * @return The evaluation of a mathametical expression at specific points.
   */
  @PostMapping(value = "/expression")
  @ResponseStatus(HttpStatus.OK)
  public double evaluateExpression(@Valid @RequestBody DerivativeRequest request) {
    Map<Character, Integer> evalPoints = request.points() == null
        ? Map.of()
        : request.points().entrySet().stream()
            .collect(Collectors.toMap(
                e -> e.getKey().charAt(0),
                Map.Entry::getValue));
    return calculatorServiceImpl.evaluateExpression(request.expression(), evalPoints);
  }
}
