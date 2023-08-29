package com.example.springbootgen.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springbootgen.utils.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 16:54
 */
@TableName("gen_database")
@Data
public class GenDatabase implements Serializable {
    private static final long serialVersionUID = 2417576545948560129L;

    @NotNull(groups = {Update.class},message = "数据Id不能为空")
    private Long id;

    @NotBlank(message = "数据库连接地址不能为空")
    private String url;

    @NotBlank(message = "数据库名称不能为空")
    private String name;

    private String remark;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "数据库类型不能为空")
    private String type;
}
