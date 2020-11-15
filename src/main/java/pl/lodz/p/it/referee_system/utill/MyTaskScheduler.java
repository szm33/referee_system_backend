package pl.lodz.p.it.referee_system.utill;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.repository.ReplaceInformationsRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MyTaskScheduler {

    private ReplaceInformationsRepository replaceInformationsRepository;

    @Scheduled(fixedRate = 5000)
    public void print() {
        for(ReplaceInformations replaceInformation: replaceInformationsRepository.findAll()
            ){
            LocalDateTime now = LocalDateTime.now();
            if (replaceInformation.getExecuteTime().isAfter(now)) {
                //inicjalizacja zastapienia
            }
            else {

            }

        }
    }
}
