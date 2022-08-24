package calculator.controller;

import calculator.DTO.Response;
import calculator.service.DerivativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
public class DerivativeController {

    @Autowired
    private DerivativeService derivativeService;

    @GetMapping("/health")
    @ResponseStatus( HttpStatus.OK )
    public String healthCheck(){
        return LocalTime.now().toString();
    }

    @GetMapping(value = "/derivative")
    @ResponseStatus( HttpStatus.OK )
    public Response generateDerivative(@RequestParam("expression") String expression, @RequestParam("points")List<Integer> points){
        return derivativeService.evaluate(expression, points);
    }
}
