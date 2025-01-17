package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.RefereeCreateDTO;
import pl.lodz.p.it.referee_system.dto.RefereeDTO;
import pl.lodz.p.it.referee_system.dto.RefereeListDTO;
import pl.lodz.p.it.referee_system.entity.License;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.mapper.RefereeMapper;
import pl.lodz.p.it.referee_system.service.LicenseService;
import pl.lodz.p.it.referee_system.service.RefereeService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("referee")
@Transactional(propagation = Propagation.NEVER)
public class RefereeController {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private LicenseService licenseService;
    @Autowired
    private RefereeService refereeService;


    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<RefereeListDTO>> getAllReferee() {
        return ResponseEntity.ok((refereeService.getAllReferees().stream()
                .map(RefereeListDTO::new)
                .collect(Collectors.toList())));
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<RefereeDTO> getReferee(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new RefereeDTO(refereeService.getReferee(id)));
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity editReferee(@Valid @RequestBody RefereeDTO referee, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        refereeService.editReferee(RefereeMapper.map(referee));
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity addReferee(@Valid @RequestBody RefereeCreateDTO referee, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        refereeService.addReferee(RefereeMapper.map(referee));
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/active")
    @Secured("ROLE_ADMIN")
    public ResponseEntity changeActiveStatus(@PathVariable Long id) {
        refereeService.changeActiveStatus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("license")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<License>> getLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicense());
    }
}
