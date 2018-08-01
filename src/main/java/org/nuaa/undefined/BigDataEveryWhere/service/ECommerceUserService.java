package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:07
 */
public interface ECommerceUserService {
    /**
     * 获取用户数据
     * @param limit
     * @return
     */
    public List<ECommerceUserEntity> listData(int limit);
}
