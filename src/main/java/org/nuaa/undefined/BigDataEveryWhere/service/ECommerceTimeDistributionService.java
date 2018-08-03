package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComMonthDistributionEntity;
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

    /**
     * 获取各年男女消费分布
     * @return
     */
    public List<EComYearDistributionEntity> listYearSexCountDistribute();

    /**
     * 获取按月男女消费分布
     * @return
     */
    public List<EComMonthDistributionEntity> listMonthSexCountDistribute();


    public void updateData();
}
