package com.zwq.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zwq.cloud.entity.SysMenu;
import com.zwq.cloud.model.SysMenuDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenuDto> getCurrentUserNav();

    /**
     * 获取所有菜单信息
     *
     * @return
     */
    List<SysMenu> tree();
}
