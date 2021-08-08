package com.zwq.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwq.cloud.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名查找
     *
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    /**
     * 根据用户id查找权限
     *
     * @param userId
     * @return
     */
    String getUserAuthorityInfo(Long userId);


    /**
     * 清除
     *
     * @param username
     */
    void clearAuthorityInfoByUser(String username);

    void clearAuthorityInfoByRole(Long roleId);


    void clearAuthorityInfoByMenu(Long menuId);

}
