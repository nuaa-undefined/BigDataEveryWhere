package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceLogDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 15:26
 */
@Repository
public class ECommerceLogDaoImpl extends DaoHelper implements ECommerceLogDao{
    @Override
    public List<ECommerceLogEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, ECommerceLogEntity.class);
    }
}
