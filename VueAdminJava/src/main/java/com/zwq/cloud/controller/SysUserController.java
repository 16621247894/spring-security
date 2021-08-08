package com.zwq.cloud.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zwq.cloud.common.Response;
import com.zwq.cloud.entity.SysRole;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.entity.SysUserRole;
import com.zwq.cloud.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser> {


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Response list(String username) {

        Page<SysUser> pageData = sysUserService.page(getPage(), new QueryWrapper<SysUser>()
                .likeRight(StrUtil.isNotBlank(username), "username", username));
        pageData.getRecords().forEach(sysUser -> {
            sysUser.setSysRoles(sysRoleService.getRoleByUserId(sysUser.getId()));
        });
        return success(pageData);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Response info(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        Assert.notNull(sysUser, "找不到该用户");
        List<SysRole> roles = sysRoleService.getRoleByUserId(id);
        sysUser.setSysRoles(roles);
        return Response.success(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public Response rolePerm(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {

        List<SysUserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            userRoles.add(sysUserRole);
        });

        // 删除所有
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        sysUserRoleService.saveBatch(userRoles);

        // 删除缓存
        SysUser sysUser = sysUserService.getById(userId);
        sysUserService.clearAuthorityInfoByUser(sysUser.getUsername());
        return Response.success("");
    }

    /**
     * 添加或者修改方法
     *
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Response save(@Validated @RequestBody SysUser sysUser) {

        sysUser.setCreated(LocalDateTime.now());
        sysUser.setStatu(Constants.STATUS_ON);

        // 默认密码
        String password = passwordEncoder.encode(Constants.DEFULT_PASSWORD);
        sysUser.setPassword(password);

        // 默认头像
        sysUser.setAvatar(Constants.DEFULT_AVATAR);

        sysUserService.save(sysUser);
        return Response.success(sysUser);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Response update(@Validated @RequestBody SysUser sysUser) {
        sysUser.setUpdated(LocalDateTime.now());
        sysUserService.updateById(sysUser);
        sysUserService.clearAuthorityInfoByUser(sysUser.getUsername());
        return Response.success(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Response delete(@RequestBody Long[] ids) {
        sysUserService.removeByIds(Arrays.asList(ids));
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("user_id", ids));
        return Response.success();
    }

}
