package calculator.controller;

import calculator.DTO.DerivativeRequest;
import calculator.DTO.DerivativeResponse;
import calculator.service.CalculatorService;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

  @Autowired private CalculatorService calculatorServiceImpl;

  @GetMapping("/health")
  @ResponseStatus(HttpStatus.OK)
  public String healthCheck() {
    return LocalTime.now().toString();
  }

  @GetMapping(value = "/derivative")
  @ResponseStatus(HttpStatus.OK)
  public DerivativeResponse generateDerivative(
          @RequestBody DerivativeRequest request) {
    return calculatorServiceImpl.evaluateDerivative(request.expression, request.points);
  }

  @GetMapping(value = "/expression")
  @ResponseStatus(HttpStatus.OK)
  public double generateDerivative(@RequestParam("expression") String expression) {
    return calculatorServiceImpl.evaluateExpression(expression);
  }
}
