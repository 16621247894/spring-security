package com.zwq.cloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.service.SysMenuService;
import com.zwq.cloud.service.SysRoleService;
import com.zwq.cloud.service.SysUserRoleService;
import com.zwq.cloud.service.SysUserService;
import com.zwq.cloud.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuqing.zhu
 */
public class BaseController<T> {

    @Autowired
    HttpServletRequest request;
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    RedisCache redisCache;


    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    /**
     * 获取页面
     *
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(request, "cuurent", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);
        return new Page(current, size);
    }

    public Response success(Page<T> pageInfo) {
        pageInfo.setTotal(pageInfo.getRecords().size());
        return Response.success(pageInfo);
    }


}
