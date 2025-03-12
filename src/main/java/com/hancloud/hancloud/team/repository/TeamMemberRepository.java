package com.hancloud.hancloud.team.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.team.dto.entity.TeamMember;

/**
 * 팀 관련 레포지토리
 *
 * @author 한민기
 */
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
	boolean existsByTeamIdAndMemberId(long teamId, UUID memberId);
}
