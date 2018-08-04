package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:03
 */
@Repository
public class HeroUserDaoImpl extends DaoHelper implements HeroUserDao {
    @Override
    public List<HeroUserEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, HeroUserEntity.class);
    }
    @Override
    public HeroUserEntity queryData(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql, keys, HeroUserEntity.class);
    }

    @Override
    public int count(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql, keys, Integer.class);
    }
}
