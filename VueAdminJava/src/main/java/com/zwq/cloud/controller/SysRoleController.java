package com.zwq.cloud.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysRole;
import com.zwq.cloud.entity.SysRoleMenu;
import com.zwq.cloud.utils.Constants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
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

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Response save(@Validated @RequestBody SysRole sysRole) {

        sysRole.setCreated(LocalDateTime.now());
        sysRole.setStatu(Constants.STATUS_ON);

        sysRoleService.save(sysRole);
        return Response.success(sysRole);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Response update(@Validated @RequestBody SysRole sysRole) {
        sysRole.setUpdated(LocalDateTime.now());

        sysRoleService.updateById(sysRole);
        // 更新缓存
        sysUserService.clearAuthorityInfoByRole(sysRole.getId());
        return Response.success(sysRole);
    }

    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public Response info(@PathVariable("id") Long id) {

        SysRole sysRole = sysRoleService.getById(id);

        // 获取角色相关联的菜单id
        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(p -> p.getMenuId()).collect(Collectors.toList());

        sysRole.setMenuIds(menuIds);
        return Response.success(sysRole);
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/perm/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public Response info(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {

        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);

            sysRoleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        sysRoleMenuService.saveBatch(sysRoleMenus);

        // 删除缓存
        sysUserService.clearAuthorityInfoByRole(roleId);


        return Response.success(menuIds);
    }
}
