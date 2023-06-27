package com.example.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j // log 추가
@Aspect // 이 클래스가 관점임을 드러내는 애노테이션
@Component // Bean 객체로 등록
public class LoggingAspect {
    // Controller Class 의 FullName
    // @Before : Advice, 실제로 실행될 코드를 나타냄
    // @Before.value : Pointcut Designator, 어느 JoinPoint 에서 실행될 것인지
    // @Before("this(com.example.aop.controller.AppController)") // 횡단 관점(표현하고자 하는 주소)
    // 기본 => execution : Method 를 지정하는 용도
    // 기본 => within : 특정 클래스 또는 패키지 지정 / this : 클래스 instance 지정
    // 기본 => annotation : 어노테이션 지정
    // @Before("within(com.example.aop.controller.AppController)")
    // @Before("within(com.example.aop.controller..*)")
    // JoinPoint : 이 Advice 가 실행된 JoinPoint (addUser)
    // @Before("@annotation(com.example.aop.asspect.LogArguments)") // annotation 지정
    @After("@annotation(com.example.aop.aspect.LogArguments)")
    public void logParameters(JoinPoint joinPoint) {
//        log.info("hello aop!");
        // Signature : JoinPoint 의 정보를 담은 객체
        Signature signature = joinPoint.getSignature();
        // Method 이름 로그 확인
        log.info(signature.getName());
        // Method 인자들 확인
        Object[] arguments = joinPoint.getArgs();
        // 인자가 없을 때
        if (arguments.length == 0) log.info("No args");
        // Method 인자들 출력
        for (Object argument : arguments) {
            log.info("argument; [{}]", argument);
        }
    }

    // @LogExecutionTime 애노테이션이 붙은 메서드의 실행되는데 걸리는 시간을 기록하고 싶을 때
    @Around("@annotation(com.example.aop.aspect.LogExecutionTime)")
    public Object logExecutionTime(
            // Advice 내에서 대상 JoinPoint 가 실행되도록 요구할 수 있음
            ProceedingJoinPoint joinPoint
    ) throws Throwable { // 예외처리
        // 시작하는 시간
        long startTime = System.currentTimeMillis();
        // joinPoint.proceed() : JoinPoint 에 해당하는 메서드를 진행함
        Object proceed = joinPoint.proceed();

        // 끝나는 시간
        long endTime = System.currentTimeMillis();
        // 실제로 추가하고 싶은 기능
        log.info("{} executed in: {}ms", joinPoint.getSignature(), endTime - startTime);

        // proceed 결과값을 반환함
        return proceed;
    }
}
