package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 16:17
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/log")
public class ECommerceLogController {
    @Autowired
    private ECommerceLogService eCommerceLogService;

    @RequestMapping(value = "/moneyTopList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<ECommerceLogEntity> listMoneyTopData(int page, int limit) {
        return new ResponseEntity<>(
                ResponseEntity.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceLogService.listMaxConsumeTopLog()
        );
    }
}
