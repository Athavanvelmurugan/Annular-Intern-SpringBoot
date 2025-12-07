package com.example.MailSenderDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MailSenderDemoApplication {

    @Autowired
    private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(MailSenderDemoApplication.class, args);
	}

    @EventListener(ApplicationReadyEvent.class)

    public void sendMail() {
        emailService.sendEmail("athavanvelmurugan@gmail.com",
                "this is subject email",
                "this is body email");
    }
}
