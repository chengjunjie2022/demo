package cjj.demo.tmpl.auth.shiro;

import cjj.demo.tmpl.auth.exception.BizException;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import road.cjj.commons.entity.R;
import road.cjj.commons.entity.RC;
import road.cjj.commons.redis.consts.RedisConst;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 效验请求是否携带 token ,此时还未进入 controller 所以抛出的异常捕获不到，只能直接输出 json
 */
@Slf4j
public class AuthFilter extends AccessControlFilter {

    /**
     * 判断用户是否已经登录
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return  true 则进入过滤器链中的下一个过滤器
     *          false 则进入 onAccessDenied 方法，进行效验
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 效验是否携带 token ，此时还未进入 controller 所以抛出的异常捕获不到，只能直接输出 json
     * @param servletRequest
     * @param servletResponse
     * @return  true 表示需要继续处理
     *          false 表示该拦截器实例已经处理完成了，直接返回即可
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            //如果客户端没有携带token，拦下请求
            String accessToken = getRequestToken(request);
            if (StringUtils.isBlank(accessToken)) {
                throw new BizException(RC.ERR_TOKEN_NULL.getCode(), RC.ERR_TOKEN_NULL.getMsg());
            }

            // 把 accessToken 设置进 Shiro
            JwtToken jwtToken = new JwtToken(accessToken);
            // 执行认证
            SecurityUtils.getSubject().login(jwtToken);
        }catch (BizException e){
            responseJson(response,e.getRcode(),e.getRmsg());
            return false;
        }catch (AuthenticationException e){// 认证异常
            log.info("【打印 e.getCause 】，{}",e.getCause());
            // 判断该异常是我们手动抛出，还是 Shiro 抛出，为了区分提示信息
            if (e.getCause() instanceof BizException){ // 手动抛出
                BizException exception = (BizException) e.getCause();
                responseJson(response,exception.getRcode(),exception.getRmsg());
            }else if (e.getCause() instanceof ShiroException){ // Shiro 抛出
                // 用户认证异常（登陆）
                responseJson(response, RC.ERR_AUTHENTICATION.getCode(), RC.ERR_AUTHENTICATION.getMsg());
            }else { // 系统异常
                responseJson(response, RC.ERR_SYS.getCode(), RC.ERR_SYS.getMsg());
            }
            return false;
        }

        return true;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest request) {
        //从header中获取token
        String token = request.getHeader(RedisConst.ACCESS_TOKEN);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(RedisConst.ACCESS_TOKEN);
        }

        if (StringUtils.isEmpty(token)) {
            Cookie[] cks = request.getCookies();
            if (cks != null) {
                for (Cookie cookie : cks) {
                    if (cookie.getName().equals(RedisConst.ACCESS_TOKEN)) {
                        token = cookie.getValue();
                        return token;
                    }
                }
            }
        }

        return token;
    }

    /**
     * 返回 json
     * @param response
     * @param message
     */
    private void responseJson(HttpServletResponse response,int code,String message){
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(R.err(code,message)));
            writer.close();
            response.flushBuffer();
        } catch (IOException e) {
            log.error("【输出 JSON 异常】，{}",e);
        }
    }
}
