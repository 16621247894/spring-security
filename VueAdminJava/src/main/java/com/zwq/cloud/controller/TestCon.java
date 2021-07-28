package com.zwq.cloud.controller;

import com.zwq.cloud.common.Response;
import com.zwq.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCon {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/test")
    public Response test() {
        int a=3/0;
        return Response.success(sysUserService.list());
    }
}
