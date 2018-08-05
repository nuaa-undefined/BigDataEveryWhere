package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/7/31 21:07
 */
public interface ECommerceUserService {

    /**
     * 获取消费总额前10的用户
     * @return
     */
    public List<ECommerceUserEntity> listSumMoneyTopUsers();

    /**
     * 获取活跃度最高的用户top10
     * @return
     */
    public List<ECommerceUserEntity> listActiveTopUsers();


    /**
     * 获取用户数据
     * @param page
     * @param limit
     * @return
     */
    public List<ECommerceUserEntity> listData(int page, int limit);

    /**
     * 获取用户数量
     * @return
     */
    public int getUserNum();

    public ResponseEntity getUserConsumeRange();
}
