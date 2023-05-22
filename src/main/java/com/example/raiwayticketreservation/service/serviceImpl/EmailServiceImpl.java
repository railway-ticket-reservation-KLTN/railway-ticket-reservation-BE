package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.service.EmailService;
import com.example.raiwayticketreservation.service.ThymeleafService;
import com.example.raiwayticketreservation.constants.MailSenderConstants;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    JavaMailSender emailSender;
    @Autowired
    private ThymeleafService thymeleafService;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    @Override
    public void guiMaXacThucMessage(String to, String subject, Map<String, Object> variables) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(MailSenderConstants.FROM_EMAIL);
            helper.setText(thymeleafService.createContent("ma-xac-thuc-mail-sender.html", variables), true);
            helper.setTo(to);
            helper.setSubject(subject);
            this.emailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void guiVeTauMessage(String to, String subject, Map<String, Object> variables) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(MailSenderConstants.FROM_EMAIL);
            helper.setText(thymeleafService.createContent("thanh-toan-success-mail-sender.html", variables), true);
            helper.setTo(to);
            helper.setSubject(subject);
            this.emailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void thanhToanVeKhongThanhCongMessage(String to, String subject, Map<String, Object> variables) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(MailSenderConstants.FROM_EMAIL);
            helper.setText(thymeleafService.createContent("thanh-toan-fail-mail-sender.html", variables), true);
            helper.setTo(to);
            helper.setSubject(subject);
            this.emailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
