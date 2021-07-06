package com.zwq.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwq.security.entiry.UsersVO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UsersVO> {
}
