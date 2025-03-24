package com.hancloud.hancloud.storage.repository.impl;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hancloud.hancloud.member.dto.entity.QMember;
import com.hancloud.hancloud.storage.dto.entity.QHanStorage;
import com.hancloud.hancloud.storage.repository.HanStorageCustomRepository;
import com.hancloud.hancloud.team.dto.entity.QTeam;
import com.hancloud.hancloud.team.dto.entity.QTeamMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HanStorageCustomRepositoryImpl implements HanStorageCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QMember qMember = QMember.member;
	private final QTeamMember qTeamMember = QTeamMember.teamMember;
	private final QTeam qTeam = QTeam.team;
	private final QHanStorage qHanStorage = QHanStorage.hanStorage;

	public HanStorageCustomRepositoryImpl(EntityManager entityManager) {
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Transactional
	@Override
	public boolean isMemberPathCan(String memberId, String path) {

		UUID uuid = UUID.fromString(memberId);

		Integer result = jpaQueryFactory
			.selectOne() // 존재 여부만 확인할 때 selectOne() 사용 가능
			.from(qTeamMember)
			.join(qTeamMember.member, qMember)
			.join(qTeamMember.team, qTeam)
			.join(qHanStorage).on(qHanStorage.team.id.eq(qTeam.id))
			.where(
				qMember.id.eq(uuid)
					.and(qHanStorage.path.eq(path))
			)
			.fetchFirst(); // 하나의 결과만 확인

		return result != null;
	}
}
