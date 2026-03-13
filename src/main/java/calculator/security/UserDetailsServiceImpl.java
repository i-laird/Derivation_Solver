/* (C)2022 */
package calculator.security;

import calculator.UserRepository;
import calculator.model.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("dev")
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * loads a user from the DB by username
   *
   * @param username the username
   * @return the user
   * @throws UsernameNotFoundException if user not found
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findById(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Username not found");
    }

    // set the username of the user
    org.springframework.security.core.userdetails.User.UserBuilder userBuilder =
        org.springframework.security.core.userdetails.User.withUsername(username);
    // set the password of the user
    userBuilder.password(user.get().getPassword());
    userBuilder.roles(user.get().getRole());

    // build the user
    return userBuilder.build();
  }
}
