package com.zwq.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwq.cloud.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
