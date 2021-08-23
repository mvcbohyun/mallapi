package com.kinsight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {

    
    @RequestMapping(value="/")
    public String index() {
    	log.info("HomeController test");
        return "index";
    }
    

}
