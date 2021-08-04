package com.zwq.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwq.cloud.entity.SysMenu;
import com.zwq.cloud.entity.SysRole;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.mapper.SysUserMapper;
import com.zwq.cloud.service.SysMenuService;
import com.zwq.cloud.service.SysRoleService;
import com.zwq.cloud.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;

    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = getOne(new QueryWrapper<SysUser>().eq("username", username));
        return sysUser;
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        // 根据id获取数据
        SysUser sysUser = sysUserMapper.selectById(userId);
//  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        // 获取角色编码
        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + userId));
        if (CollectionUtils.isNotEmpty(roles)) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            System.out.println("获取的roleCodes:"+roleCodes);

            // 将指定字符拼接到字符串后面
            authority = roleCodes.concat(",");
        }
        // 获取菜单操作编码
        List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
        if (CollectionUtils.isNotEmpty(menuIds)) {
            List<SysMenu> menus = sysMenuService.listByIds(menuIds);
            String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            System.out.println("menuPerms:"+menuPerms);

            authority = authority.concat(menuPerms);
        }
        System.out.println("authority:"+authority);
        return authority;
    }
}
