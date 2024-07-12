package com.example.ncc_spring.model.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDataDto {
    private String to;
    private String subject;
    private String locale;
    private String country;
    private Map<String, Object> props;
}
