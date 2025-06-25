package com.pds.partidosapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



/**
 * Implementación del adaptador de correo electrónico utilizando JavaMail.
 * Proporciona funcionalidad para enviar correos electrónicos.
 */
@Service
public class AdapterJavaMail implements IAdapterEmailNotification {

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String replyToEmail;

    public AdapterJavaMail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendEmail(String toEmail, String subject, String content, boolean isHtmlContent) throws Exception {
        if (!StringUtils.hasText(toEmail)) {
            throw new IllegalArgumentException("El correo electrónico del destinatario no puede estar vacío");
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Configurar remitente y destinatario
            helper.setFrom(fromEmail);
            if (replyToEmail != null && !replyToEmail.isEmpty()) {
                helper.setReplyTo(replyToEmail);
            }
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // Configurar el contenido del mensaje
            helper.setText(content, isHtmlContent);

            // Enviar el correo
            mailSender.send(message);
            return true;
            
        } catch (MessagingException e) {
            throw new Exception("Error al enviar el correo electrónico: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Error inesperado al enviar el correo electrónico", e);
        }
    }
}
