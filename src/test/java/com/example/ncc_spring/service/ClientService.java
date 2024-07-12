package com.example.ncc_spring.service;

import com.example.ncc_spring.model.dto.client.TodoDto;
import com.example.ncc_spring.service.client.RestTemplateExample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientService {
    @Mock
    private RestTemplateExample restTemplateExample;

    @Test
    public void  TestRestTemplate() {
        // Mock data
        TodoDto[] todoDtos = new TodoDto[5];
        // Define behavior for mock repository
        when(restTemplateExample.getAllTodos()).thenReturn(todoDtos);
        //call method
        TodoDto[] todos = restTemplateExample.getAllTodos();
        // Assert the size of returned list
        assertThat(todos.length == 4);
    }
}
