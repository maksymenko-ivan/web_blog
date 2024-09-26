package com.maksymenko.wp_backend.service.authentication;


import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.model.images.Image;
import com.maksymenko.wp_backend.repository.authentication.UserRepository;
import com.maksymenko.wp_backend.repository.images.ImageRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceIpm implements UserDetailsService {

    private final UserRepository userRepository;
    private ImageRepository imageRepository;

    public UserDetailsServiceIpm(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with such id was not found"));
    }

    @Transactional
    public void addImageToUser(Long userId, Long imageId) {
        User user = userRepository.findById(userId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        user.setImage(image);
        userRepository.save(user);
    }
}
