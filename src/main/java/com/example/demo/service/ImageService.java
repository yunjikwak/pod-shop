package com.example.demo.service;

import com.example.demo.controller.product.dto.ImageSimpleDto;
import com.example.demo.repository.image.ImageRepository;
import com.example.demo.repository.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public Image save(String url) {
        Image image = Image.create(url);
        Image created = imageRepository.save(image);
        return created; // 꼭 dto로 반환해야하나?
    }
}
