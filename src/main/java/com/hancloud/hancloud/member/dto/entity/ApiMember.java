package com.hancloud.hancloud.member.dto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiMember {
    @Id
    String id;
    String password;

    @ManyToOne
    Member member;

    /**
     * id 와 password 랜덤 생성
     */
    public void generateIdPassword() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = generateRandom();
        }
        if (this.password == null || this.password.isEmpty()) {
            this.password = generateRandom();
        }
    }

    private String generateRandom() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public ApiMember(Member member) {
        this.member = member;
        generateIdPassword();
    }
}
