package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroLogDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroLogEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:02
 */
@Repository
public class HeroLogDaoImpl extends DaoHelper implements HeroLogDao{
    @Override
    public List<HeroLogEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, HeroLogEntity.class);
    }

    @Override
    public int count(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql, keys, Integer.class);
    }
}
