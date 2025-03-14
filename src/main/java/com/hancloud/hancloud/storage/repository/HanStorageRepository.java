package com.hancloud.hancloud.storage.repository;

import com.hancloud.hancloud.storage.dto.entity.HanStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

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
