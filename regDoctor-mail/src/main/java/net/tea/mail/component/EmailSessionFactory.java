/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.component;

import com.sun.mail.util.MailSSLSocketFactory;
import net.tea.mail.component.model.EmailInfo;

import javax.mail.Session;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailSessionFactory {

    public static Session create(EmailServerType emailServerType, SessionAuthenticator sessionAuthenticator) {
        Session session = null;
        if (emailServerType.equals(EmailServerType.HOTMAIL)) {
            session = createHotmailSession(sessionAuthenticator);
        } else {
            throw new UnsupportedOperationException("unsupported email server type yet!!");
        }

        return session;
    }

    public static Session createHotmailSession(SessionAuthenticator sessionAuthenticator) {
        String host = "smtp-mail.outlook.com";//Ip address of your system    smtp-mail.outlook.com
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.ssl.enable","false");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session ses = Session.getInstance(props, sessionAuthenticator);
        return ses;
    }

}
