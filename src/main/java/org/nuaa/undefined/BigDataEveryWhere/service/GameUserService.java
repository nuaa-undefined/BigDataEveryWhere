package org.nuaa.undefined.BigDataEveryWhere.service;

import org.nuaa.undefined.BigDataEveryWhere.entity.GameAllEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameAllTwoEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserEntity;
import org.nuaa.undefined.BigDataEveryWhere.entity.GameUserResEntity;

import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 15:27
 * @Description:
 */
public interface GameUserService {
    //获取花费时间最长的用户（Top10）
    public List<GameUserEntity> listTotalTimeTopUser();
    //每天新增用户数
    public List<GameUserResEntity> listDayNewUser();
    //用户四个活跃时段数量
    public List<GameUserResEntity> listMainPlayTimeDistribution();
    //操作系统的分布
    public List<GameUserResEntity> listOsDistribution();
    //每天登录次数
    public List<GameUserResEntity> listDayLoginUserNum();
    //不同登陆天数用户统计
    public List<GameUserResEntity> listDayLoginTimeNum();
    //用户行为信息提要
    public GameAllEntity UserBehaviorInfo();
    //游戏留存信息提要
    public GameAllTwoEntity GameRetentionInfo();
}
