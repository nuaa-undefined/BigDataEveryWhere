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
public class ECommerceDaoImpl extends DaoHelper implements ECommerceUserDao {
    @Override
    public List<ECommerceUserEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, ECommerceUserEntity.class);
    }
}
