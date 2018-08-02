package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceRegionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceRegionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 15:28
 */
@Repository
public class ECommerceRegionDaoImpl extends DaoHelper implements ECommerceRegionDao{
    @Override
    public List<ECommerceRegionEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, ECommerceRegionEntity.class);
    }
}
