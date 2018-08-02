package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComMonthDistributionEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:16
 */
public interface ECommerceMonthDistributionDao {

    /**
     * 获取月份分布数据
     * @param sql
     * @param keys
     * @return
     */
    public List<EComMonthDistributionEntity> listData(String sql, Object[] keys);
    public void updateYearData(String sql, List<EComMonthDistributionEntity> eComYearDistributionEntities);
}
