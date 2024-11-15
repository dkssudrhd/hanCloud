package com.hancloud.hancloud.member.dto.entity;

import com.hancloud.hancloud.group.dto.entity.GroupMember;
import com.hancloud.hancloud.member.dto.enums.MemberAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiMember {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    UUID password;

    MemberAuth auth;

    @OneToOne
    Member member;

    @OneToMany(mappedBy = "apiMember")
    List<GroupMember> groupMemberList = new ArrayList<>();
}
