package calculator.controller;

import calculator.DTO.JwtRequest;
import calculator.DTO.JwtResponse;
import calculator.DTO.UserGeneration;
import calculator.security.JwtTokenUtil;
import calculator.service.UserService;
import jakarta.validation.Valid;
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

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;

  public UserController(
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService,
      UserService userService,
      JwtTokenUtil jwtTokenUtil) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  /**
   * Registers a user.
   *
   * @param u the user to register.
   * @return JWT for the registered user.
   * @throws DisabledException if the user account is disabled.
   * @throws BadCredentialsException if the credentials are invalid.
   */
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserGeneration u)
      throws DisabledException, BadCredentialsException {
    userService.register(u.getEmail(), u.getPassword(), "STANDARD");
    return createAuthenticationToken(new JwtRequest(u.getEmail(), u.getPassword()));
  }

  /**
   * Logins in a user.
   *
   * @param authenticationRequest the users login credentials.
   * @return JWT for the logged in user.
   * @throws DisabledException if the user account is disabled.
   * @throws BadCredentialsException if the credentials are invalid.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws DisabledException, BadCredentialsException {
    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String username, String password)
      throws DisabledException, BadCredentialsException {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
