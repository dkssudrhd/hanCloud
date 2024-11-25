package com.hancloud.hancloud.team.dto.entity;

import com.hancloud.hancloud.storage.dto.entity.HanStorage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<HanStorage> hanStorageList = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMemberList = new ArrayList<>();

    public void addHanStorage(HanStorage hanStorage) {
        hanStorageList.add(hanStorage);
        hanStorage.setTeam(this);
    }

    public Team(String name) {
        this.name = name;
    }
}
