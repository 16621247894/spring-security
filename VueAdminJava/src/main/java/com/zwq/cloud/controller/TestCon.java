package com.zwq.cloud.controller;

import com.zwq.cloud.common.Response;
import com.zwq.cloud.service.SysUserService;
import com.zwq.cloud.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCon {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisCache cache;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public Response test() {
        // 测试全局异常
        //int a=3/0;
        // 测试redis连接获取
       /* cache.set("zwq","info");

        String v=cache.get("zwq");
        System.out.println("获取的v:"+v);

        return Response.success(sysUserService.list());*/

        String password=passwordEncoder.encode("111111");
        boolean ma=passwordEncoder.matches("111111",password);
        System.out.println("获取的结果:"+ma);

        return Response.success(password);
    }
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("pass")
    public Response pass(){
        System.out.println("huoq li");
        return Response.success();
    }


}
