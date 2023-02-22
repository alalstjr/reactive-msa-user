package com.jjunpro.reactive.domain.team.dto;

import com.jjunpro.reactive.domain.team.Team;
import com.jjunpro.reactive.domain.user.dto.GetUserDto;
import java.util.List;

public record GetTeamDto(String id, String name, List<GetUserDto> members) {

    public Team toTeam() {
        return Team.builder()
                   .id(id)
                   .name(name)
                   .members(members.stream().map(GetUserDto::toUser).toList())
                   .build();
    }
}