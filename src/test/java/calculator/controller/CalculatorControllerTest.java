package calculator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import calculator.DTO.DerivativeRequest;
import calculator.DTO.DerivativeResponse;
import calculator.security.JwtAuthenticationEntryPoint;
import calculator.security.JwtRequestFilter;
import calculator.security.JwtTokenUtil;
import calculator.security.WebSecurityConfig;
import calculator.service.CalculatorService;
import tools.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalculatorController.class)
@Import({
  WebSecurityConfig.class,
  JwtRequestFilter.class,
  JwtAuthenticationEntryPoint.class,
  JwtTokenUtil.class
})
@TestPropertySource(
    properties =
        "jwt.secret=dGVzdC1zZWNyZXQta2V5LWZvci11bml0LXRlc3RpbmctcHVycG9zZXMtb25seS1iYXNlNjQ=")
class CalculatorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CalculatorService calculatorServiceImpl;

  @MockitoBean private UserDetailsService jwtUserDetailsService;

  @MockitoBean private PasswordEncoder passwordEncoder;

  @Test
  void should_return200_and_derivative_response_body_when_validDerivativeRequest()
      throws Exception {
    DerivativeResponse mockResponse = new DerivativeResponse("2*x", 4.0);
    when(calculatorServiceImpl.evaluateDerivative(anyString(), any(char.class), any())).thenReturn(mockResponse);

    DerivativeRequest request = new DerivativeRequest("x^2", "x", Map.of("x", 2));
    mockMvc
        .perform(
            post("/derivative")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.antiderivative").value("2*x"))
        .andExpect(jsonPath("$.result").value(4.0));
  }

  @Test
  void should_return400_when_blankExpression_on_derivative() throws Exception {
    DerivativeRequest request = new DerivativeRequest("", "x", Map.of("x", 1));
    mockMvc
        .perform(
            post("/derivative")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return200_and_value_when_validExpressionRequest() throws Exception {
    when(calculatorServiceImpl.evaluateExpression(anyString(), any())).thenReturn(7.0);

    DerivativeRequest request = new DerivativeRequest("2*x + 1", "x", Map.of("x", 3));
    mockMvc
        .perform(
            post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(7.0));
  }

  @Test
  void should_return400_when_blankExpression_on_expression() throws Exception {
    DerivativeRequest request = new DerivativeRequest("", "x", Map.of("x", 1));
    mockMvc
        .perform(
            post("/expression")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return400_when_expression_null_on_derivative() throws Exception {
    mockMvc
        .perform(
            post("/derivative")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": null, \"withRespectTo\": \"x\", \"points\": {\"x\": 1}}"))
        .andExpect(status().isBadRequest());
  }
}
