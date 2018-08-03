package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceRegionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 15:31
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/region")
public class ECommerceRegionController {

    @Autowired
    private ECommerceRegionService eCommerceRegionService;

    @GetMapping("/listRegionData")
    public @ResponseBody
    ResponseEntity<ECommerceRegionEntity> listRegionData() {
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceRegionService.listECommerceRegionData()
        );
    }
}
