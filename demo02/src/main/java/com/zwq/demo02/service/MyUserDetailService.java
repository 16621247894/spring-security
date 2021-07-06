package com.zwq.demo02.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwq.demo02.entity.MyUser;
import com.zwq.demo02.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser usersVO = userMapper.selectOne(new QueryWrapper<MyUser>().eq("username", username));
        if(usersVO==null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("role");


        return new User(usersVO.getUsername(), new BCryptPasswordEncoder().encode(usersVO.getPassword()), authorities);
    }
}
