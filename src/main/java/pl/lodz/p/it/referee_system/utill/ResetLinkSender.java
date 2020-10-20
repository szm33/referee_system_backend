package pl.lodz.p.it.referee_system.utill;

import javax.mail.MessagingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResetLinkSender extends Thread {

    private String link;
    private String to;

    public ResetLinkSender(String to, String link) {
        this.to = to;
        this.link = link;
    }

    public void run() {
        try {
            EmailUtills.sendMail(to, ContextUtills.getMessage("resetlink.subject"),
                    "<a href=" + link + " a>" + ContextUtills.getMessage("resetlink.text") + "</a>",
                    true);
        }
        catch (MessagingException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        }
    }
}
