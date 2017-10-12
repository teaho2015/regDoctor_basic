package net.tea.mail.component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class SessionAuthenticator extends Authenticator {
    String userName=null;
    String password=null;

    public SessionAuthenticator(){
    }
    public SessionAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
