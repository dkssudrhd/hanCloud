package com.hancloud.hancloud.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.team.dto.entity.Team;

/**
 * 팀 관련 레포지토리
 *
 * @author 한민기
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
