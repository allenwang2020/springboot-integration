package com.esb.aop;



import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
public class RepoLogAspect {


    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.esb.user..UserMapper.*(..))")
    public void repoLog(){

    }

    @Before("repoLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        //接受到請求，記錄請求內容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) attributes.getRequest();

        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", pointcut = "repoLog()")
    public void doAfterReturning(Object ret) throws Throwable{
    	log.info("RESPONSE : " + ret);
    	log.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    @Around("repoLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
    	log.info(System.currentTimeMillis());
        Object object = proceedingJoinPoint.proceed();
        System.out.println(object);
        log.info(System.currentTimeMillis());
        return object;
    }

}
