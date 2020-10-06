package pl.lodz.p.it.referee_system.utill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Account;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class ContextUtills {

    private static String resourceBundleName;
    @Value("${resource.bundle}")
    public void setNameStatic(String name){
        ContextUtills.resourceBundleName = name;
    }

    public static String getUsername() {
        return ((Account) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
    }

    public static String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage();
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    public static String getMessage(String key) {
        return ResourceBundle.getBundle(resourceBundleName, getLocale()).getString(key);
    }


}
