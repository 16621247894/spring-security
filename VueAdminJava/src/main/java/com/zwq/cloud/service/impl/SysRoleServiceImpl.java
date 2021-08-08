package com.zwq.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwq.cloud.entity.SysRole;
import com.zwq.cloud.mapper.SysRoleMapper;
import com.zwq.cloud.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {



    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        return baseMapper.getRoleByUserId(userId);
    }
}
