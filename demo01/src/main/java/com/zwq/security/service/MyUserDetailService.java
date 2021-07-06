package com.zwq.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zwq.security.entiry.UsersVO;
import com.zwq.security.mapper.UserMapper;
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

        UsersVO usersVO = userMapper.selectOne(new QueryWrapper<UsersVO>().eq("username", username));
        if(usersVO==null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        // https://www.bilibili.com/video/BV15a411A7kP?p=9  快结束

        List<GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("role");


        return new User(usersVO.getUsername(), new BCryptPasswordEncoder().encode(usersVO.getPassword()), authorities);
    }
}
