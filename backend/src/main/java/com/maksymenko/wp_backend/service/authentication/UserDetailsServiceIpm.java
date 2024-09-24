package com.maksymenko.wp_backend.service.authentication;


import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.repository.authentication.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceIpm implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceIpm(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with such id was not found"));
    }
}
