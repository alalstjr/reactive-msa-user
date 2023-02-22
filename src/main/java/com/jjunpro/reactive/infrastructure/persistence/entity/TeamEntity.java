package com.jjunpro.reactive.infrastructure.persistence.entity;

import com.jjunpro.reactive.domain.team.Team;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teams")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {

    @Id
    String id;

    String           name;
    List<UserEntity> members;

    public Team toTeam() {
        return Team.builder()
                   .id(id)
                   .name(name)
                   .members(members.stream().map(UserEntity::toUser).toList())
                   .build();
    }
}