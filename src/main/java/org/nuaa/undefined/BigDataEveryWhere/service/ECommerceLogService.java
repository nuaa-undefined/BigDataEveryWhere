package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.EComYearDistributionEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceLogEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/2 16:06
 */
public interface ECommerceLogService {
    /**
     * 获取单笔交易值最高的10条记录
     * @return
     */
    public List<ECommerceLogEntity> listMaxConsumeTopLog();

    /**
     * 获取购物车放弃率数据
     * @return
     */
    public List<EComYearDistributionEntity> listShoppingGiveUpData();

}
