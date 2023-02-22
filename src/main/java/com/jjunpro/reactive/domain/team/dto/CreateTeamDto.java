package com.jjunpro.reactive.domain.team.dto;

import com.jjunpro.reactive.domain.team.Team;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import java.util.List;

public record CreateTeamDto(String name, List<GetUserDto> members) {

    public Team toTeam() {
        return Team.builder()
                   .name(name)
                   .members(members.stream().map(GetUserDto::toUser).toList())
                   .build();
    }
}