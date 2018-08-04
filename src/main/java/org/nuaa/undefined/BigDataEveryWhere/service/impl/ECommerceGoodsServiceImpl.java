package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceGoodsDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceGoodsEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:23
 * @Description:
 */
@Service
public class ECommerceGoodsServiceImpl implements ECommerceGoodsService {
    @Autowired
    private ECommerceGoodsDao eCommerceGoodsDao;

    @Override
    public List<ECommerceGoodsEntity> listSumMoneyTopGoods() {
        String sql = "select id,total_amounts from e_commerce_goods order by total_amounts desc limit 10";
        return eCommerceGoodsDao.listData(sql,new Object[]{});
    }

    @Override
    public List<ECommerceGoodsEntity> listAbandonTopGoods() {
        String sql = "select id,abandon_rate ,fail_counts from e_commerce_goods order by abandon_rate limit 10";
        return eCommerceGoodsDao.listData(sql,new Object[]{});
    }

    @Override
    public List<ECommerceGoodsEntity> listMalePurchasedTopGoods() {
        String sql = "select id, male_rate, male_counts from e_commerce_goods order by male_rate desc limit 10";
        return eCommerceGoodsDao.listData(sql,new Object[]{});
    }

    @Override
    public List<ECommerceGoodsEntity> listFemalePurchasedTopGoods() {
        String sql = "select id, female_rate, female_counts from e_commerce_goods order by female_rate desc limit 10";
        return eCommerceGoodsDao.listData(sql,new Object[]{});
    }

    @Override
    public List<ECommerceGoodsEntity> listData(int page, int limit) {
        return eCommerceGoodsDao.listData(
                "select  * from e_commerce_goods limit ?,?",
                new Object[]{limit * (page - 1),limit}
        );
    }

    @Override
    public int getGoodsNum() {
        return eCommerceGoodsDao.count();
    }

    @Override
    public List<ECommerceGoodsEntity> getGoodsInfo(String id) {
        String sql = "SELECT * FROM e_commerce_goods WHERE id = '" + id + "'";
        List<ECommerceGoodsEntity> res = eCommerceGoodsDao.listData(sql,new Object[]{});
        return res.size() == 0 ? null:res;
    }
}
