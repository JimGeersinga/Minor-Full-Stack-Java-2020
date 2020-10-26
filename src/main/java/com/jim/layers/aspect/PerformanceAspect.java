package com.jim.layers.aspect;

import com.jim.layers.helper.RequestHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

import java.util.logging.Logger;

@Aspect
public class PerformanceAspect {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(public * *Controller(..))")
    private void controllerMethods() {}

    @Pointcut("execution(public * *Repository(..))")
    private void repositoryMethods() {}

    @Around("controllerMethods()")
    public Object logControllerMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return logMethodDuration(proceedingJoinPoint);
    }

    @Around("repositoryMethods()")
    public Object logRepositoryMethodDurations(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final var header = RequestHelper.getCurrentRequest().getHeader("X-Trace-Database-Time");
        if (header == null) {
            return  proceedingJoinPoint.proceed();
        }

        return logMethodDuration(proceedingJoinPoint);
    }

    private Object logMethodDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final var stopWatch = new StopWatch();
        stopWatch.start();

        final var result = proceedingJoinPoint.proceed();

        stopWatch.stop();

        final var methodName = proceedingJoinPoint.getSignature().getName();
        logger.info("Execution of "+ methodName +" took " + (stopWatch.getTotalTimeMillis()) + "ms" );

        return result;
    }
}
