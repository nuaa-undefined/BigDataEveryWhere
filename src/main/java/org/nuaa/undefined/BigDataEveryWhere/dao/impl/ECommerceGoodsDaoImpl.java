package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceGoodsDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceGoodsEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 11:12
 * @Description:
 */
@Repository
public class ECommerceGoodsDaoImpl extends DaoHelper implements ECommerceGoodsDao {
    @Override
    public List<ECommerceGoodsEntity> listData(String sql, Object[] keys) {
        return query(sql,keys,ECommerceGoodsEntity.class);
    }

    @Override
    public int count() {
        return getJdbcTemplate().queryForObject(
                "select count(*) from e_commerce_goods",
                Integer.class
        );
    }

    @Override
    public int count(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql,keys,Integer.class);
    }
}
