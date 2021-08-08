package com.zwq.cloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zwq.cloud.entity.SysMenu;
import com.zwq.cloud.entity.SysUser;
import com.zwq.cloud.mapper.SysMenuMapper;
import com.zwq.cloud.mapper.SysUserMapper;
import com.zwq.cloud.model.SysMenuDto;
import com.zwq.cloud.service.SysMenuService;
import com.zwq.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysMenuDto> getCurrentUserNav() {
        // 当前登录用户
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SysUser sysUser = sysUserService.getByUsername(username);
        // 菜单id
        List<Long> menuIds = sysUserMapper.getNavMenuIds(sysUser.getId());

        List<SysMenu> menus = this.listByIds(menuIds);
        // 转树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);
        // 实体转DTO
        return convert(menuTree);
    }

    private List<SysMenuDto> convert(List<SysMenu> menuTree) {
        List<SysMenuDto> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            SysMenuDto dto = new SysMenuDto();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            if (CollectionUtils.isNotEmpty(m.getChildren())) {
                // 子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }

    @Override
    public List<SysMenu> tree() {

        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByAsc("orderNum"));

        // 转成树状结构
        return buildTreeMenu(sysMenus);
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {

        List<SysMenu> parendMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (SysMenu menu : menus) {
            // 提取出父节点
            if (menu.getParentId() == 0L) {
                parendMenus.add(menu);
            }

            for (SysMenu e : menus) {
                if (menu.getId().equals(e.getParentId())) {
                    menu.getChildren().add(e);
                }
            }

        }

        return parendMenus;
    }

}
