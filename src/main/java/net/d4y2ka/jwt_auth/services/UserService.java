package net.d4y2ka.jwt_auth.services;

import net.d4y2ka.jwt_auth.entities.User;
import net.d4y2ka.jwt_auth.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getById(UUID id) {
    return userRepository.findById(id);
  }

  public Optional<User> getByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User save(User user) {
    String password = user.getPassword();
    String hashPassword = passwordEncoder.encode(password);
    user.setPassword(hashPassword);
    return userRepository.save(user);
  }
}
