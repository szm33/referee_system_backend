package pl.lodz.p.it.referee_system.utill;

import javax.mail.MessagingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReplacementInformationSender extends Thread {

    private String link;
    private List<String> recipients;

    public ReplacementInformationSender(List<String> recipients, String link) {
        this.recipients = recipients;
        this.link = link;
    }

    public void run() {
        try {
            EmailUtills.sendMails(recipients, ContextUtills.getMessage("replace.informations.subject"),
                    "<a href=" + link + " a>" + ContextUtills.getMessage("replace.informations.text") + "</a>",
                    true);
        }
        catch (MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        }
    }
}
