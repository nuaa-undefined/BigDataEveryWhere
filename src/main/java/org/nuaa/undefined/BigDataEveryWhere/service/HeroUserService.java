package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroUserEntity;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:18
 */
public interface HeroUserService {
    /**
     * 根据用户id获取用户数据
     * @param id
     * @return
     */
    public List<HeroUserEntity> getHeroUserInfo(int id);

    /**
     * 获取用户用过的英雄
     * @param id
     * @param page
     * @param limit
     * @return
     */
    public List<HeroEntity> getHeroes(int id, int page, int limit);

    public List<HeroUserEntity> getStarUsers(int page, int limit);
}
