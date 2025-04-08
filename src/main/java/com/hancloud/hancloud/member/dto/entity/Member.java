package com.hancloud.hancloud.member.dto.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hancloud.hancloud.member.dto.enums.MemberAuth;
import com.hancloud.hancloud.member.dto.request.CreateMemberRequest;
import com.hancloud.hancloud.team.dto.entity.TeamMember;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private static final int DEFAULT_COUNT = 50000;
	private static final boolean DEFAULT_SITUATION = true;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	String password;
	@Email
	@Unique
	String email;

	@Unique
	String name;

	int count = DEFAULT_COUNT;

	MemberAuth auth;

	Boolean situation = DEFAULT_SITUATION;

	@OneToMany(mappedBy = "member")
	List<ApiMember> apiMemberList = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	List<TeamMember> teamMemberList = new ArrayList<>();

	public Member(CreateMemberRequest request, PasswordEncoder passwordEncoder) {
		this.email = request.email();
		this.password = passwordEncoder.encode(request.password());
		this.name = request.name();
		this.auth = MemberAuth.user;
	}
}
