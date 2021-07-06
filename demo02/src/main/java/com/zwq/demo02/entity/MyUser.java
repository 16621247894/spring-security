package com.zwq.demo02.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("users")
public class MyUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username", fill = FieldFill.DEFAULT)
    private String username;
    @TableField(value = "password", fill = FieldFill.DEFAULT)
    private String password;
}
