package com.example.aop.controller;

import com.example.aop.aspect.LogArguments;
import com.example.aop.aspect.LogExecutionTime;
import com.example.aop.dto.ResponseDto;
import com.example.aop.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppController {

    // addUser method => Post
    @PostMapping("/users")
    // Controller 의 코드를 크게 바꾸지 않으면서 부수적인 기능을 추가
    @LogArguments // 로그인을 체크하는 인터페이스 애노테이션
    @LogExecutionTime
    public ResponseDto addUser(@RequestBody UserDto user) {
        log.info(user.toString());
        ResponseDto response = new ResponseDto();
        response.setMessage("addUser");
        return response;
    }

    // getUsers method => Get
    @GetMapping("/users")
    @LogArguments // 로그인을 체크하는 인터페이스 애노테이션
    @LogExecutionTime
    public ResponseDto getUsers() {
        Long start = System.currentTimeMillis();
        try {
            ResponseDto response = new ResponseDto();
            response.setMessage("addUser");
            return response;
        } finally {
            log.info("elapsed: {}", System.currentTimeMillis() - start);
        }

    }
}
