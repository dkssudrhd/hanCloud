package com.hancloud.hancloud.member.service.impl;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hancloud.hancloud.member.dto.entity.ApiMember;
import com.hancloud.hancloud.member.dto.entity.Member;
import com.hancloud.hancloud.member.dto.request.LoginRequest;
import com.hancloud.hancloud.member.dto.response.LoginResponse;
import com.hancloud.hancloud.member.exception.ApiLoginMatchFailedException;
import com.hancloud.hancloud.member.exception.ApiMemberNotFoundException;
import com.hancloud.hancloud.member.exception.MemberNotFoundException;
import com.hancloud.hancloud.member.repository.ApiMemberRepository;
import com.hancloud.hancloud.member.service.ApiLoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiLoginServiceImpl implements ApiLoginService {
	private final ApiMemberRepository apiMemberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	@Override
	public LoginResponse login(String id) {
		ApiMember apiMember = apiMemberRepository.findById(id).orElseThrow(
			() -> new ApiMemberNotFoundException(id + "에 해당하는 api member 를 찾을 수 없습니다.")
		);

		return getLoginResponseToApiMember(apiMember);
	}

	@Transactional(readOnly = true)
	@Override
	public LoginResponse loginChecking(LoginRequest loginRequest) {
		ApiMember apiMember = apiMemberRepository.findById(loginRequest.id()).orElseThrow(
			() -> new ApiMemberNotFoundException(loginRequest.id() + "에 해당하는 api member 를 찾을 수 없습니다.")
		);

		if (!passwordEncoder.matches(loginRequest.password(), apiMember.getPassword())) {
			throw new ApiLoginMatchFailedException("Api 의 비밀번호가 틀렸습니다.");
		}

		return getLoginResponseToApiMember(apiMember);
	}

	private LoginResponse getLoginResponseToApiMember(ApiMember apiMember) {
		Member member = apiMember.getMember();
		if (member == null) {
			throw new MemberNotFoundException("멤버를 찾을 수 없습니다.");
		}

		return LoginResponse.builder()
			.id(apiMember.getId())
			.password(apiMember.getPassword())
			.memberId(member.getId().toString())
			.email(member.getEmail())
			.name(member.getName())
			.count(member.getCount())
			.role(List.of(new SimpleGrantedAuthority(member.getAuth().toString())))
			.build();
	}
}
