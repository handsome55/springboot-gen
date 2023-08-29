package com.example.springbootgen.controller;

import com.example.springbootgen.model.GenDatabase;
import com.example.springbootgen.service.IGenDatabaseService;
import com.example.springbootgen.utils.R;
import com.example.springbootgen.utils.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/25 17:00
 */
@RestController
@RequestMapping("/gen_database")
public class GenDatabaseController {

    @Autowired
    private IGenDatabaseService genDatabaseService;

    @PostMapping("/add")
    public R<Long> add(@Validated @RequestBody GenDatabase genDatabase){
        return R.ok(genDatabaseService.insertGenDatabase(genDatabase));
    }

    @PutMapping("/update")
    public Long update(@Validated(Update.class) @RequestBody GenDatabase genDatabase){
        return genDatabaseService.updateGenDatabase(genDatabase);
    }

    @GetMapping
    public List<GenDatabase> selectGenDatabase(GenDatabase genDatabase){
        return genDatabaseService.selectGenDatabase(genDatabase);
    }

    @GetMapping("/select/{id}")
    public GenDatabase selectGenDatabaseById(@PathVariable Long id){
        return genDatabaseService.selectGenDatabaseById(id);
    }
}
