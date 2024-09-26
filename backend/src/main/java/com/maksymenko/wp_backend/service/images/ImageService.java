package com.maksymenko.wp_backend.service.images;

import com.maksymenko.wp_backend.model.authentication.User;
import com.maksymenko.wp_backend.model.images.Image;
import com.maksymenko.wp_backend.repository.authentication.UserRepository;
import com.maksymenko.wp_backend.repository.images.ImageRepository;
import com.maksymenko.wp_backend.service.authentication.UserDetailsServiceIpm;
import org.hibernate.mapping.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final UserDetailsServiceIpm userDetailsService;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository, UserDetailsServiceIpm userDetailsService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    public String uploadImage(MultipartFile imageFile, User request) throws IOException {
        Image image = new Image();
        image.setType(imageFile.getContentType());
        image.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        imageRepository.save(image);
        userDetailsService.addImageToUser(request.getId(), image.getId());
        return "file uploaded successfully : " + imageFile.getOriginalFilename();

    }

    public byte[] downloadImage(String userId) throws IOException {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        Optional<Image> dbImage = Optional.ofNullable(user.get().getImage());

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new RuntimeException("Error downloading an image", exception);
            }
        }).orElse(null);
    }
}