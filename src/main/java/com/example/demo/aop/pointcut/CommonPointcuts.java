package com.example.demo.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Central registry for pointcut expressions so that multiple aspects can share the same targets.
 */
@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void controllerLayer() {
    }

    @Pointcut("execution(* com.example.demo.service..*(..))")
    public void serviceLayer() {
    }

    @Pointcut("@annotation(com.example.demo.common.annotation.Loggable)")
    public void loggableMethods() {
    }
}

