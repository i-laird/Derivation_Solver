package calculator.controller;

import calculator.DTO.DerivativeRequest;
import calculator.DTO.DerivativeResponse;
import calculator.service.CalculatorService;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
Controller handling calculator requests.
 */
@RestController
public final class CalculatorController {

  @Autowired private CalculatorService calculatorServiceImpl;

  /**
   * Health check for the calculator.
   */
  @GetMapping("/health")
  @ResponseStatus(HttpStatus.OK)
  public String healthCheck() {
    return LocalTime.now().toString();
  }

  /**
   * Calculates the anti-derivative of a mathematical expression and then evaluates it at a specific point.
   */
  @GetMapping(value = "/derivative")
  @ResponseStatus(HttpStatus.OK)
  public DerivativeResponse generateDerivative(
          @RequestBody DerivativeRequest request) {
    return calculatorServiceImpl.evaluateDerivative(request.getExpression(), request.getPoints());
  }

  /**
   * Evaluates a mathematical expression.
   */
  @GetMapping(value = "/expression")
  @ResponseStatus(HttpStatus.OK)
  public double generateDerivative(@RequestParam("expression") final String expression) {
    return calculatorServiceImpl.evaluateExpression(expression);
  }
}
