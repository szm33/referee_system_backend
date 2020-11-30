package pl.lodz.p.it.referee_system.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.repository.ReplaceInformationsRepository;
import pl.lodz.p.it.referee_system.service.MatchService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MyTaskScheduler {

    @Autowired
    private MatchService matchService;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Scheduled(fixedRate = 10000)
    public void print() {
        for(ReplaceInformations replaceInformation: matchService.getAllReplaceInformations()
            ){
            LocalDateTime now = LocalDateTime.now();
            if (replaceInformation.getExecuteTime().isBefore(now)) {
                //inicjalizacja zastapienia
                matchService.replaceReferee(replaceInformation);
                Logger.getGlobal().log(Level.SEVERE, replaceInformation.getExecuteTime().toString());
            }
            else {

            }

        }
    }
}
