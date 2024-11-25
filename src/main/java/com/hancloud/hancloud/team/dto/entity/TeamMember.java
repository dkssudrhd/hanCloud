package com.hancloud.hancloud.team.dto.entity;


import com.hancloud.hancloud.member.dto.entity.Member;
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
                @UniqueConstraint(columnNames = {"member_id", "team_id"})
        }
)
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Member member;

    public TeamMember(Team team, Member member) {
        this.team = team;
        this.member = member;
    }

}
