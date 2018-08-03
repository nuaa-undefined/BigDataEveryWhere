package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 15:12
 */
public interface ECommerceLogDao {
    /**
     * 获取日志数据
     * @param sql
     * @param keys
     * @return
     */
    public List<ECommerceLogEntity> listData(String sql, Object[] keys);

    /**
     * 计数
     * @param sql
     * @param keys
     * @return
     */
    public int count(String sql, Object[] keys);
}
