package com.TaskManagement.TaskManagement3.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {
    @Autowired
    private JavaMailSender JavaMailSender;
    public void sendResetPasswordEmail(String to,String token){
        String resetPasswordLink="http://localhost:8080/reset-password?token="+token;
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("click the link to reset your password :"+resetPasswordLink);
        JavaMailSender.send(message);
    }
}
