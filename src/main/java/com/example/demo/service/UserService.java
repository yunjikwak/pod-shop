package com.example.demo.service;

import com.example.demo.controller.user.dto.UserCreateResponseDto;
import com.example.demo.controller.user.dto.UserResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND, id));
        return UserResponseDto.from(user);
    }

    @Transactional
    public UserResponseDto save(UserCreateResponseDto request) {

        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new CustomException(ExceptionType.DUPLICATED_LOGIN_ID, request.getLoginId());
        }

        String hash = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                request.getLoginId(),
                hash,
                request.getName()
        );
        User created = userRepository.save(user);
        return UserResponseDto.from(created);
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
