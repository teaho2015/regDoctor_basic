/**
 * @author teaho2015@gmail.com
 * since 2017
 */
package net.tea.mail.component.model;

import net.tea.mail.Builder;
import net.tea.mail.component.EmailTextType;

public class EmailInfo {

//    private String mailServerHost = "";
//    private int mailServerPort = 25;
    //sender addr
    private String fromAddress = "";
    private String toAddress = "";
    // two fields for auth
    private String userName = "";
    private String password = "";
    //sender name
    private String senderName = "";
//    private boolean validate = false;
    private String subject = "";
    private String text = "";
    private EmailTextType textType;
    private String[] attachFileNames = new String[1];


    public static class EmailInfoBuilder implements Builder<EmailInfo> {
        private EmailInfo emailInfo = new EmailInfo();

        public EmailInfoBuilder fromAddress(String str) {
            emailInfo.fromAddress = str;
            return this;
        }

        public EmailInfoBuilder toAddress(String str) {
            emailInfo.toAddress = str;
            return this;
        }

        public EmailInfoBuilder userName(String str) {
            emailInfo.userName = str;
            return this;
        }

        public EmailInfoBuilder password(String str) {
            emailInfo.password = str;
            return this;
        }

        public EmailInfoBuilder senderName(String str) {
            emailInfo.senderName = str;
            return this;
        }

        public EmailInfoBuilder subject(String str) {
            emailInfo.subject = str;
            return this;
        }

        public EmailInfoBuilder text(String str) {
            emailInfo.text = str;
            return this;
        }

        public EmailInfoBuilder textType(EmailTextType emailTextType) {
            emailInfo.textType = emailTextType;
            return this;
        }

        public EmailInfoBuilder attachFileNames(String... str) {
            emailInfo.attachFileNames = str;
            return this;
        }

        @Override
        public EmailInfo build() {
            return emailInfo;
        }
    }

    public static EmailInfoBuilder newBuilder() {
        return new EmailInfoBuilder();
    }


    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public EmailTextType getTextType() {
        return textType;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }
}
