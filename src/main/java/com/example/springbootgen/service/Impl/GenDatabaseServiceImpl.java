package com.example.springbootgen.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootgen.mapper.GenDatabaseMapper;
import com.example.springbootgen.model.GenDatabase;
import com.example.springbootgen.service.IGenDatabaseService;
import com.example.springbootgen.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 17:00
 */
@Service
public class GenDatabaseServiceImpl implements IGenDatabaseService {

    @Autowired
    private GenDatabaseMapper genDatabaseMapper;

    @Override
    @Transactional
    public Long insertGenDatabase(GenDatabase genDatabase) {
        try {
            Long id=IdGen.nextId();
            genDatabase.setId(id);
            genDatabaseMapper.insert(genDatabase);
            return id;
        } catch (Exception e) {
            throw new RuntimeException("插入数据库表数据错误");
        }
    }

    @Override
    @Transactional
    public Long updateGenDatabase(GenDatabase genDatabase) {
        try {
            genDatabaseMapper.updateById(genDatabase);
            return genDatabase.getId();
        } catch (Exception e) {
            throw new RuntimeException("修改数据库表数据错误");
        }
    }

    @Override
    public List<GenDatabase> selectGenDatabase(GenDatabase genDatabase) {
        QueryWrapper<GenDatabase> wrapper = new QueryWrapper<>();
        wrapper.eq("type", StrUtil.isNotBlank(genDatabase.getType()));
        return genDatabaseMapper.selectList(wrapper);
    }

    @Override
    public GenDatabase selectGenDatabaseById(Long id) {
        return genDatabaseMapper.selectById(id);
    }
}
