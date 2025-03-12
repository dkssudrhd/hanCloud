package com.hancloud.hancloud.member.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.member.dto.entity.Member;

/**
 * 멤버관련 레포지토리
 *
 * @author 한민기
 */
public interface MemberRepository extends JpaRepository<Member, UUID> {
	/**
	 * 이메일 존재 여부 확인
	 *
	 * @param email 이메일
	 * @return 존재 여부
	 */
	boolean existsByEmail(String email);

	/**
	 * 이름 존재 여부 확인
	 *
	 * @param name 이름
	 * @return 존재 여부
	 */
	boolean existsByName(String name);

	Optional<Member> findByEmail(String email);
}
