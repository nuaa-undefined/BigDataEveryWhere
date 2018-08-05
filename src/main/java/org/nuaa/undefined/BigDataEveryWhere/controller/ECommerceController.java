package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/5 12:58
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/index")
public class ECommerceController {
    @Autowired
    private ECommerceService eCommerceService;

    @GetMapping("/info")
    public @ResponseBody
    ECommerceEntity info() {
        return eCommerceService.getSystemData();
    }

    @GetMapping("/addUser")
    public @ResponseBody
    ResponseEntity addUser() {
        return new ResponseEntity<EComYearDistributionEntity>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceService.getUserAddNumByYear()
        );
    }
}
