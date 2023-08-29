package com.example.springbootgen.service.Impl;

import com.example.springbootgen.mapper.GenTableColumnMapper;
import com.example.springbootgen.model.GenTableColumn;
import com.example.springbootgen.service.IGenTableColumnService;
import com.example.springbootgen.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 9:49
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService {

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Override
    public Long addGenTableColumn(GenTableColumn genTableColumn) {
        Long id = IdGen.nextId();
        genTableColumn.setId(id);
        try {
            genTableColumnMapper.insert(genTableColumn);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
