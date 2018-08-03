package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:01
 */
public interface HeroDao {
    public List<HeroEntity> listData(String sql, Object[] keys);
}
