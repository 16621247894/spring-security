package com.zwq.cloud.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuqing.zhu
 * @date 2021/08/13 15:38
 */
@RestController()
@RequestMapping("/user")
public class UserController {


    /**
     * 返回当前登录的用户类
     *
     * @param authentication
     * @return
     */
    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }


}
