package com.example.springbootgen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/27 15:15
 */
@Controller
public class IndexController {

    @RequestMapping("/html")
    public String index(){
        System.out.println("1111");
        return "index.html";
    }
}
