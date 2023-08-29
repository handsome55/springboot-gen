package com.example.springbootgen.service;

import com.example.springbootgen.model.GenTable;
import com.example.springbootgen.model.GenTableColumn;
import com.example.springbootgen.model.GenTablePreViewBo;

import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 17:20
 */
public interface IGenTableService {

    Long addGenTable(GenTable genTable);

    List<GenTablePreViewBo.GenTableCodeBO> preViewGen(Long id);

    Long deleteGenTable(Long id);

    Long updateGenTable(GenTable genTable);
}
