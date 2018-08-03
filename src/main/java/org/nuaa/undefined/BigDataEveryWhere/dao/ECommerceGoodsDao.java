package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceGoodsEntity;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:07
 * @Description:
 */
public interface ECommerceGoodsDao {

    public List<ECommerceGoodsEntity> listData(String sql,Object[] keys);

    public int count();

    public int count(String sql,Object[] keys);
}
