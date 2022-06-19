package cjj.demo.tmpl.auth.log;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一日志处理切面
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {

//    /**
//     * 配置切入点（配置为自定义注解）
//     */
//    @Pointcut("@annotation(com.shiro.aop.annotation.MyLog)")
//    public void logPointCut(){};

    //定义切点表达式,指定通知功能被应用的范围
    @Pointcut("execution(public * cjj.demo.aop.log.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    /**
     * value切入点位置
     * returning 自定义的变量，标识目标方法的返回值，自定义变量名必须和通知方法的形参一样
     * 特点：在目标方法之后执行,能够获取到目标方法的返回值，可以根据这个返回值做不同的处理
     */
    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        try{
            printLog(joinPoint, startTime, endTime, result);
        }catch (Exception e){
            log.error("record web log error: {}", e);
        }

        return result;
    }

    private void printLog(ProceedingJoinPoint joinPoint, long startTime, long endTime, Object result){
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String clazzName = joinPoint.getTarget().getClass().getName();// 获取控制器，包含报包名
        String methodName = methodSignature.getName();// 获取方法名

        //记录请求信息
        Object params =getParameter(method, joinPoint.getArgs());
        String ip = getRemoteHost(request);
        log.info("ip:{}, {}请求:{}.{}, 耗时:{}ms, 入参为:{}, 返回结果:{}",
                ip, request.getMethod(), clazzName, methodName, endTime-startTime, JSONUtil.toJsonStr(params), JSONUtil.toJsonStr(result));

    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (StrUtil.isNotBlank(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (CollUtil.isEmpty(argList)) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}
