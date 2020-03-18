package com.jd.jr.wx.helper;

import com.jd.jr.wx.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
@Aspect
@Component
public class LogHelper {

    @Pointcut("@annotation(com.jd.jr.wx.helper.LogHelper.LogReqResp)")
    public void logReqResp() {
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
        logger.info("LogHelper {} -> 返回值: {}", this.getMethodName(joinPoint), resp);
    }

    String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getName();
    }

    void logReqArgs(Logger logger, String methodName, Object[] args) {

        if(args == null || args.length == 0) {
            logger.info("LogHelper {}", methodName);
        } else if (args.length == 1) {
            logger.info("LogHelper {} -> 参数: {}", methodName, JsonUtils.toJson(args[0]));
        } else {
            logger.info("LogHelper {} -> 参数: {}", methodName, JsonUtils.toJson(args));
        }
    }

    /**
     * 打印入参和返回值
     * 注：不能用于jsf接口实现
     * dongzhihua
     * 2020/3/6 10:46
     **/
    public @interface LogReqResp {
    }
}
