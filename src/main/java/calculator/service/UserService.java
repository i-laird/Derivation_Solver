package calculator.service;

import calculator.UserRepository;
import calculator.exception.UserAlreadyExistsException;
import calculator.model.User;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public User register(String email, String password, String role) {
    Optional<User> optionalUser = userRepository.findById(email);
    if (optionalUser.isPresent()) {
      throw new UserAlreadyExistsException("User Exists");
    }
    User newUser = new User();
    newUser.setUsername(email);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setRole(role);
    return userRepository.save(newUser);
  }
}
