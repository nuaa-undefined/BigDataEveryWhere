package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:17
 */
public interface HeroService {
    /**
     * 根据英雄名获取英雄数据
     * @param heroName
     * @return
     */
    public List<HeroEntity> getHeroInfo(String heroName);
}
