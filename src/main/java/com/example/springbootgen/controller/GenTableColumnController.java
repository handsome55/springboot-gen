package com.example.springbootgen.controller;

import com.example.springbootgen.service.IGenTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 9:46
 */
@RestController
@RequestMapping("gen_table_column")
public class GenTableColumnController {

    @Autowired
    private IGenTableColumnService genTableColumnService;

}
