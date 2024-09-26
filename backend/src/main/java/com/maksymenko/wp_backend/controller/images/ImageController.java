package com.maksymenko.wp_backend.controller.images;

import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.repository.authentication.UserRepository;
import com.maksymenko.wp_backend.service.images.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api/v1/")
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;

    public ImageController(ImageService imageService, UserRepository userRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@AuthenticationPrincipal User authUser, @RequestParam("image") MultipartFile file) throws IOException {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        imageService.uploadImage(file, user);
        return ResponseEntity.status(HttpStatus.OK).body("Uploaded successfully");
    }

    @GetMapping("/download-avatar")
    public ResponseEntity<?> downloadImage(@AuthenticationPrincipal User authUser){
        byte[] imageData = null;
        try {

            imageData = imageService.downloadImage(authUser.getId().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}