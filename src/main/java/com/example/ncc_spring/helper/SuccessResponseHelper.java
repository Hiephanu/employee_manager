package com.example.ncc_spring.helper;

import com.example.ncc_spring.model.dto.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponseHelper {
    public static ResponseEntity<SuccessResponse> createSuccessResponse(Object data) {
        SuccessResponse successResponse = new SuccessResponse("Success", HttpStatus.OK.value(), data);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
