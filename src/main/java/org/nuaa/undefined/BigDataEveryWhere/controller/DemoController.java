package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/30 14:42
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/desc")
    public @ResponseBody
    String demo(String name) {
        return "This is demo , "+ name;
    }
}
