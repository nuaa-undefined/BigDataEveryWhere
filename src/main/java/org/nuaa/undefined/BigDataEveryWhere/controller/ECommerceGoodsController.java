package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceGoodsEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.Response;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceGoodsService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:29
 * @Description:
 */
@Controller
@CrossOrigin
@RequestMapping("/ecommerce/goods")
public class ECommerceGoodsController {
    @Autowired
    private ECommerceGoodsService eCommerceGoodsService;

    //获取销售总额最高的商品列表(top10)
    @RequestMapping(value = "/totalAmountsTopList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity <ECommerceGoodsEntity> listTotalAmountsTopData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceGoodsService.listSumMoneyTopGoods()
        );
    }

    //获取购买放弃率最低的商品列表(top10)
    @RequestMapping(value = "/abandonTopGoodsTopList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity <ECommerceGoodsEntity> listAbandonRateTopData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceGoodsService.listAbandonTopGoods()
        );
    }

    //获取男性购买率最高的商品列表(top10)
    @RequestMapping(value = "/malePurchasedTopGoodsList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity <ECommerceGoodsEntity> listMalePurchasedTopData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceGoodsService.listMalePurchasedTopGoods()
        );
    }

    //获取女性购买率最高的商品列表(top10)
    @RequestMapping(value = "/femalePurchasedTopGoodsList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity <ECommerceGoodsEntity> listFemalePurchasedTopData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceGoodsService.listFemalePurchasedTopGoods()
        );
    }

    //获取分页商品数据
    @GetMapping("/listGoodsData")
    public @ResponseBody
    ResponseEntity<ECommerceGoodsEntity> listData(int page, int limit){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                eCommerceGoodsService.listData(page,limit)
        );
    }

    //查询商品信息
    @GetMapping("/goodsInfoList")
    public @ResponseBody
    ResponseEntity<ECommerceGoodsEntity> goodsInfoData(String id){
        List<ECommerceGoodsEntity> res = eCommerceGoodsService.getGoodsInfo(id);
        return res != null ?
                new ResponseEntity<>(Response.GET_DATA_SUCCESS_CODE,"获取数据成功",res):
                new ResponseEntity<>(400, "该商品不存在", 0, null);
    }
}
