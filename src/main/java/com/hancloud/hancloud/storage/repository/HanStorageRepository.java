package com.hancloud.hancloud.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.storage.dto.entity.HanStorage;

/**
 * 팀 관련 레포지토리
 *
 * @author 한민기
 */
public interface HanStorageRepository extends JpaRepository<HanStorage, Long> {
	boolean existsByPath(String path);
}
