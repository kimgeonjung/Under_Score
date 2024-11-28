package edu.du.pproject11;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RequiredArgsConstructor
public class PProject11Application {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        System.setProperty("mail_username", dotenv.get("mail_username"));
        System.setProperty("mail_password", dotenv.get("mail_password"));

        SpringApplication.run(PProject11Application.class, args);
    }


}
