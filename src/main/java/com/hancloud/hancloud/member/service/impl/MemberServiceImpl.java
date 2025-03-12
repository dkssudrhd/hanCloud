package com.hancloud.hancloud.member.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hancloud.hancloud.member.dto.entity.ApiMember;
import com.hancloud.hancloud.member.dto.entity.Member;
import com.hancloud.hancloud.member.dto.request.CreateMemberRequest;
import com.hancloud.hancloud.member.dto.response.CreatedApiMemberResponse;
import com.hancloud.hancloud.member.exception.MemberAlreadyExistsException;
import com.hancloud.hancloud.member.exception.MemberNotFoundException;
import com.hancloud.hancloud.member.repository.ApiMemberRepository;
import com.hancloud.hancloud.member.repository.MemberRepository;
import com.hancloud.hancloud.member.service.MemberService;
import com.hancloud.hancloud.storage.dto.entity.HanStorage;
import com.hancloud.hancloud.storage.exception.StorageAlreadyExistsException;
import com.hancloud.hancloud.storage.repository.HanStorageRepository;
import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.team.dto.entity.Team;
import com.hancloud.hancloud.team.dto.entity.TeamMember;
import com.hancloud.hancloud.team.exception.TeamMemberAlreadyExistsException;
import com.hancloud.hancloud.team.repository.TeamMemberRepository;
import com.hancloud.hancloud.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final TeamMemberRepository teamMemberRepository;
	private final TeamRepository teamRepository;
	private final HanStorageRepository hanStorageRepository;
	private final FileService fileService;
	private final ApiMemberRepository apiMemberRepository;

	@Transactional
	@Override
	public void addMember(CreateMemberRequest createMemberRequest) {
		Member member = new Member(createMemberRequest, passwordEncoder);
		if (memberRepository.existsByEmail(member.getEmail())) {
			throw new MemberAlreadyExistsException("해당 이메일의 아이디가 있습니다.");
		}
		if (memberRepository.existsByName(member.getName())) {
			throw new MemberAlreadyExistsException("해당 이름의 아이디가 있습니다.");
		}
		memberRepository.save(member);

		Team team = new Team(member.getName());
		teamRepository.save(team);
		if (teamMemberRepository.existsByTeamIdAndMemberId(team.getId(), member.getId())) {
			throw new TeamMemberAlreadyExistsException("해당 멤버는 이미 해당 팀에 들어가 있습니다.");
		}
		teamMemberRepository.save(new TeamMember(team, member));

		String path = "/" + member.getName();
		if (hanStorageRepository.existsByPath(path)) {
			throw new StorageAlreadyExistsException("해당 이름은 사용할 수 없습니다.");
		}

		HanStorage hanStorage = new HanStorage(path);
		team.addHanStorage(hanStorage);
		hanStorageRepository.save(hanStorage);

		fileService.storageAdd(path);
	}

	@Override
	public CreatedApiMemberResponse addApiMember(String memberId) {
		Member member = memberRepository.findById(UUID.fromString(memberId))
			.orElseThrow(() -> new MemberNotFoundException("멤버를 찾지 못했습니다."));
		ApiMember apiMember = new ApiMember(member);
		String apiPassword = apiMember.getPassword();
		apiMember.setPassword(passwordEncoder.encode(apiPassword));
		apiMemberRepository.save(apiMember);

		return CreatedApiMemberResponse.builder()
			.apiId(apiMember.getId())
			.apiPassword(apiPassword)
			.build();
	}
}
