package com.zwq.cloud.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysMenu;
import com.zwq.cloud.entity.SysRoleMenu;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.model.SysMenuDto;
import com.zwq.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
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

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Response list() {
        List<SysMenu> menus = sysMenuService.tree();
        return Response.success(menus);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Response delete(@PathVariable("id") Long id) {

        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Response.fail("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearAuthorityInfoByMenu(id);

        sysMenuService.removeById(id);
        // 同步删除中间关联表
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return Response.success("");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Response update(@Validated @RequestBody SysMenu sysMenu) {

        sysMenu.setUpdated(LocalDateTime.now());
        sysMenuService.updateById(sysMenu);
        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearAuthorityInfoByMenu(sysMenu.getId());
        return Response.success(sysMenu);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Response save(@Validated @RequestBody SysMenu sysMenu) {
        sysMenu.setCreated(LocalDateTime.now());
        sysMenuService.save(sysMenu);
        return Response.success(sysMenu);
    }

}
