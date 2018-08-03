package org.nuaa.undefined.BigDataEveryWhere.service;

import com.sun.xml.internal.xsom.impl.ListSimpleTypeImpl;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceGoodsEntity;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:19
 * @Description:
 */
public interface ECommerceGoodsService {
    //获取消费总额最高的前10商品
    public List<ECommerceGoodsEntity> listSumMoneyTopGoods();

    public List<ECommerceGoodsEntity> listAbandonTopGoods();

    public List<ECommerceGoodsEntity> listMalePurchasedTopGoods();

    public List<ECommerceGoodsEntity> listFemalePurchasedTopGoods();

    public List<ECommerceGoodsEntity> listData(int page, int limit);

    public int getGoodsNum();

}
