package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.ECommerceRegionDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.ECommerceRegionEntity;
import org.nuaa.undefined.BigDataEveryWhere.mr.ecommerce.ConstCommerceValue;
import org.nuaa.undefined.BigDataEveryWhere.service.ECommerceRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 15:28
 */
@Service
public class ECommerceRegionServiceImpl implements ECommerceRegionService {

    @Autowired
    private ECommerceRegionDao eCommerceRegionDao;

    @Override
    public List<ECommerceRegionEntity> listECommerceRegionData() {
        String sql = "select * from e_commerce_region order by sum_money desc";
        return eCommerceRegionDao.listData(sql, new Object[]{}).stream()
                .map(x -> {
                    x.setRegionName(ConstCommerceValue.CITY_CODE_NAME_MAP.get(x.getRegionCode()));
                    return x;
                }).collect(Collectors.toList());
    }
}
