package com.zwq.cloud.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {


    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Response list(String name) {
        Page<SysRole> pageData = sysRoleService.page(getPage(),
                new QueryWrapper<SysRole>()
                        .like(StrUtil.isNotBlank(name), "name", name)
        );


        return Response.success(pageData);
    }

}
