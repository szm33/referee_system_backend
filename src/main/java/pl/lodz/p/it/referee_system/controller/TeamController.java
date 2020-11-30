package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.TeamCreateDTO;
import pl.lodz.p.it.referee_system.dto.TeamListDTO;
import pl.lodz.p.it.referee_system.entity.League;
import pl.lodz.p.it.referee_system.mapper.TeamMapper;
import pl.lodz.p.it.referee_system.service.LeagueService;
import pl.lodz.p.it.referee_system.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private LeagueService leagueService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TeamListDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams().stream()
                .map(TeamListDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TeamListDTO> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(new TeamListDTO(teamService.getTeam(id)));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity addTeam(@RequestBody TeamCreateDTO team) {
        teamService.addTeam(TeamMapper.map(team));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity editTeam(@RequestBody TeamListDTO team) {
        teamService.editTeam(TeamMapper.map(team));
        return ResponseEntity.ok().build();
    }

    @GetMapping("league")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<League>> getAllLeagues() {
        return ResponseEntity.ok(leagueService.getAllLeagues());
    }
}
