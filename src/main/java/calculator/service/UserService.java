package calculator.service;

import calculator.UserRepository;
import calculator.model.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private UserRepository userRepository;

  public User register(String email, String password, String role) {
    Optional<User> optionalUser = userRepository.findById(email);
    if (optionalUser.isPresent()) {
      throw new RuntimeException("User Exists");
    }
    User newUser = new User();
    newUser.setUsername(email);
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setRole(role);
    return userRepository.save(newUser);
  }
}
