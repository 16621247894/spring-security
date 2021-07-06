package com.zwq.demo02.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwq.demo02.entity.MyUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<MyUser> {
}
