package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.referee_system.entity.License;

public interface LicenseRepository extends JpaRepository<License, Long> {

    License findByType(String type);
}
