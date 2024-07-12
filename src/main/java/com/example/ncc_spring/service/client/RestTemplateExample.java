package com.example.ncc_spring.service.client;

import com.example.ncc_spring.model.dto.client.TodoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;


@AllArgsConstructor
@Service
public class RestTemplateExample {
    private final RestTemplate restTemplate;
    private final String BASE_URL= "https://6501c2ac736d26322f5c3d68.mockapi.io/todo";

    public TodoDto[] getAllTodos() {
        return restTemplate.getForObject(BASE_URL, TodoDto[].class);
    }

    public TodoDto getTodoById(long id) {
        return  restTemplate.getForObject(BASE_URL+"/" + id, TodoDto.class);
    }
    public void saveTodoTest(TodoDto todoDto) {
        restTemplate.postForEntity(BASE_URL, todoDto, TodoDto.class );
    }
    public void saveTodo(TodoDto todoDto, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + token);

        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.set("todo", todoDto);

        HttpEntity<MultiValueMap<String, Object>> req= new HttpEntity<>(body, httpHeaders);

        restTemplate.exchange(BASE_URL, HttpMethod.POST, req, TodoDto.class);
    }

    public void deleteTodo(long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
