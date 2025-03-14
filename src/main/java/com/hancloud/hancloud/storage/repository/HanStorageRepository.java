package com.hancloud.hancloud.storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hancloud.hancloud.storage.dto.entity.HanStorage;

/**
 * 저장 관련 레포지토리
 *
 * @author 한민기
 */
public interface HanStorageRepository extends JpaRepository<HanStorage, Long>,
	HanStorageCustomRepository {
	boolean existsByPath(String path);

	Optional<HanStorage> findByPath(String path);
}
