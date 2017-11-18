/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.component;

import net.tea.mail.component.model.EmailInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;

public class SendEmailComponent {

    private static Logger logger = LogManager.getLogger(SendEmailComponent.class);

    /**
     *
     * @param emailInfo
     * @param emailServerType use for creating email session
     * @throws MessagingException
     */
    public static void send(EmailInfo emailInfo, EmailServerType emailServerType) throws MessagingException {
        SessionAuthenticator authenticator = new SessionAuthenticator(emailInfo.getUserName(), emailInfo.getPassword());
        Session session = EmailSessionFactory.create(emailServerType, authenticator);
        session.setDebug(true);
        //create mail
        MimeMessage message = new MimeMessage(session);

        message.addHeader("charset", "UTF-8");

        String renderName;
        try {
            renderName = MimeUtility.encodeText(emailInfo.getSenderName(), MimeUtility.mimeCharset("UTF-8"), null);
            message.setFrom(new InternetAddress(emailInfo.getFromAddress(), renderName, MimeUtility.mimeCharset("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            logger.warn("email render name encoding fails!!", e);
        }

        String[] toAddresses = emailInfo.getToAddresses();
        InternetAddress[] sendTo = new InternetAddress[toAddresses.length];
        for (int i = 0; i < toAddresses.length; i++) {
            sendTo[i] = new InternetAddress(toAddresses[i]);
        }
        message.setRecipients(Message.RecipientType.TO, sendTo);
//        // cc
//        InternetAddress[] sendCc = new InternetAddress[1];
//        sendCc[0] = new InternetAddress(emailInfo.);
//        message.setRecipients(Message.RecipientType.CC, );

        message.setSubject(emailInfo.getSubject());

//        message.setText(emailInfo.getText(),
//                new ContentType("text/" +
//                        emailInfo.getTextType().getSubTpte()
//                        + ";"
//                        + emailInfo.getTextType().getCharset())
//                        .toString());


        //could refactor, could separate all the following code
        MimeMultipart mp = new MimeMultipart();
        BodyPart bp = new MimeBodyPart();
        bp.setContent(emailInfo.getText(),
                new ContentType("text/" +
                        emailInfo.getTextType().getSubTpte()
                        + "; charset="
                        + emailInfo.getTextType().getCharset())
                        .toString());

        mp.addBodyPart(bp);
        // set attachment
        String[] fileList = emailInfo.getAttachFileNames();
        if (fileList != null && fileList.length > 0) {
            for (int i = 0; i < fileList.length; i++) {
                bp = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(fileList[i]);
                bp.setDataHandler(new DataHandler(fds));
                try {
                    bp.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
                } catch (UnsupportedEncodingException e) {
                    logger.warn("email attached filename encoding fail!!", e);
                }
                mp.addBodyPart(bp);
            }
        }
        message.setContent(mp);



        //发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        Transport.send(message, message.getAllRecipients());
    }

}
