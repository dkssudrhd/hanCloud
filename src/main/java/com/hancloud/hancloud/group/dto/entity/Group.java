package com.hancloud.hancloud.group.dto.entity;

import com.hancloud.hancloud.member.dto.entity.ApiMember;
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
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<HanStorage> hanStorageList = new ArrayList<>();
}
