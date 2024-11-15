package com.hancloud.hancloud.group.dto.entity;

import com.hancloud.hancloud.member.dto.entity.ApiMember;
import jakarta.persistence.*;
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
                @UniqueConstraint(columnNames = {"api_member_id", "group_id"})
        }
)
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApiMember apiMember;

    @ManyToOne
    private Group group;
}
