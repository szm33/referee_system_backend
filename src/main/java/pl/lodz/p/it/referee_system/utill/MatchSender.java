package pl.lodz.p.it.referee_system.utill;

import javax.mail.MessagingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchSender extends Thread{

    private String link;
    private List<String> recipients;

    public MatchSender(String link, List<String> recipients) {
        this.link = link;
        this.recipients = recipients;
    }

    public void run() {
        try {
            EmailUtills.sendMails(recipients, ContextUtills.getMessage("match.subject"),
                    "<a href=" + link + " a>" + ContextUtills.getMessage("match.text") + "</a>",
                    true);
        }
        catch (MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        }
    }
}
