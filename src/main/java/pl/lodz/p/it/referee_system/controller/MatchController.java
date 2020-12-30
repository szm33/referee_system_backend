package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.*;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.MatchFunction;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.entity.ReplacementCandidate;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.mapper.MatchMapper;
import pl.lodz.p.it.referee_system.service.MatchService;

import javax.validation.Valid;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MatchDTO>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches().stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MatchToEditDTO> getMatch(@PathVariable Long id) {
        Match match = matchService.getMatch(id);
        return ResponseEntity.ok(new MatchToEditDTO(match, matchService.getFreeTeamsAndReferees(match.getDateOfMatch()).getSecond()));
    }

    @GetMapping("referee/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<MatchDTO>> getRefereeMatches(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getRefereeMatches(id).stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MatchDTO>> getMyMatches() {
        return ResponseEntity.ok(matchService.getMyMatches().stream().map(MatchDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("free/referees-teams")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<FreeRefereeAndMatchesDTO> getFreeRefereesAndMatches(@RequestBody LocalDate date) {
        return ResponseEntity.ok(new FreeRefereeAndMatchesDTO(matchService.getFreeTeamsAndReferees(date)));
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<MatchDTO> createMatch(@Valid @RequestBody MatchDTO match, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        return ResponseEntity.ok(new MatchDTO(matchService.createMatch(MatchMapper.map(match))));
    }

    @GetMapping("functions")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<MatchFunction>> getAllMatchFunctions() {
        return ResponseEntity.ok(matchService.getAllMatchFunctions());
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity modifyMatch(@Valid @RequestBody MatchDTO match, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        matchService.editMatch(MatchMapper.map(match));
        return ResponseEntity.ok().build();
    }

    @PostMapping("replace/{matchId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity initReplacement(@PathVariable Long matchId) {
        matchService.initReplacement(matchId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("arrivalTime")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity registerArrivalTime(@RequestBody MatchArrivalTimeDTO replaceInformations) {
        matchService.registerArrivalTime(ReplacementCandidate.builder()
                .replaceInformations(ReplaceInformations.builder().id(replaceInformations.getId()).build())
                .arrivalTime(replaceInformations.getArrivalTime())
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("arrivalTime/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReplaceInformationsDTO> getReplaceInformations(@PathVariable Long id) {
        return ResponseEntity.ok(new ReplaceInformationsDTO(matchService.getReplaceInformations(id)));
    }

    @GetMapping("replacesInformations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReplaceInformationsDTO>> getAllReplaceInformations() {
        return ResponseEntity.ok(matchService.getAllReplaceInformations().stream()
                .map(ReplaceInformationsDTO::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("resign")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity replacementResign(@RequestBody Long replaceId) {
        this.matchService.replacementResign(replaceId);
        return ResponseEntity.ok().build();
    }

}
