package com.company.project.EndersOverFlow.gmail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator{
    PasswordAuthentication pa;
    public MailAuth() {
        String mail_id = "wjdehd7991";
        String mail_pw = "!dpswpf1004";
        pa = new PasswordAuthentication(mail_id, mail_pw);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}

