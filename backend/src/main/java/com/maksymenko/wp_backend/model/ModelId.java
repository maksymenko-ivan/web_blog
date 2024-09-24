package com.maksymenko.wp_backend.model;

import jakarta.persistence.*;

@MappedSuperclass
public class ModelId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
