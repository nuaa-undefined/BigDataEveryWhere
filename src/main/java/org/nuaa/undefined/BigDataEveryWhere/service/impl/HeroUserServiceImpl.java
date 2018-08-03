package org.nuaa.undefined.BigDataEveryWhere.service.impl;

import org.nuaa.undefined.BigDataEveryWhere.dao.HeroDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroLogDao;
import org.nuaa.undefined.BigDataEveryWhere.dao.HeroUserDao;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroLogEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.HeroUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.service.HeroUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/8/3 17:20
 */
@Service
public class HeroUserServiceImpl implements HeroUserService{
    @Autowired
    private HeroLogDao heroLogDao;
    @Autowired
    private HeroUserDao heroUserDao;
    @Autowired
    private HeroDao heroDao;
    @Override
    public List<HeroUserEntity> getHeroUserInfo(int id) {
        List<HeroUserEntity> heroUserEntities = heroUserDao.listData("select * from hero_user where id = ?", new Object[]{id});
        if (heroUserEntities.size() == 0) {
            return null;
        }
        List<HeroLogEntity> heroLogEntities = heroLogDao.listData("select * from hero_log where user_id = ?", new Object[]{id});
        List<Integer> labelUseNum = new ArrayList<>();
        List<Integer> labelWinNum = new ArrayList<>();
        List<Double> userWinRate = new ArrayList<>();
        Map<String, HeroEntity> heroEntityMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            labelUseNum.add(0);
            labelWinNum.add(0);
        }

        for (HeroLogEntity in : heroLogEntities) {
            labelUseNum.set(in.getLabel() - 1, labelUseNum.get(in.getLabel() - 1) + 1);
            labelWinNum.set(in.getLabel() - 1, in.getStatus() == 1 ? labelWinNum.get(in.getLabel() - 1) + 1 : labelWinNum.get(in.getLabel() - 1));
            if (!heroEntityMap.containsKey(in.getHeroName())) {
                HeroEntity heroEntity = new HeroEntity();
                heroEntity.setName(in.getHeroName());
                if (in.getStatus() == 1) {
                    heroEntity.setWinNum(1);
                    heroEntity.setWinNum(0);
                } else {
                    heroEntity.setFailNum(1);
                    heroEntity.setWinNum(0);
                }
                heroEntityMap.put(in.getHeroName(), heroEntity);
            } else {
                if (in.getStatus() == 1) {
                    heroEntityMap.get(in.getHeroName()).setWinNum(heroEntityMap.get(in.getHeroName()).getWinNum() + 1);
                } else {
                    heroEntityMap.get(in.getHeroName()).setFailNum(heroEntityMap.get(in.getHeroName()).getFailNum() + 1);
                }
            }
        }
        List<HeroEntity> heroEntities = new ArrayList<>();
        for (Map.Entry<String, HeroEntity> entityEntry : heroEntityMap.entrySet()) {
            heroEntities.add(entityEntry.getValue());
        }
        heroEntities = heroEntities.stream()
                .filter(x -> x.getWinNum() > 1 && x.getWinNum() != null && x.getFailNum() != null)
                .sorted((x, y) ->
                        new Double(y.getWinNum() /(double) (y.getWinNum() + y.getFailNum())).compareTo(
                                new Double(x.getWinNum() / (double) (x.getWinNum() + x.getFailNum()))
                        )
                ).collect(Collectors.toList());
        int i = 3;
        while (i < heroEntities.size()) {
            heroEntities.remove(i);
        }
        heroUserEntities.get(0).setLabelUseNum(labelUseNum);
        heroUserEntities.get(0).setLabelWinNum(labelWinNum);
        heroUserEntities.get(0).setTop3Heroes(heroEntities);
        return heroUserEntities;
    }

    @Override
    public List<HeroEntity> getHeroes(int id, int page, int limit) {
        String sql = "select * from hero_log where user_id = ?";
        List<HeroLogEntity> heroLogEntities = heroLogDao.listData(sql, new Object[]{id});
        Map<String, HeroEntity> heroEntityMap = new HashMap<>();
        for (HeroLogEntity in : heroLogEntities) {
            if (heroEntityMap.containsKey(in.getHeroName())) {
                HeroEntity hero = heroDao.listData("select * from hero where name = ?", new Object[]{in.getHeroName()}).get(0);
                if (in.getStatus() == 1) {
                    hero.setWinNum(1);
                    hero.setFailNum(0);
                } else {
                    hero.setWinNum(0);
                    hero.setFailNum(1);
                }
                heroEntityMap.put(in.getHeroName(), hero);
            } else {
                if (in.getStatus() == 1) {
                    heroEntityMap.get(in.getHeroName()).setWinNum(heroEntityMap.get(in.getHeroName()).getWinNum() + 1);
                } else {
                    heroEntityMap.get(in.getHeroName()).setFailNum(heroEntityMap.get(in.getHeroName()).getFailNum() + 1);
                }
            }
        }
        return null;
    }
}
