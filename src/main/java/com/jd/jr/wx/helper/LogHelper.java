package com.jd.jr.wx.helper;

import com.jd.jr.wx.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志帮助类
 *
 * @Author dongzhihua
 * @Date 2020-02-10 22:21
 */
@Slf4j
@Aspect
@Component
public class LogHelper {
    @Pointcut("@annotation(com.jd.jr.wx.helper.LogHelper.LogReqResp)")
    public void logReqResp() {
    }
    @Pointcut("@annotation(com.jd.jr.wx.helper.LogHelper.FacadeLogReqResp)")
    public void facadeLogReqResp() {
    }

    @Before("logReqResp()")
    public void logReq(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logReqArgs(logger, getMethodName(joinPoint), args);
    }

    @AfterReturning(
            value = "logReqResp()",
            returning = "resp"
    )
    public void logResp(JoinPoint joinPoint, Object resp) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("{} -> resp: {}", this.getMethodName(joinPoint), resp);
    }

    @Around("facadeLogReqResp()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        try {
            Object[] args = joinPoint.getArgs();
            logReqArgs(logger, getMethodName(joinPoint), args);
            Object resp = joinPoint.proceed();
            logger.info("{} -> resp: {}", this.getMethodName(joinPoint), resp);
            return resp;
        } catch (Throwable e) {
            logger.error("处理异常", e);
        }
        return null;
    }

    String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getName();
    }

    void logReqArgs(Logger logger, String methodName, Object[] args) {

        if(args == null || args.length == 0) {
            logger.info("{}", methodName);
        } else if (args.length == 1) {
            logger.info("{} -> req: {}", methodName, JsonUtils.toJson(args[0]));
        } else {
            logger.info("{} -> req: {}", methodName, JsonUtils.toJson(args));
        }
    }

    public @interface LogReqResp {
    }

    public @interface FacadeLogReqResp {
    }
}
