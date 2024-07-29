package com.example.ncc_spring.service.email;

import com.example.ncc_spring.model.dto.mail.MailDataDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class EmailService {
        @Autowired
        private final JavaMailSender javaMailSender;
        @Autowired
        private ApplicationContext context;
        @Autowired
        private final TemplateEngine templateEngine;

        private final ExecutorService executorService = Executors.newFixedThreadPool(10);

        public void sendSimpleTextMail(String to, String subject, String body) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);

            javaMailSender.send(simpleMailMessage);
        }

        @Async(value = "threadPoolTaskExecutor")
        public void sendHtmlMail(MailDataDto mailDataDto, String template) {
            try {
                MimeMessage message =  javaMailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(message,true);

                Locale locale = new Locale.Builder()
                        .setLanguage(mailDataDto.getLocale())
                        .setRegion(mailDataDto.getCountry())
                        .build();

                LocaleContextHolder.setLocale(locale);
                Context context = new Context(locale);
                context.setVariables(mailDataDto.getProps());
                String html = templateEngine.process(template, context);

                helper.setTo(mailDataDto.getTo());
                helper.setSubject(mailDataDto.getSubject());
                helper.setText(html, true);
                System.out.println(Thread.currentThread().getName());
                javaMailSender.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

    public void sendMultipleHtmlEmails(List<MailDataDto> mailDataDtoList, String template) {
//        for (MailDataDto mailDataDto : mailDataDtoList) {
//            executorService.submit(() -> sendHtmlMail(mailDataDto, template));
//        }
        EmailService test = context.getBean(EmailService.class);
        for(MailDataDto mailDataDto : mailDataDtoList) {
            test.sendHtmlMail(mailDataDto, template);
        }
    }
    public void sendingMineMessageWithAttachments(String name, String to) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            mimeMessageHelper.setSubject(name);
            mimeMessageHelper.setTo(to);

            FileSystemResource fileSystemResource = new FileSystemResource(new File("C:/images/hi.png"));

            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
