package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.License;
import pl.lodz.p.it.referee_system.repository.LicenseRepository;
import pl.lodz.p.it.referee_system.service.LicenseService;

import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Override
    public List<License> getAllLicense() {
        return licenseRepository.findAll();
    }
}
