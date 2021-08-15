package com.zwq.cloud.controller;


import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

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
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {

        String head = request.getHeader("Authorization");
        String token = head.substring(head.indexOf("bearer") + 7);
        System.out.println("获取token:" + token);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();


        //return authentication.getPrincipal();
    }


}
