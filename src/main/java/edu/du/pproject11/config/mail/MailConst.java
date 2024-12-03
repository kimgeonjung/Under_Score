package edu.du.pproject11.config.mail;

import edu.du.pproject11.config.EnvConfig;

public class MailConst {
    public static final String mailSenderName = EnvConfig.get("mail_username");
    public static final String mailSenderPassword = EnvConfig.get("mail_password");
}
