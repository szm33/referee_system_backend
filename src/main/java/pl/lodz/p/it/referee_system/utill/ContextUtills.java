package pl.lodz.p.it.referee_system.utill;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Account;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ContextUtills {

    private static String resourceBundleName;
    @Value("${resource.bundle}")
    public void setResourceBundleName(String name){
        ContextUtills.resourceBundleName = name;
    }
    private static SecretKeySpec secretKey;
    @Value("${encryption.key}")
    public void setEncryptionKey(String key){
        setKey(key);
    }
    private static String resetLinkAddress;
    @Value("${frontend.address.reset.link}")
    public void setFrontendResetLinkAddress(String address){
        ContextUtills.resetLinkAddress = address;
    }
    private static String replaceInformationsLinkAddress;
    @Value("${frontend.address.replace.informations.link}")
    public void setFrontendReplaceInformationsAddress(String address){
        ContextUtills.replaceInformationsLinkAddress = address;
    }
    private static String matchLinkAddress;
    @Value("${frontend.address.match.link}")
    public void setFrontendMatchAddress(String address){
        ContextUtills.matchLinkAddress = address;
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
//        return messageSource.getMessage(key, null, getLocale());
    }

    public static void setKey(String encryptionKey)
    {
        MessageDigest sha;
        try {
            byte[] key = encryptionKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());

        }
    }

    public static String encrypt(Long strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(String.valueOf(strToEncrypt).getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        }
        return null;
    }

    public static Long decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return Long.valueOf(new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt))));
        }
        catch (Exception e)
        {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage() + " Klasa: " + e.getClass());
        }
        return null;
    }

    public static String createResetLink(String token) {
        return resetLinkAddress + token;
    }

    public static String createReplaceInformationsLink(Long id) {
        return replaceInformationsLinkAddress + id;
    }

    public static String createMatchLink(Long id) {
        return matchLinkAddress + id;
    }

}
