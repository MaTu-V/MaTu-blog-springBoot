package com.mt.blog.interceptor;

import com.mt.blog.service.TokenService;
import com.mt.blog.utils.ServiceResult;
import com.mt.blog.utils.ServiceResultHelper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    /**
     * 验证用户身份
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8"); //编码设置
        String token = request.getHeader("public-token"); // 获取客户端请求头中携带的token
        if (StringUtils.isEmpty(token)) { // 判断token是否存在在响应头中
            returnJson(response,ServiceResultHelper.genResultWithTokenFailed());
            return false;
        }
        ServiceResult<Boolean> serviceResult = tokenService.checkToken(token);   // 检测token正确性
        if(!serviceResult.isSuccess()){ // 返回false 则说明出现异常 返回异常信息
            returnJson(response,serviceResult);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 自定义返回数据信息
     * @param response
     * @param result
     * @throws IOException
     * @throws JSONException
     */
    public void returnJson(HttpServletResponse response, ServiceResult<Boolean> result) throws IOException, JSONException {
        OutputStream out = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            out = response.getOutputStream();
            JSONObject res = new JSONObject();
            // TODO: 待优化
            res.put("code",result.getCode());
            res.put("msg",result.getMsg());
            out.write(res.toString().getBytes("utf-8"));
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
