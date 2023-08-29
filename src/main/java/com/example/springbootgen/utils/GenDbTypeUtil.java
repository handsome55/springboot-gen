package com.example.springbootgen.utils;

import cn.hutool.core.util.ObjectUtil;
import com.example.springbootgen.enums.GenJavaTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 16:37
 */
public class GenDbTypeUtil {
    private static final Logger logger = LoggerFactory.getLogger(GenDbTypeUtil.class);
    /**
     * 根据数据库字段类型获取Java类型
     **/
    public static String getJavaTypeBySqlType(String dataType) {
        if(ObjectUtil.isEmpty(dataType)) {
            logger.info(">>> 字段的数据库类型为空，使用默认值String");
            return GenJavaTypeEnum.String.getValue();
        }
        dataType = dataType.toUpperCase();
        switch(dataType){
            case "NVARCHAR":
            case "NVARCHAR2":
            case "CHAR":
            case "VARCHAR":
            case "ENUM":
            case "SET":
            case "TEXT":
            case "LONGTEXT":
            case "NCHAR":
            case "BLOB":
            case "NCLOB":
            case "IMAGE":
            case "BPCHAR":
                return GenJavaTypeEnum.String.getValue();
            case "INTEGER":
            case "INT2":
            case "INT4":
            case "TINYINT":
            case "SMALLINT":
            case "MEDIUMINT":
            case "INT":
                return GenJavaTypeEnum.Integer.getValue();
            case "BIGINT":
            case "NUMBER":
            case "ID":
            case "INT8":
                return GenJavaTypeEnum.Long.getValue();
            case "BIT":
            case "BOOLEAN":
                return GenJavaTypeEnum.Boolean.getValue();
            case "FLOAT":
                return GenJavaTypeEnum.Float.getValue();
            case "DOUBLE":
            case "MONEY":
            case "SMALLMONEY":
                return GenJavaTypeEnum.Double.getValue();
            case "DECIMAL":
            case "NUMERIC":
            case "REAL":
                return GenJavaTypeEnum.BigDecimal.getValue();
            case "DATE":
            case "YEAR":
                return GenJavaTypeEnum.LocalDate.getValue();
            case "TIME":
                return GenJavaTypeEnum.LocalTime.getValue();
            case "DATETIME":
            case "TIMESTAMP":
                return GenJavaTypeEnum.LocalDateTime.getValue();
            default:
                logger.info(">>> 字段的数据库类型：{}无法匹配，使用默认值String", dataType);
                return GenJavaTypeEnum.String.getValue();
        }
    }
}
