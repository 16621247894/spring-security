package com.zwq.cloud.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuqing.zhu
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}
