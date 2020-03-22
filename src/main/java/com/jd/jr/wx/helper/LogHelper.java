package com.jd.jr.wx.helper;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志帮助类
 *
 * @Author dongzhihua
 * @Date 2020-02-10 22:21
 */
@Aspect
@Component
public class LogHelper {

    @Pointcut("@annotation(com.jd.jr.wx.helper.Log)")
    public void log() {
        System.out.println("..................");
    }

    @Before("log()")
    public void logReq(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logReqArgs(logger, getMethodName(joinPoint), args);
    }

    @AfterReturning(
            value = "log()",
            returning = "resp"
    )
    public void logResp(JoinPoint joinPoint, Object resp) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("LogHelper {} -> 返回值: {}", this.getMethodName(joinPoint), resp);
    }

    String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[] annotations = method.getAnnotations();
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        Log annotation = method.getDeclaredAnnotation(Log.class);
        if (annotation!=null && StringUtils.hasText(annotation.m())) {
            return annotation.m();
        }
        return method.getName();
    }

    void logReqArgs(Logger logger, String methodName, Object[] args) {

        if(args == null || args.length == 0) {
            logger.info("LogHelper {}", methodName);
        } else if (args.length == 1) {
            logger.info("LogHelper {} -> 参数: {}", methodName, args[0]);
        } else {
            logger.info("LogHelper {} -> 参数: {}", methodName, Arrays.toString(args));
        }
    }
}
