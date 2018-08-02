package org.nuaa.undefined.BigDataEveryWhere.service;

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
}
