package com.hancloud.hancloud.team.dto.entity;

import com.hancloud.hancloud.member.dto.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"member_id", "team_id"})
	}
)
public class TeamMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = false)
	private Team team;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public TeamMember(Team team, Member member) {
		this.team = team;
		this.member = member;
	}
}
