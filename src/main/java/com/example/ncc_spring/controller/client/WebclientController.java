package com.example.ncc_spring.controller.client;

import com.example.ncc_spring.helper.SuccessResponseHelper;
import com.example.ncc_spring.service.client.WebclientExample;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/webclient")
public class WebclientController {
    private final WebclientExample webclientExample;

    @GetMapping("/combine-res")
    public ResponseEntity<?> getAllResponse() {
        return SuccessResponseHelper.createSuccessResponse(webclientExample.callAll());
    }
}
