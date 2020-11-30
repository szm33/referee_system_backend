package pl.lodz.p.it.referee_system.service;

import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.referee_system.entity.License;

import java.util.List;

public interface LicenseService {

    @PreAuthorize("permitAll()")
    List<License> getAllLicense();
}
