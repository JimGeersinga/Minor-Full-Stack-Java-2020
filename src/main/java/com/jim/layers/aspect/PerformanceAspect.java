package com.jim.layers.aspect;

import com.jim.layers.helper.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("execution(* com.jim.layers.controller.*.*(..))")
    private void controllerMethods() {}

    @Pointcut("execution(* com.jim.layers.repository.*.*(..))")
    private void repositoryMethods() {}

    @Around("controllerMethods()")
    public Object logControllerMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logMethodDuration(proceedingJoinPoint);
    }

    @Around("repositoryMethods()")
    public Object logRepositoryMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final String header = RequestHelper.getCurrentRequest().getHeader("X-Trace-Database-Time");
        if (header == null) {
            return  proceedingJoinPoint.proceed();
        }
        return logMethodDuration(proceedingJoinPoint);
    }

    private Object logMethodDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final Object result = proceedingJoinPoint.proceed();

        stopWatch.stop();

        final String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.println("Execution of "+ methodName +" took " + (stopWatch.getTotalTimeMillis()) + "ms" );

        return result;
    }
}
