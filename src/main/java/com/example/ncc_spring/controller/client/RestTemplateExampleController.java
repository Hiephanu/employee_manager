package com.example.ncc_spring.controller.client;

import com.example.ncc_spring.model.dto.client.TodoDto;
import com.example.ncc_spring.service.client.RestTemplateExample;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/client")
public class RestTemplateExampleController {
    private final RestTemplateExample restTemplateExample;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodo() {
        System.out.println("HI");
        return ResponseEntity.ok(restTemplateExample.getAllTodos());
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto) {
        restTemplateExample.saveTodoTest(todoDto);
        return  ResponseEntity.ok("Success");
    }

}
