package com.hancloud.hancloud.storage.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hancloud.hancloud.member.dto.entity.Member;
import com.hancloud.hancloud.member.dto.enums.MemberAuth;
import com.hancloud.hancloud.member.exception.MemberNotFoundException;
import com.hancloud.hancloud.member.repository.MemberRepository;
import com.hancloud.hancloud.storage.dto.entity.HanStorage;
import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import com.hancloud.hancloud.storage.dto.response.MemberPathResponse;
import com.hancloud.hancloud.storage.exception.FileNotFoundException;
import com.hancloud.hancloud.storage.repository.HanStorageRepository;
import com.hancloud.hancloud.storage.service.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
	private final HanStorageRepository hanStorageRepository;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	@Override
	public MemberPathResponse canUse(String memberId, String path) {
		String bucketPath = path.split("/")[0];
		HanStorage hanStorage = hanStorageRepository.findByPath(bucketPath).orElseThrow(
			() -> new FileNotFoundException("파일을 찾을 수 없습니다."));

		if (Objects.isNull(memberId)) {
			return MemberPathResponse.builder()
				.nowLogin(false)
				.auth(hanStorage.getAuth().name())
				.build();
		}
		Member member = memberRepository.findById(UUID.fromString(memberId)).orElseThrow(
			() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

		if (member.getAuth().equals(MemberAuth.admin)) {
			log.info("admin");
		}
		if (member.getAuth().equals(MemberAuth.admin) || hanStorageRepository.useCan(memberId, bucketPath)) {
			return MemberPathResponse.builder()
				.nowLogin(true)
				.auth(StorageAuth.all.name())
				.build();
		}

		return MemberPathResponse.builder()
			.nowLogin(true)
			.auth(hanStorage.getAuth().name())
			.build();
	}
}
