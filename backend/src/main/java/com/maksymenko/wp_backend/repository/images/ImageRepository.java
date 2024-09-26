package com.maksymenko.wp_backend.repository.images;

import com.maksymenko.wp_backend.model.images.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);
}