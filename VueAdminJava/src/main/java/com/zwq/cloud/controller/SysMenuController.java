package com.zwq.cloud.controller;


import cn.hutool.core.map.MapUtil;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.model.SysMenuDto;
import com.zwq.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {


    @GetMapping("/nav")
    public Response nav(Principal principal) {
        // 获取当前登录用户名
        SysUser sysUser = sysUserService.getByUsername(principal.getName());
        // 获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserNav();
        return Response.success(MapUtil.builder().put("authoritys", authorityInfoArray).put("nav", navs).map());


    }

}
