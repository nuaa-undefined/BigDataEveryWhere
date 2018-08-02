package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceTimeDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:50
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/time")
public class ECommerceTimeController {
    @Autowired
    private ECommerceTimeDistributionService eCommerceTimeDistributionService;

    @GetMapping("/update")
    public @ResponseBody
    String update() {
        eCommerceTimeDistributionService.updateData();
        return "success";
    }
}
