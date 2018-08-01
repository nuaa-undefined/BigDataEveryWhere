package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 20:47
 */
public interface ECommerceUserDao {
    /**
     * 查询用户数据
     * @param sql
     * @param keys
     * @return
     */
    public List<ECommerceUserEntity> listData(String sql, Object[] keys);
}
