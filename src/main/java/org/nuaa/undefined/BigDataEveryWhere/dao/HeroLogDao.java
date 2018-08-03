package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroLogEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:00
 */
public interface HeroLogDao {
    public List<HeroLogEntity> listData(String sql, Object[] keys);
    public int count(String sql, Object[] keys);
}
