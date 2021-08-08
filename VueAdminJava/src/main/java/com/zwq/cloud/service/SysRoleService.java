package com.zwq.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwq.cloud.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRoleByUserId(Long userId);

}
