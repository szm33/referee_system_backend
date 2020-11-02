package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.FreeRefereeAndMatchesDTO;
import pl.lodz.p.it.referee_system.dto.MatchCreateDTO;
import pl.lodz.p.it.referee_system.dto.MatchDTO;
import pl.lodz.p.it.referee_system.entity.MatchFunction;
import pl.lodz.p.it.referee_system.mapper.MatchMapper;
import pl.lodz.p.it.referee_system.service.MatchService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("match")
@Transactional(propagation = Propagation.NEVER)
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches().stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(new MatchDTO(matchService.getMatch(id)));
    }

    @GetMapping("referee/{id}")
    public ResponseEntity<List<MatchDTO>> getRefereeMatches(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getRefereeMatches(id).stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("my")
    public ResponseEntity<List<MatchDTO>> getMyMatches() {
        return ResponseEntity.ok(matchService.getMyMatches().stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("free/referees-teams")
    public ResponseEntity<FreeRefereeAndMatchesDTO> getFreeRefereesAndMatches(@RequestBody LocalDate date) {
        return ResponseEntity.ok(new FreeRefereeAndMatchesDTO(matchService.getFreeTeamsAndReferees(date)));
    }

    @PostMapping
    public ResponseEntity createMatch(@RequestBody MatchCreateDTO match) {
        matchService.createMatch(MatchMapper.map(match));
        return ResponseEntity.ok().build();
    }

    @GetMapping("functions")
    public ResponseEntity<List<MatchFunction>> getAllMatchFunctions() {
        return ResponseEntity.ok(matchService.getAllMatchFunctions());
    }

}
