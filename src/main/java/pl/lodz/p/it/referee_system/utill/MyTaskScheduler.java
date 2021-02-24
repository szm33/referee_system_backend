package pl.lodz.p.it.referee_system.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.ReplacementInformation;
import pl.lodz.p.it.referee_system.service.MatchService;


@Service
public class MyTaskScheduler {

    @Autowired
    private MatchService matchService;

    @Scheduled(fixedDelayString = "${delay.between.replacement.check}")
    public void print() {
        for(ReplacementInformation replaceInformation: matchService.getAllReplaceInformationsForScheduler()
            ){
                //inicjalizacja zastapienia
                matchService.replaceReferee(replaceInformation);
        }
    }
}
