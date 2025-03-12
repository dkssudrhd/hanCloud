package com.hancloud.hancloud.storage.dto.entity;

import org.checkerframework.common.aliasing.qual.Unique;

import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import com.hancloud.hancloud.team.dto.entity.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HanStorage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Unique
	String path;
	StorageAuth auth;

	@ManyToOne
	private Team team;

	public HanStorage(String path) {
		this.path = path;
		this.auth = StorageAuth.read;
	}
}
