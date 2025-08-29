package com.example.demo.repository.image.entity;

import com.example.demo.repository.product.entity.ProductImage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image")
    private List<ProductImage> productImages;

    public static Image create(String url) {
        return new Image(
                null,
                url,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
    }
}
