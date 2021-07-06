package com.zwq.security.entiry;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class UsersVO {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username",fill= FieldFill.DEFAULT)
    private String username;
    @TableField(value = "password" ,fill= FieldFill.DEFAULT)
    private String password;
}
