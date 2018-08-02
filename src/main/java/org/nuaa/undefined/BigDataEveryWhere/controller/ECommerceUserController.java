package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:10
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/user")
public class ECommerceUserController {
    @Autowired
    private ECommerceUserService eCommerceUserService;

    /**
     * 获取消费总额最高的用户列表(top10)
     * @return
     */
    @RequestMapping(value = "/moneyTopList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<ECommerceUserEntity> listMoneyTopData(int page, int limit) {
        return new ResponseEntity<>(
                ResponseEntity.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceUserService.listSumMoneyTopUsers()
        );
    }

    /**
     * 获取活跃度最高的用户列表（top10）
     * @return
     */
    @RequestMapping(value = "/activeTopList", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<ECommerceUserEntity> listActiveTopData(int page, int limit) {
        return new ResponseEntity<>(
                ResponseEntity.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceUserService.listActiveTopUsers()
        );
    }

    /**
     * 获取分页用户数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/listUserData")
    public @ResponseBody
    ResponseEntity<ECommerceUserEntity> listData(int page, int limit) {
        return new ResponseEntity<ECommerceUserEntity>(
            ResponseEntity.GET_DATA_SUCCESS_CODE, "获取数据成功", eCommerceUserService.listData(page, limit)
        );
    }
}
