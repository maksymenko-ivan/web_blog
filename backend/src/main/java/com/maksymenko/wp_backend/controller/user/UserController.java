package com.maksymenko.wp_backend.controller.user;

import com.maksymenko.wp_backend.model.authentication.AuthenticationResponse;
import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.repository.authentication.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserDetails(@AuthenticationPrincipal User authUser) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setId(null);
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }


    @PostMapping("/change-first-name")
    public ResponseEntity<AuthenticationResponse> changeFirstName(@RequestParam("firstName") String firstName, @AuthenticationPrincipal User authUser) {
        try {
            User user = userRepository.findById(authUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setFirstName(firstName);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-last-name")
    public ResponseEntity<AuthenticationResponse> changeLastName(@RequestParam("lastName") String lastName, @AuthenticationPrincipal User authUser) {
        try {
            User user = userRepository.findById(authUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setLastName(lastName);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestParam("password") String password, @AuthenticationPrincipal User authUser) {
        try {
            User user = userRepository.findById(authUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-username")
    public ResponseEntity<AuthenticationResponse> changeUsername(@RequestParam("username") String username, @AuthenticationPrincipal User authUser) {
        try {
            User user = userRepository.findById(authUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setUsername(username);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
