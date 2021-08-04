package com.zwq.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwq.cloud.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service

public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);


    String getUserAuthorityInfo(Long userId);
}
