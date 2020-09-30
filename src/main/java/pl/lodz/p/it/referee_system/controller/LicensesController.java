package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.referee_system.entity.License;
import pl.lodz.p.it.referee_system.service.LicenseService;

import java.util.List;

@RestController
@RequestMapping("license")
public class LicensesController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping
    public ResponseEntity<List<License>> getLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicense());
    }
}
