package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/5 12:46
 */
public interface ECommerceService {
    public ECommerceEntity getSystemData();
    public List<EComYearDistributionEntity> getUserAddNumByYear();
}
