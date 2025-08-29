package com.example.demo.controller.product.dto;

import com.example.demo.repository.image.entity.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageSimpleDto {
    private Integer id;
    private String url;

    public static ImageSimpleDto from(Image entity) {
        return new ImageSimpleDto(
                entity.getId(),
                entity.getUrl()
        );
    }
}
