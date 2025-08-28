package com.example.demo.repository.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String loginId;

    private String password; // hash
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Product OneToMany
    // Image OneToMany

    public static User create(String loginId, String password, String name) {
        return new User(
                null,
                loginId,
                password,
                name,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
