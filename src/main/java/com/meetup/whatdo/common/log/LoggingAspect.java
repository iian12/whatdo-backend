package com.meetup.whatdo.common.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * TODO: Pointcut 패키지 재설정 필요
 * 로깅 클래스 정의
 * @version 1.0
 * @author Minchan Park
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.meetup.whatdo..controller..*(..)) || execution(* com.meetup.whatdo..service..*(..))")
    public void applicationPackagePointcut() {
        // Pointcut for application packages
    }

    @Around("applicationPackagePointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 클래스명 + 메서드명
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String params = Arrays.toString(joinPoint.getArgs());

        log.info("➡️ [START] {}.{}() 호출 / args = {}", className, methodName, params);

        try {
            Object result = joinPoint.proceed(); // 실제 메서드 실행
            long end = System.currentTimeMillis();

            log.info("✅ [END] {}.{}() 완료 ({}ms)", className, methodName, (end - start));
            return result;

        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            log.error("❌ [ERROR] {}.{}() 예외 발생 ({}ms) => {}", className, methodName, (end - start), e.getMessage());
            throw e;
        }
    }
}
