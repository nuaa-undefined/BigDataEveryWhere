package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroUserEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:01
 */
public interface HeroUserDao {
    public List<HeroUserEntity> listData(String sql, Object[] keys);
    public HeroUserEntity queryData(String sql, Object[] keys);
    public int count(String sql, Object[] keys);
}
