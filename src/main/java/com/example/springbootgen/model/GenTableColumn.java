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
 * @date 2023/6/25 17:32
 */
@TableName("gen_table_column")
@Data
public class GenTableColumn implements Serializable {

    private static final long serialVersionUID = -2591584978995436095L;

    @NotNull(groups = {Update.class},message = "表ID不能为空")
    private Long id;

    @NotNull(message = "表ID不能为空")
    private Long genTableId;

    @NotBlank(message = "是否主键不能为空")
    private String isTableKey;

    @NotBlank(message = "字段名称不能为空")
    private String columnName;

    @NotBlank(message = "字段含义不能为空")
    private String columnRemark;

    private String columnType;

    private String columnJavaType;

}
