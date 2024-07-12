package com.example.ncc_spring.service.client;

import com.example.ncc_spring.exception.ExceptionEntity.InternalServerErrorException;
import com.example.ncc_spring.model.dto.client.TodoDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WebclientExample {
    private WebClient webClient;

    public Mono<List<TodoDto>> getAllTodo1() {
        return  webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TodoDto>>() {
                })
                .onErrorResume(e -> {
                    throw  new InternalServerErrorException("Fail to call third party");
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<List<TodoDto>> getAllTodo2() {
        return  webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TodoDto>>() {
                })
                .onErrorResume(e -> {
                    throw  new InternalServerErrorException("Fail to call third party");
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<TodoDto> createTodoDto(TodoDto todoDto) {
        return webClient.post()
                .uri("")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + "hihi")
                .body(Mono.just(todoDto), TodoDto.class)
                .retrieve()
                .bodyToMono(TodoDto.class);
    }
    public Mono<?> callAll() {
        Mono<List<TodoDto>> listMono1 = getAllTodo1();
        Mono<List<TodoDto>> listMono2 = getAllTodo2();

        listMono1.subscribe(result -> {
            System.out.println(Thread.currentThread().getName());
            for(TodoDto todoDto : result) {
                System.out.println("id: " + todoDto.getId() + " task: " + todoDto.getTask());
            }
        });

        listMono2.subscribe(result -> {
            System.out.println(  Thread.currentThread().getName());
            for(TodoDto todoDto : result) {
                System.out.println("id: " + todoDto.getId() + " task: " + todoDto.getTask());
            }
        });

        return null;
    }
}
