package com.jim.layers.aspect;

import com.jim.layers.helper.RequestHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class LoggerAspect {

    @Pointcut("execution(public * *Controller(..))")
    public void controllerMethods() {}

    @Pointcut("execution(public * *Repository(..))")
    public void repositoryMethods() {}

    @Around("controllerMethods()")
    public Object logControllerMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logMethodDuration(proceedingJoinPoint);
    }

    @Around("repositoryMethods()")
    public Object logRepositoryMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var header = RequestHelper.getCurrentRequest().getHeader("X-Trace-Database-Time");
        if (header == null) {
            return  proceedingJoinPoint.proceed();
        }

        return logMethodDuration(proceedingJoinPoint);
    }

    private Object logMethodDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        var stopWatch = new StopWatch();
        stopWatch.start();

        final var result = proceedingJoinPoint.proceed();

        stopWatch.stop();

        var methodName = proceedingJoinPoint.getSignature().getName();
        System.out.println("ELAPSED ("+ methodName +"): " + stopWatch.getTotalTimeNanos());

        return result;
    }
}
