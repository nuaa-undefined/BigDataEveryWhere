package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.GameUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameAllTwoEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 15:22
 * @Description:
 */
@Repository
public class GameUserDaoImpl extends DaoHelper implements GameUserDao {
    @Override
    public List<GameUserEntity> listData(String sql, Object[] keys) {
        return query(sql,keys,GameUserEntity.class);
    }

    @Override
    public List<GameAllTwoEntity> listGlobalData(String sql, Object[] keys) {
        return query(sql,keys,GameAllTwoEntity.class);
    }

    @Override
    public int count() {
        return getJdbcTemplate().queryForObject(
                "select count (*) from game_user",
                Integer.class
        );
    }

    @Override
    public int count(String sql, Object[] keys) {
        return getJdbcTemplate().queryForObject(sql,keys,Integer.class);
    }
}
