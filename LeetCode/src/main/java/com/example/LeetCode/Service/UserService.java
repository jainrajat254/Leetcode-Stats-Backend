package com.example.LeetCode.Service;

import com.example.LeetCode.Model.LoginCredentials;
import com.example.LeetCode.Model.LoginResponse;
import com.example.LeetCode.Model.Users;
import com.example.LeetCode.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public LoginResponse login(LoginCredentials credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));

        if (authentication.isAuthenticated()) {
            Optional<Users> user = Optional.ofNullable(userRepository.findByUsername(credentials.getUsername()));

            if (user.isPresent()) {
                Users userEntity = user.get();

                String token = jwtService.generateToken(credentials.getUsername());

                return new LoginResponse(userEntity.getName(), userEntity.getSelectedLanguage(), userEntity.getUsername(), token);
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public List<String> getAllUsernames() {
        return userRepository.findAllUsernames();
    }
}
