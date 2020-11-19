package pl.lodz.p.it.referee_system.utill;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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

    private MatchService matchService;

    @Scheduled(fixedRate = 180000)
    public void print() {
//        List<ReplaceInformations> replaceInformationsList = matchService.getAllReplaceInformations();
//        for(ReplaceInformations replaceInformation: matchService.getAllReplaceInformations()
//            ){
//            LocalDateTime now = LocalDateTime.now();
//            if (replaceInformation.getExecuteTime().isAfter(now)) {
//                //inicjalizacja zastapienia
////                matchService.replaceReferee(replaceInformation);
//                Logger.getGlobal().log(Level.SEVERE, replaceInformation.getExecuteTime().toString());
//            }
//            else {
//
//            }

//        }
    }
}
