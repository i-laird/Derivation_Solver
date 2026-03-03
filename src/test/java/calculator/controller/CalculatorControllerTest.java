package calculator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import calculator.DTO.DerivativeRequest;
import calculator.DTO.DerivativeResponse;
import calculator.security.JwtAuthenticationEntryPoint;
import calculator.security.JwtRequestFilter;
import calculator.security.JwtTokenUtil;
import calculator.security.WebSecurityConfig;
import calculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  @MockBean private CalculatorService calculatorServiceImpl;

  @MockBean private UserDetailsService jwtUserDetailsService;

  @MockBean private PasswordEncoder passwordEncoder;

  @Test
  void should_return200_when_validDerivativeRequest() throws Exception {
    DerivativeResponse mockResponse = new DerivativeResponse("x", 1.0);
    when(calculatorServiceImpl.evaluateDerivative(anyString(), any())).thenReturn(mockResponse);

    DerivativeRequest request = new DerivativeRequest("x", List.of(1));
    mockMvc
        .perform(
            get("/derivative")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void should_return400_when_blankExpression() throws Exception {
    DerivativeRequest request = new DerivativeRequest("", List.of());
    mockMvc
        .perform(
            get("/derivative")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}
