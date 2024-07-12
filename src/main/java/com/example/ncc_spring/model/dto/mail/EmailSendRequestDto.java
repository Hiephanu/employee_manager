package com.example.ncc_spring.model.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailSendRequestDto {
    private String to;
    private String subject;
    private String name;
    private String content;
    private String locale;
}
