package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:04
 */
@Repository
public class ECommerceUserDaoImpl extends DaoHelper implements ECommerceUserDao {
    @Override
    public List<ECommerceUserEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, ECommerceUserEntity.class);
    }

    @Override
    public int count() {
        return getJdbcTemplate().queryForObject(
                "select count(*) from e_commerce_user",
                Integer.class
        );
    }

    @Override
    public int count(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql, keys, Integer.class);
    }
}
