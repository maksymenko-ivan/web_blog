package com.maksymenko.wp_backend.model.images;

import com.maksymenko.wp_backend.model.ModelId;
import com.maksymenko.wp_backend.model.authentication.User;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "images")
public class Image extends ModelId {

    @Column(nullable = false)
    private String type;

    @Lob  // This annotation indicates that the field should be treated as a Large Object
    @Basic(fetch = FetchType.LAZY)  // This annotation is optional, used to specify that the image data should be fetched lazily
    private byte[] imageData;

    @OneToOne(mappedBy = "image")
    private User user;


    // Constructors, getters, and setters
    public Image() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
