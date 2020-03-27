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
import org.springframework.util.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
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

    @Pointcut("@annotation(com.jd.jr.wx.helper.LogHelper.LogReqResp)")
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
        logger.info("{} <- 返回值: {}", this.getMethodName(joinPoint), resp);
    }

    String getMethodName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        LogReqResp annotation = method.getDeclaredAnnotation(LogReqResp.class);
        if (annotation!=null && StringUtils.hasText(annotation.desc())) {
            return annotation.desc();
        }
        return method.getName();
    }

    void logReqArgs(Logger logger, String methodName, Object[] args) {
        if (args == null || args.length == 0) {
            logger.info("{} -> ", methodName);
            return;
        }
        Object logRes = JsonUtils.toJson(args);
        // 如果序列化出现问题
        if(logRes == null) {
            if ( args.length == 1) {
                logRes = args[0];
            } else {
                logRes = Arrays.toString(args);
            }
        }
        logger.info("{} -> 参数: {}", methodName, logRes);
    }

    /**
     * 打印入参和返回值
     * 注：不能用于jsf
     * dongzhihua
     * 2020/3/6 10:46
     **/
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface LogReqResp {
        /**
         * 方法描述
         */
        String desc() default "";

        /**
         * 日志级别
         */
        String level() default "INFO";
    }
}
