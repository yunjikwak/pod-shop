package com.example.demo.controller.product.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductCreateRequestDto {
    @Size(max = 20)
    private String name;
    private Integer baseId;

    @Size(min = 1, max = 5)
//    private List<Integer> imageIds; // 아이디 -> X, 아직 생성 전임
    private List<String> images; // MultipartFile??

}
