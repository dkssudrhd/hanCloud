package com.hancloud.hancloud.member.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @Email
    String email;
    String password;

    @OneToOne(mappedBy = "member")
    ApiMember apiMember;
}
