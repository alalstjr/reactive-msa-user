package com.jjunpro.reactive.application.service;

import com.jjunpro.reactive.application.exception.TeamServiceException;
import com.jjunpro.reactive.domain.team.Team;
import com.jjunpro.reactive.domain.team.TeamUtils;
import com.jjunpro.reactive.domain.team.dto.CreateTeamDto;
import com.jjunpro.reactive.domain.team.dto.GetTeamDto;
import com.jjunpro.reactive.domain.team.repository.TeamRepository;
import com.jjunpro.reactive.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Flux<GetTeamDto> findAllTeams() {
        return teamRepository
            .findAll()
            .flatMap(team -> Flux.just(team.toGetTeamDto()));
    }

    public Mono<GetTeamDto> findById(String teamId) {
        return teamRepository
            .findById(teamId)
            .map(Team::toGetTeamDto)
            .switchIfEmpty(Mono.error(new TeamServiceException("Team with given id doesn't exist")));
    }

    public Mono<GetTeamDto> findByName(String name) {
        return teamRepository
            .findByName(name)
            .map(Team::toGetTeamDto)
            .switchIfEmpty(Mono.error(new TeamServiceException("Team with given name doesn't exist")));
    }

    public Mono<GetTeamDto> addTeam(Mono<CreateTeamDto> createTeamDtoMono) {

        return createTeamDtoMono
            .flatMap(
                createTeamDto ->
                    teamRepository.findByName(createTeamDto.name())
                        .doOnEach(
                            team -> log.info("팀 [" + createTeamDto.name() + "] 을 추가 시도합니다.")
                        )
                        .map(Team::toGetTeamDto)
                        .switchIfEmpty(
                            Mono.defer(
                                () -> createTeamWithMembers(createTeamDto)
                            )
                        )
            );
    }

    private Mono<GetTeamDto> createTeamWithMembers(CreateTeamDto createTeamDto) {

        // 처음에는 팀이 db에 삽입되고 모든 구성원이 새 teamId로 업데이트됩니다.
        return teamRepository
            .save(createTeamDto.toTeam())
            .flatMap(insertedTeam -> {
                var membersToInsert = createTeamDto
                    .members()
                    .stream()
                    .map(createUserDto -> createUserDto.toUser().withTeamId(
                        TeamUtils.toId.apply(insertedTeam)))
                    .toList();

                // 그런 다음 올바른 teamId를 가진 모든 구성원이 db에 저장되고 플럭스는 팀 구성원을 업데이트하는 데 사용되는 목록으로 변환됩니다. 마지막으로 업데이트된 팀은 db에 저장됩니다.
                return userRepository
                    .saveAll(membersToInsert)
                    .collectList()
                    .flatMap(insertedUsers -> teamRepository
                        .save(insertedTeam.withMembers(insertedUsers))
                        .map(Team::toGetTeamDto));
            });
    }

    public Mono<GetTeamDto> deleteTeam(String teamId) {

        return teamRepository
            .findById(teamId)
            .flatMap(team -> {

                // at first, I'm changing teamId of all team members to null

                var membersToUpdate = TeamUtils.toMembers.apply(team)
                                                         .stream()
                                                         .map(member -> member.withTeamId(null))
                                                         .toList();

                // then I'm saving all updated members, deleting team and returning mono of DTO

                return userRepository.saveAll(membersToUpdate)
                                     .then(teamRepository.delete(teamId))
                                     .then(Mono.just(team.toGetTeamDto()));
            })
            .switchIfEmpty(Mono.error(new TeamServiceException("cannot find team to delete")));
    }
}