package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:10
 */
@Controller
@RequestMapping("/ecommerce")
public class ECommerceController {
    @Autowired
    private ECommerceUserService eCommerceUserService;

    @GetMapping("/listUserData")
    public @ResponseBody
    ResponseEntity<ECommerceUserEntity> listData(int limit) {
        return new ResponseEntity<ECommerceUserEntity>(
                200, "获取数据成功", eCommerceUserService.listData(limit)
        );
    }
}
