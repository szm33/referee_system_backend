package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.AccountDTO;
import pl.lodz.p.it.referee_system.dto.RefereeCreateDTO;
import pl.lodz.p.it.referee_system.dto.RefereeDTO;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.mapper.RefereeCreateMapper;
import pl.lodz.p.it.referee_system.service.RefereeService;
import pl.lodz.p.it.referee_system.service.implementation.AccountServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("referee")
@Transactional(propagation = Propagation.NEVER)
public class RefereeController {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private RefereeService refereeService;

    @GetMapping
    public List<Referee> getAllReferee() {
            return refereeService.getAllReferees();
    }

    @GetMapping("{id}")
    public RefereeDTO getReferee(@PathVariable("id") Long id) {
        return new RefereeDTO(refereeService.getReferee(id));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> addReferee(@Valid @RequestBody RefereeCreateDTO referee) {
        refereeService.addReferee(RefereeCreateMapper.map(referee));
        return ResponseEntity.ok(new AccountDTO());
    }
}
