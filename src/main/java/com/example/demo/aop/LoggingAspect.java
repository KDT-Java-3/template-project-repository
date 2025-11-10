package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingAspect {

    @Before("com.example.demo.aop.pointcut.CommonPointcuts.controllerLayer()")
    public void logBeforeApiExecution() {
        log.info("[execution] Controller method execution detected");
    }

    @Before("com.example.demo.aop.pointcut.CommonPointcuts.serviceLayer()")
    public void logBeforeWithin() {
        log.info("[within] Service package method about to run");
    }

    @Before("com.example.demo.aop.pointcut.CommonPointcuts.loggableMethods()")
    public void logBeforeAnnotation() {
        log.info("[@annotation] @Loggable method execution detected");
    }

    @Before("com.example.demo.aop.pointcut.CommonPointcuts.serviceLayer()")
    public void logMethodDetails(JoinPoint joinPoint) {
        log.info("Method name: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            log.info("Arguments: {}", args);
        } else {
            log.info("Arguments: (none)");
        }
    }
}
