package com.example.LeetCode.Service;

import com.example.LeetCode.Model.*;
import com.example.LeetCode.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

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

    public Users register(Users user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponse login(LoginCredentials credentials) {
        Users user = userRepository.findByUsername(credentials.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Username does not exist");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Incorrect password");
        }
        String token = jwtService.generateToken(credentials.getUsername());
        return new LoginResponse(user.getId(), user.getName(), user.getSelectedLanguage(),user.getYear(), user.getUsername(), token);
    }

    public List<String> getAllUsernames() {
        return userRepository.findAllUsernames();
    }

    public String editPassword(EditPassword request, UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null ) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect current password.");
        }
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password updated successfully";
    }

    public Users editDetails(EditDetails request, UUID id) {
        Users users = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        users.setName(request.getName());
        users.setUsername(request.getUsername());
        users.setYear(request.getYear());
        users.setSelectedLanguage(request.getSelectedLanguage());
        return userRepository.save(users);
    }
}
