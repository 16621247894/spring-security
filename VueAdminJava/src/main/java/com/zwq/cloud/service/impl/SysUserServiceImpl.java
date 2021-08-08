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

import com.zwq.cloud.utils.RedisCache;
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

    @Autowired
    RedisCache redisCache;
    private static final String AUTH = "GrantedAuthority:";

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

        if (redisCache.hasKey(AUTH + sysUser.getUsername())) {
            // redis读取
            //System.out.println("redis读取");
            authority = (String) redisCache.get(AUTH + sysUser.getUsername());
            return authority;
        }

        // 获取角色编码
        List<SysRole> roles = sysRoleService.getRoleByUserId(userId);
        if (CollectionUtils.isNotEmpty(roles)) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));

            // 将指定字符拼接到字符串后面
            authority = roleCodes.concat(",");
        }
        // 获取菜单操作编码
        List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
        if (CollectionUtils.isNotEmpty(menuIds)) {
            List<SysMenu> menus = sysMenuService.listByIds(menuIds);
            String menuPerms = menus.parallelStream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            authority = authority.concat(menuPerms);
        }
        //System.out.println("authority:" + authority);
        redisCache.set(AUTH + sysUser.getUsername(), authority, 10L);
        //clearAuthorityInfoByRole(new Long(1));
        return authority;
    }

    /**
     * 清除
     *
     * @param username
     */
    @Override
    public void clearAuthorityInfoByUser(String username) {
        redisCache.delete(AUTH + username);
    }

    @Override
    public void clearAuthorityInfoByRole(Long roleId) {
        String sql = "select user_id from sys_user_role where role_id =" + roleId;
        clearBySql(sql);
    }

    @Override
    public void clearAuthorityInfoByMenu(Long menuId) {
        String sql = "select DISTINCT user_id  from sys_user_role ur left join sys_role_menu rm on ur.role_id=rm" +
                ".role_id where rm.menu_id =" + menuId;
        clearBySql(sql);
    }

    private void clearBySql(String sql) {
        List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>().inSql("id", sql));
        sysUsers.parallelStream().forEach(sysUser -> {
            clearAuthorityInfoByUser(sysUser.getUsername());
        });

    }
}

