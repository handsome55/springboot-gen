package com.example.springbootgen.controller;

import com.example.springbootgen.model.GenTable;
import com.example.springbootgen.model.GenTableColumn;
import com.example.springbootgen.model.GenTablePreViewBo;
import com.example.springbootgen.service.IGenTableService;
import com.example.springbootgen.utils.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 17:50
 */
@RestController
@RequestMapping("/gen_table")
public class GenTableController {

    @Autowired
    private IGenTableService genTableService;

    @PostMapping("/add")
    public Long addGenTable(@Validated @RequestBody GenTable genTable) {
        return genTableService.addGenTable(genTable);
    }

    /**
     * 预览代码查看
     * */
    @GetMapping("/{id}")
    public List<GenTablePreViewBo.GenTableCodeBO> preViewGen(@PathVariable Long id){
       return genTableService.preViewGen(id);
    }

    @DeleteMapping("/{id}")
    public Long deleteGenTable(@PathVariable Long id){
        return genTableService.deleteGenTable(id);
    }

    @PutMapping("/update")
    public Long updateGenTable(@Validated(value = {Update.class}) @RequestBody GenTable genTable){
        return genTableService.updateGenTable(genTable);
    }
}
