package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:07
 */
public interface ECommerceYearDistributionDao {
    /**
     * 获取年度分布数据
     * @param sql
     * @param keys
     * @return
     */
    public List<EComYearDistributionEntity> listData(String sql, Object[] keys);
    public void updateYearData(String sql, List<EComYearDistributionEntity> eComYearDistributionEntities);

}
