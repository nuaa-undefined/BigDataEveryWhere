package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 21:13
 */
public interface ECommerceTimeDistributionService {

    /**
     * 获取各年的消费总额
     * @return
     */
    public List<EComYearDistributionEntity> listYearMoney();


    public void updateData();
}
