package calculator.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtTokenUtil.class)
@TestPropertySource(
    properties =
        "jwt.secret=dGVzdC1zZWNyZXQta2V5LWZvci11bml0LXRlc3RpbmctcHVycG9zZXMtb25seS1iYXNlNjQ=")
class JwtTokenUtilTest {

  @Autowired JwtTokenUtil jwtTokenUtil;

  private static UserDetails testUser() {
    return User.withUsername("testuser@example.com")
        .password("{noop}password")
        .roles("USER")
        .build();
  }

  @Test
  void should_generateNonEmptyToken_when_userProvided() {
    String token = jwtTokenUtil.generateToken(testUser());
    assertNotNull(token);
    assertFalse(token.isBlank());
  }

  @Test
  void should_returnCorrectUsername_when_tokenGenerated() {
    UserDetails user = testUser();
    String token = jwtTokenUtil.generateToken(user);
    assertEquals("testuser@example.com", jwtTokenUtil.getUsernameFromToken(token));
  }

  @Test
  void should_validateToken_when_userMatches() {
    UserDetails user = testUser();
    String token = jwtTokenUtil.generateToken(user);
    assertTrue(jwtTokenUtil.validateToken(token, user));
  }
}
