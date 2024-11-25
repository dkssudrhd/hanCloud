package com.hancloud.hancloud.storage.repository;

import com.hancloud.hancloud.member.dto.entity.Member;
import com.hancloud.hancloud.storage.dto.entity.HanStorage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 팀 관련 레포지토리
 *
 * @author 한민기
 */
public interface HanStorageRepository extends JpaRepository<HanStorage, Long> {
    boolean existsByPath(String path);
}
