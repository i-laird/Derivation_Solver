package calculator.controller;

import calculator.DTO.JwtRequest;
import calculator.DTO.JwtResponse;
import calculator.DTO.UserGeneration;
import calculator.security.JwtTokenUtil;
import calculator.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Controller for user management. */
@RestController
public final class UserController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserDetailsService userDetailsService;

  @Autowired private UserService userService;

  /**
   * Registers a user.
   *
   * @param u the user to register.
   * @return JWT for the registered user.
   * @throws Exception if the user already exists.
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserGeneration u) throws Exception {
    userService.register(u.getEmail(), u.getPassword(), "STANDARD");
    return createAuthenticationToken(new JwtRequest(u.getEmail(), u.getPassword()));
  }

  /**
   * Logins in a user.
   *
   * @param authenticationRequest the users login credentials.
   * @return JWT for the logged in user.
   * @throws Exception If login fails.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws Exception {
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = JwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }
}
