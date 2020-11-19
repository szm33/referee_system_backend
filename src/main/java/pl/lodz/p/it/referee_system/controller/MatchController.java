package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.*;
import pl.lodz.p.it.referee_system.entity.MatchFunction;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.mapper.MatchMapper;
import pl.lodz.p.it.referee_system.service.MatchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<MatchToEditDTO> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(new MatchToEditDTO(matchService.getMatch(id), matchService.getAllMatchFunctions()));
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

    @PutMapping
    public ResponseEntity modifyMatch(@RequestBody MatchToEditDTO match) {
        matchService.editMatch(MatchMapper.map(match));
        return ResponseEntity.ok().build();
    }

    @PostMapping("replace/{matchId}")
    public ResponseEntity initReplacement(@PathVariable Long matchId) {
        matchService.initReplacement(matchId);
        //wysylanie maili do wszystkich
        return ResponseEntity.ok().build();
    }

    @PostMapping("arrivalTime")
    public ResponseEntity registerArrivalTime(@RequestBody MatchArrivalTimeDTO replaceInformations) {
        matchService.registerArrivalTime(ReplaceInformations.builder()
                .id(replaceInformations.getId())
                .arrivalTime(replaceInformations.getArrivalTime())
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("arrivalTime/{id}")
    public ResponseEntity<ReplaceInformationsDTO> getReplaceInformations(@PathVariable Long id) {
        return ResponseEntity.ok(new ReplaceInformationsDTO(matchService.getReplaceInformations(id)));
    }

}
