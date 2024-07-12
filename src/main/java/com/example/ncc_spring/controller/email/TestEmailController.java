package com.example.ncc_spring.controller.email;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.model.dto.mail.EmailSendRequestDto;
import com.example.ncc_spring.model.dto.mail.MailDataDto;
import com.example.ncc_spring.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class TestEmailController {
    private final EmailService emailService;
    @PostMapping("")
    public ResponseEntity<?> sendHtmlMail(@RequestBody EmailSendRequestDto emailSendRequestDto) {
        MailDataDto mailDataDto = new MailDataDto();
        Map<String,Object> map = new HashMap<>();
        map.put("name", emailSendRequestDto.getName());
        map.put("content", emailSendRequestDto.getContent());

        mailDataDto.setTo(emailSendRequestDto.getTo());
        mailDataDto.setSubject(emailSendRequestDto.getSubject());
        mailDataDto.setLocale(emailSendRequestDto.getLocale());
        mailDataDto.setProps(map);
        emailService.sendHtmlMail(mailDataDto, "email-template.html");

        return SuccessResponseHelper.createSuccessResponse("Send mail success");
    }
}
