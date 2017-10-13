/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.component;

import net.tea.mail.component.model.EmailInfo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestSendEmailComponent {

    private Path configFilePath = Paths.get(System.getProperty("user.dir"), "regDoctor-mail/test.properties");

    private String fromAddr;

    private String toAddr;

    private String pwd;

    private Properties prop = new Properties();

    @Before
    public void beforeClass() {
        try {
            prop.load(Files.newInputStream(configFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSend_sucessful() throws MessagingException {

        String[] attachFilePaths = prop.getProperty("attachFilePath").split(",");


        List<String> list = new ArrayList<>();
        for (String attachFilePath : attachFilePaths) {
            list.add(Paths.get(System.getProperty("user.dir"), "regDoctor-mail/" + attachFilePath).toAbsolutePath().toString());
        }

        EmailInfo emailInfo = EmailInfo.newBuilder()
                .fromAddress(prop.getProperty("username"))
                .userName(prop.getProperty("username"))
                .password(prop.getProperty("password"))
                .senderName("tea haha 邮件测试")
                .subject("test")
                .text("<p style=\"color:green;\">This is a test mail!</p>")
                .textType(EmailTextType.HTML_UTF_8)
                .toAddress(prop.getProperty("toAddr"))
                .attachFileNames(list.toArray(new String[]{}))
                .build();

        SendEmailComponent.send(emailInfo, EmailServerType.HOTMAIL);

    }

    @Test
    public void testTextSend_sucessful() throws MessagingException {

        EmailInfo emailInfo = EmailInfo.newBuilder()
                .fromAddress(prop.getProperty("username"))
                .userName(prop.getProperty("username"))
                .password(prop.getProperty("password"))
                .senderName("tea haha 邮件测试")
                .subject("test")
                .text("This is a test mail!")
                .textType(EmailTextType.PLAIN_UTF_8)
                .toAddress(prop.getProperty("toAddr"))
                .build();

        SendEmailComponent.send(emailInfo, EmailServerType.HOTMAIL);

    }

}
