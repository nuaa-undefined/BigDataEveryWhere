package org.nuaa.undefined.BigDataEveryWhere.dao.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.DaoHelper;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:02
 */
@Repository
public class HeroDaoImpl extends DaoHelper implements HeroDao{
    @Override
    public List<HeroEntity> listData(String sql, Object[] keys) {
        return query(sql, keys, HeroEntity.class);
    }
}
