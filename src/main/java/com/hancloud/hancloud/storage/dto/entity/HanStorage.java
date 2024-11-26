package com.hancloud.hancloud.storage.dto.entity;

import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import com.hancloud.hancloud.team.dto.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;

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
