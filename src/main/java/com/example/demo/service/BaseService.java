package com.example.demo.service;

import com.example.demo.repository.product.BaseRepository;
import com.example.demo.repository.product.entity.Base;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseService {
    private final BaseRepository baseRepository;

    @PostConstruct // 호출 X 자동 실행
    public void init() {
        baseRepository.save(Base.create("컵",2000.0));
        baseRepository.save(Base.create("우산",5000.0));
        baseRepository.save(Base.create("텀블러",3000.0));
    }
}
