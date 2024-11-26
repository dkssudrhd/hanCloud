package com.hancloud.hancloud.team.repository;

import com.hancloud.hancloud.team.dto.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * 팀 관련 레포지토리
 *
 * @author 한민기
 */
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    boolean existsByTeamIdAndMemberId(long team_id, UUID member_id);
}
