package org.nuaa.undefined.BigDataEveryWhere.dao;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceRegionEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 15:27
 */
public interface ECommerceRegionDao {
    /**
     * 获取区域数据
     * @param sql
     * @param keys
     * @return
     */
    public List<ECommerceRegionEntity> listData(String sql, Object[] keys);
}
