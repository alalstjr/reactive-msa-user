package com.jjunpro.reactive.domain.team;

import com.jjunpro.reactive.domain.team.dto.GetTeamDto;
import com.jjunpro.reactive.domain.user.User;
import com.jjunpro.reactive.infrastructure.persistence.entity.TeamEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Team {

    String     id;
    String     name;
    List<User> members;

    public Team withMembers(List<User> newMembers) {
        return Team
            .builder()
            .id(id)
            .name(name)
            .members(newMembers)
            .build();
    }

    public TeamEntity toEntity() {
        return TeamEntity
            .builder()
            .id(id)
            .name(name)
            .members(members.stream().map(User::toEntity).toList())
            .build();
    }

    public GetTeamDto toGetTeamDto() {
        return new GetTeamDto(
            id,
            name,
            members.stream().map(User::toGetUserDto).toList()
        );
    }
}