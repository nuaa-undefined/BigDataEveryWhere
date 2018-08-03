package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComMonthDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
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

    @GetMapping("/yearMoney")
    public @ResponseBody
    ResponseEntity<EComYearDistributionEntity> yearMoney() {
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceTimeDistributionService.listYearMoney()
        );
    }

    @GetMapping("/sexConsumeCount")
    public @ResponseBody
    ResponseEntity<EComYearDistributionEntity> sexConsumeCount() {
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceTimeDistributionService.listYearSexCountDistribute()
        );
    }

    @GetMapping("/monthDistribution")
    public @ResponseBody
    ResponseEntity<EComMonthDistributionEntity> monthContribution() {
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceTimeDistributionService.listMonthSexCountDistribute()
        );
    }

//    @GetMapping("/update")
//    public @ResponseBody
//    String update() {
//        eCommerceTimeDistributionService.updateData();
//        return "success";
//    }
}
