package com.zwq.cloud.security;

import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义用户登录
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }

        return new AccountUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    /**
     * 根据用户获取权限信息（角色，菜单权限）
     *
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 角色(ROLE_admin)  权限(sys:user:list)
        String authority=sysUserService.getUserAuthorityInfo(userId);


        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }

}
