package com.hancloud.hancloud.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.member.dto.entity.ApiMember;

/**
 * 멤버관련 레포지토리
 *
 * @author 한민기
 */
public interface ApiMemberRepository extends JpaRepository<ApiMember, String> {
}
