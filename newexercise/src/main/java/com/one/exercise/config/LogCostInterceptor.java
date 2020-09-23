package com.one.exercise.config;

import com.alibaba.fastjson.JSONObject;
import com.one.exercise.pojo.Identity;
import com.one.exercise.service.TeacherService;
import com.one.exercise.service.impl.TeacherServiceImpl;
import com.one.exercise.utils.IdentityUtils;
import com.one.exercise.utils.SpringContextUtils;
import com.one.exercise.utils.Tools;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这是一个登录的拦截器
 */
public class LogCostInterceptor implements HandlerInterceptor {

    private TeacherService teacherService = (TeacherService) SpringContextUtils.getBeanByClass(TeacherServiceImpl.class);

    /**
     * 请求执行前
     * public boolean loginVerify
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取身份码
        String verifyCode = request.getHeader("verifyCode");
        if (Tools.isNullStr(verifyCode)){
            response.setContentType("text/html;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 401);
            jsonObject.put("msg", "无身份码，拒绝访问");

            response.getWriter().write(jsonObject.toString());
            return false;
        }

        // 1403344689@qq.com
        if (verifyCode.equalsIgnoreCase("test-teacher20")){
            request.setAttribute("identity", new Identity((long) 20, 1));
            return true;
        }
        // id 18 学生
        if (verifyCode.equalsIgnoreCase("test-student18")){
            request.setAttribute("identity", new Identity((long) 18, 2));
            return true;
        }
        // id 22 admin
        if (verifyCode.equalsIgnoreCase("test-admin22")){
            request.setAttribute("identity", new Identity((long) 22, 3));
            return true;
        }


        // 通过verifyCode获取用户信息
        try {
            Identity check = IdentityUtils.check(verifyCode);

            if (check == null){
                response.setContentType("text/html;charset=UTF-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("state", 401);
                jsonObject.put("msg", "verifyCode错误或无效");
                response.setStatus(401);
                response.getWriter().write(jsonObject.toString());
                return false;
            }

            request.setAttribute("identity", check);
            return true;
        }catch (Exception e){
            response.setContentType("text/html;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", 500);
            jsonObject.put("msg", e.getMessage());
            response.setStatus(500);
            response.getWriter().write(jsonObject.toString());
            return false;
        }

    }

    /**
     * 请求执行后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * afterCompletion是视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
