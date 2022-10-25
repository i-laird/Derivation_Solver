import calculator.service.CalculatorService;
import calculator.service.impl.CalculatorServiceImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class TestingConfiguration {

  @Bean
  CalculatorService getCalculatorService() {
    return new CalculatorServiceImpl();
  }
}
