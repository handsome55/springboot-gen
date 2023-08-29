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
 * @date 2023/6/25 17:55
 */
@TableName("gen_table")
@Data
public class GenTable implements Serializable {
    private static final long serialVersionUID = -7284365874799167189L;

    @NotNull(groups = {Update.class},message = "表ID不能为空")
    private Long id;

    @NotBlank(message = "作者名称不能为空")
    private String author;

    @NotBlank(message = "数据库表名称不能为空")
    private String dbTable;

    @NotBlank(message = "数据库主键不能为空")
    private String dbTableKey;

    private String tablePrefix;

    private String generateType;

    @NotBlank(message = "功能名称不能为空")
    private String functionName;

    @NotBlank(message = "服务名称不能为空")
    private String service;

    @NotBlank(message = "类名不能为空")
    private String className;

    @NotBlank(message = "包名不能为空")
    private String packageName;

    private int sort;

    private String module;

    @NotNull(message = "数据库ID不能为空")
    private Long genDatabaseId;

}
