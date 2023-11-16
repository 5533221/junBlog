package com.jun.Aspect;

import com.alibaba.fastjson.JSON;
import com.jun.annoation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/12 13:21
 */
@Component
@Aspect  //切面类
@Slf4j //控制台打印日志
public class LogAspect {


    //确定哪个切点，以后哪个类想成为切点，
    // 就在哪个类添加上面那行的注解。例如下面这个pt()就是切点
    @Pointcut("@annotation(com.jun.annoation.SystemLog)")
    public void pt(){


    }

    //定义通知的方法(这里用的是环绕通知)，
    // 通知的方法也就是增强的具体代码。
    // @Around注解表示该通知的方法会用在哪个切点
    @Around("pt()")
    public Object plintLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //proceed方法表示调用目标方法，ret就是目标方法执行完之后的返回值

        Object ret;
        try {
            //调用下面写的'实现打印日志信息的格式信息'的方法
            handlerBefore(joinPoint);
            //这是目标方法执行完成，上一行是目标方法未执行，下一行是目标方法已经执行
            ret = joinPoint.proceed();
            //调用下面写的'实现打印日志信息的数据信息'的方法
            handlerAfter(ret);

        } finally {
            //下面的'实现打印日志信息的数据信息'的方法，
            // 不管有没有出异常都会被执行，确保下面的输出必然输出在控制台
            //System.lineSeparator系统的换行符
            log.info("=======================end=======================" + System.lineSeparator());

        }


        return ret;
    }


    private void handlerBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        //增强SystemLog的方法
        SystemLog systemLog =getSystemLog(joinPoint);

        log.info("======================Start======================");
// 打印请求 URL
        log.info("请求URL   : {}",request.getRequestURI());
// 打印描述信息
        log.info("接口描述   : {}", systemLog.businessName());
// 打印 Http method
        log.info("请求方式   : {}",request.getMethod() );
// 打印调用 controller 的全路径以及执行方法                                               //方法名
        log.info("请求类名   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature)joinPoint.getSignature()).getName() );
// 打印请求的 IP
        log.info("访问IP    : {}",request.getRemoteHost());
// 打印请求入参
        log.info("传入参数   : {}",joinPoint.getArgs());


    }

    private void handlerAfter(Object ret) {

       log.info("响应   : {}",JSON.toJSONString(ret));

    }

    //增强SystemLog
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {

        //Signature是spring提供的接口，MethodSignature是Signature的子接口
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //下面那行就能获取被增强方法的注解对象，
        // 例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
        SystemLog systemLog = signature.getMethod().getAnnotation(SystemLog.class);


        return systemLog;
    }

}
