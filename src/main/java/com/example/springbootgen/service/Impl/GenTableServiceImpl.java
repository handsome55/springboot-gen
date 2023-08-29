package com.example.springbootgen.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootgen.mapper.GenDatabaseMapper;
import com.example.springbootgen.mapper.GenTableColumnMapper;
import com.example.springbootgen.mapper.GenTableMapper;
import com.example.springbootgen.model.GenDatabase;
import com.example.springbootgen.model.GenTable;
import com.example.springbootgen.model.GenTableColumn;
import com.example.springbootgen.model.GenTablePreViewBo;
import com.example.springbootgen.service.IGenTableService;
import com.example.springbootgen.utils.GenDbTypeUtil;
import com.example.springbootgen.utils.IdGen;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 17:21
 */
@Service
public class GenTableServiceImpl implements IGenTableService {

    @Autowired
    private GenDatabaseMapper genDatabaseMapper;

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    private static final List<JSONObject> GEN_BACKEND_FILE_LIST = CollectionUtil.newArrayList(
            JSONUtil.createObj().set("name", "Controller.java.btl").set("path", "controller"),
            JSONUtil.createObj().set("name", "PO.java.btl").set("path", "model" + File.separator + "po"));
//            JSONUtil.createObj().set("name", "BO.java.btl").set("path", "model" + File.separator + "bo"),
//            JSONUtil.createObj().set("name", "DTO.java.btl").set("path", "model" + File.separator + "dto"),
//            JSONUtil.createObj().set("name", "VO.java.btl").set("path", "model" + File.separator + "vo"),
//            JSONUtil.createObj().set("name", "Convertor.java.btl").set("path", "model" + File.separator + "mapping"),
//            JSONUtil.createObj().set("name", "Mapper.java.btl").set("path", "data" + File.separator + "repository"),
//            JSONUtil.createObj().set("name", "Mapper.xml.btl").set("path", "mapper"),
//            JSONUtil.createObj().set("name", "Service.java.btl").set("path", "service"),
//            JSONUtil.createObj().set("name", "ServiceImpl.java.btl").set("path", "service" + File.separator + "impl")


    @Override
    @Transactional
    public Long addGenTable(GenTable genTable) {
        QueryWrapper<GenTable> wrapper = new QueryWrapper<>();
        wrapper.eq("db_table",genTable.getDbTable());
        wrapper.eq("gen_database_id",genTable.getGenDatabaseId());
        Long count = genTableMapper.selectCount(wrapper);
        boolean exists = genTableMapper.exists(wrapper);
        if(exists){
            throw new RuntimeException("表已经存在，不可重复添加");
        }
        try {
            Long id= IdGen.nextId();
            genTable.setId(id);
            genTableMapper.insert(genTable);
            List<GenTableColumn> genTableColumns = this.selectGenTableColumn(genTable);
            genTableColumns.forEach(item->{
                item.setId(IdGen.nextId());
                genTableColumnMapper.insert(item);
            });
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<GenTablePreViewBo.GenTableCodeBO> preViewGen(Long id) {
        GenTable genTable = genTableMapper.selectById(id);
        JSONObject bindingJsonObject = this.getBindingJsonObject(genTable);
        String basePath="src"+ File.separator+"main";
        //后端基础路径
        String backendBasicPath=basePath+File.separator+"java"+File.separator+StrUtil.replace(genTable.getPackageName(),".",File.separator)+File.separator;
        //后端
        try {
            GroupTemplate groupTemplate = new GroupTemplate(new ClasspathResourceLoader("backend"), Configuration.defaultConfiguration());
            List<GenTablePreViewBo.GenTableCodeBO> genTableCodeBOList = CollectionUtil.newArrayList();

            GEN_BACKEND_FILE_LIST.forEach(item->{
                String fileName= item.getStr("name");
                String filePath= item.getStr("path");
                Template template = groupTemplate.getTemplate(fileName);
                template.binding(bindingJsonObject);
                String resultName = StrUtil.removeSuffix(fileName, ".btl");
                GenTablePreViewBo.GenTableCodeBO genTableCodeBO = new GenTablePreViewBo.GenTableCodeBO();
                genTableCodeBO.setCodeFileName(genTable.getClassName()+resultName);
                genTableCodeBO.setCodeFileWithPathName(backendBasicPath+filePath+File.separator+genTable.getClassName()+resultName);
                genTableCodeBO.setCodeFileContent(template.render());
                genTableCodeBOList.add(genTableCodeBO);
            });
            return genTableCodeBOList;
        } catch (IOException e) {
            throw new RuntimeException("预览代码失败");
        }
    }

    @Override
    @Transactional
    public Long deleteGenTable(Long id) {
        QueryWrapper<GenTable> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        genTableMapper.delete(wrapper);

        QueryWrapper<GenTableColumn> columnWrapper = new QueryWrapper<>();
        columnWrapper.eq("gen_table_id",id);
        genTableColumnMapper.delete(columnWrapper);
        return id;
    }

    @Override
    public Long updateGenTable(GenTable genTable) {
        QueryWrapper<GenTable> wrapper = new QueryWrapper<>();
        wrapper.eq("db_table", genTable.getDbTable())
                .eq("gen_database_id", genTable.getGenDatabaseId());
        boolean exists = genTableMapper.exists(wrapper);
        if (exists) {
            genTableMapper.updateById(genTable);
        } else {
            QueryWrapper<GenTableColumn> databaseWrapper = new QueryWrapper<>();
            databaseWrapper.eq("gen_table_id", genTable.getId());
            genTableColumnMapper.delete(databaseWrapper);
            List<GenTableColumn> genTableColumns = this.selectGenTableColumn(genTable);
            genTableColumns.forEach(item -> {
                item.setId(IdGen.nextId());
                genTableColumnMapper.insert(item);
            });
        }
        return genTable.getId();
    }

    /**
     * 根据代码生成基础获取构造的参数
     */
    public JSONObject getBindingJsonObject(GenTable genTable) {
        JSONObject bindingJsonObject = JSONUtil.createObj();
        // 代码模块名
        bindingJsonObject.set("moduleName", genTable.getModule());
        // 功能名
        bindingJsonObject.set("functionName", genTable.getFunctionName());
        // 业务名
        bindingJsonObject.set("serviceName", genTable.getService());
        // 包名
        bindingJsonObject.set("packageName", genTable.getPackageName());
        // 库名
        bindingJsonObject.set("dbTable", genTable.getDbTable());
        // 类名
        bindingJsonObject.set("className", genTable.getClassName());
        // 作者
        bindingJsonObject.set("authorName", genTable.getAuthor());
        // 生成时间
        bindingJsonObject.set("genTime", DateUtil.format(DateTime.now(), " yyyy/MM/dd HH:mm"));
        // 类首字母小写名
        bindingJsonObject.set("classNameFirstLower", StrUtil.lowerFirst(genTable.getClassName()));
        //类名转 "-"
        bindingJsonObject.set("classNameSymbolCase", StrUtil.toSymbolCase(genTable.getClassName(), '-'));

        // 定义配置详情列表
        List<JSONObject> configList = CollectionUtil.newArrayList();

        genTableColumnMapper.selectList(new LambdaQueryWrapper<GenTableColumn>().eq(GenTableColumn::getGenTableId, genTable.getId()))
                .forEach(genConfig -> {
                    // 定义字段信息
                    JSONObject configItem = JSONUtil.createObj();
                    // 如果是主键，则无需作为添加参数，需要作为编辑参数，需要主键注解
                    if (genConfig.getColumnName().equalsIgnoreCase(genTable.getDbTableKey())) {
                        configItem.set("needAdd", false);
                        configItem.set("needEdit", true);
                        configItem.set("needPage", false);
                        configItem.set("needPageType", "none");
                        configItem.set("required", true);
                        configItem.set("needTableId", true);
                        bindingJsonObject.set("dbTableKeyJavaType", genConfig.getColumnType());
                        bindingJsonObject.set("dbTableKeyRemark", genConfig.getColumnRemark());
                    }else{
                        configItem.set("needAdd", false);
                        configItem.set("needEdit", false);
                        configItem.set("needPage", false);
                        configItem.set("needPageType", "none");
                        configItem.set("required", false);
                        configItem.set("needTableId", false);
                    }
                    // 实体类型
                    configItem.set("fieldJavaType", genConfig.getColumnJavaType());
                    // 字段驼峰名
                    configItem.set("fieldNameCamelCase", StrUtil.toCamelCase(genConfig.getColumnName().toLowerCase()));
                    // 字段驼峰首字母大写名
                    configItem.set("fieldNameCamelCaseFirstUpper", StrUtil.upperFirst(StrUtil.toCamelCase(genConfig.getColumnName().toLowerCase())));
                    // 字段注释
                    configItem.set("fieldRemark", genConfig.getColumnRemark());
                    configList.add(configItem);

                });
        // 配置信息
        bindingJsonObject.set("configList", configList);

        return bindingJsonObject;
    }

    private List<GenTableColumn> selectGenTableColumn(GenTable genTable){
        GenDatabase genDatabase = genDatabaseMapper.selectById(genTable.getGenDatabaseId());
        List<GenTableColumn> list = new ArrayList<>();
        Connection conn=null;
        ResultSet res=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(genDatabase.getUrl(),genDatabase.getUsername(),genDatabase.getPassword());
            DatabaseMetaData metaData = conn.getMetaData();
            res= metaData.getColumns(null,null,genTable.getDbTable(),"%");
            while (res.next()){
                GenTableColumn genTableColumn = new GenTableColumn();
                String tableName= res.getString("COLUMN_NAME");
                genTableColumn.setColumnName(tableName);
                if (genTable.getDbTableKey().equals(tableName)){
                    genTableColumn.setIsTableKey("Y");
                }else {
                    genTableColumn.setIsTableKey("N");
                }
                String remarks = res.getString("REMARKS");
                if(ObjectUtils.isEmpty(remarks)){
                    genTableColumn.setColumnRemark(tableName);
                }else {
                    genTableColumn.setColumnRemark(remarks);
                }
                String typeName = res.getString("TYPE_NAME");
                if (ObjectUtil.isEmpty(typeName)) {
                    genTableColumn.setColumnType("NONE");
                } else {
                    genTableColumn.setColumnType(typeName);
                    genTableColumn.setColumnJavaType(GenDbTypeUtil.getJavaTypeBySqlType(typeName));
                }
                genTableColumn.setGenTableId(genTable.getId());
                list.add(genTableColumn);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.closeResultSet(res);
            JdbcUtils.closeConnection(conn);
        }
        return list;
    }
}
