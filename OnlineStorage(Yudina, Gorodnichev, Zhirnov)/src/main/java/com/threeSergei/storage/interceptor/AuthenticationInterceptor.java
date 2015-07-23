package com.threeSergei.storage.interceptor;

import com.threeSergei.storage.model.UserEntity;
import com.threeSergei.storage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sergej on 22.07.15.
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserEntity user = userService.getCurrent();
        if(user == null) {
            response.sendRedirect("/auth");
            return false;
        }
        return true;
    }
}
