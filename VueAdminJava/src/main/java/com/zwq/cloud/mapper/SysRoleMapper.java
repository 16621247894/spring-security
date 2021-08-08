package com.zwq.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zwq.cloud.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    List<SysRole> getRoleByUserId(Long userId);
}
