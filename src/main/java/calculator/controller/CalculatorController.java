package calculator.controller;

import calculator.DTO.DerivativeResponse;
import calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorServiceImpl;

    @GetMapping("/health")
    @ResponseStatus( HttpStatus.OK )
    public String healthCheck(){
        return LocalTime.now().toString();
    }

    @GetMapping(value = "/derivative")
    @ResponseStatus( HttpStatus.OK )
    public DerivativeResponse generateDerivative(@RequestParam("expression") String expression, @RequestParam("points")List<Integer> points){
        return calculatorServiceImpl.evaluateDerivative(expression, points);
    }

    @GetMapping(value = "/expression")
    @ResponseStatus( HttpStatus.OK )
    public double generateDerivative(@RequestParam("expression") String expression){
        return calculatorServiceImpl.evaluateExpression(expression);
    }
}
