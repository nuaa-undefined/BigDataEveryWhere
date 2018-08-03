package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceRegionEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 15:27
 */
public interface ECommerceRegionService {
    /**
     * 获取地区全部数据
     * @return
     */
    public List<ECommerceRegionEntity> listECommerceRegionData();
}
