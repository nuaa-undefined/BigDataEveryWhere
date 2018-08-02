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

    /**
     * 统计用户个数
     * @return
     */
    public int count();

    /**
     * 条件限制统计
     * @param sql
     * @param keys
     * @return
     */
    public int count(String sql, Object[] keys);
}
