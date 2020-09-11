package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.RefereeCreateDTO;
import pl.lodz.p.it.referee_system.dto.RefereeDTO;
import pl.lodz.p.it.referee_system.mapper.RefereeMapper;
import pl.lodz.p.it.referee_system.service.RefereeService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("referee")
@Transactional(propagation = Propagation.NEVER)
public class RefereeController {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private RefereeService refereeService;

    @GetMapping
    public ResponseEntity<List<RefereeDTO>> getAllReferee() {
            return ResponseEntity.ok((refereeService.getAllReferees().stream()
                    .map(RefereeDTO::new)
                    .collect(Collectors.toList())));
    }

    @GetMapping("{id}")
    public ResponseEntity<RefereeDTO> getReferee(@PathVariable("id") Long id) {
            return ResponseEntity.ok(new RefereeDTO(refereeService.getReferee(id)));
    }

    //dostepna jednie dla admina
    @PutMapping
    public ResponseEntity<String> editReferee(@Valid @RequestBody RefereeDTO referee) {
        refereeService.editReferee(RefereeMapper.map(referee));
        return ResponseEntity.ok("Successfully edited referee");
    }

    @PostMapping
    public ResponseEntity<String> addReferee(@Valid @RequestBody RefereeCreateDTO referee) {
        refereeService.addReferee(RefereeMapper.map(referee));
        return ResponseEntity.ok("Successfully added " + referee.getName() + "" + referee.getSurname());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String noRefereeFound() {
        return "Referee not found";
    }
}
