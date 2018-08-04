package org.nuaa.undefined.BigDataEveryWhere.controller;

import org.nuaa.undefined.BigDataEveryWhere.entity.*;
import org.nuaa.undefined.BigDataEveryWhere.service.GameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: cyw35
 * @Date: 2018/8/3 17:03
 * @Description:
 */
@Controller
@CrossOrigin
@RequestMapping("/game/user")
public class GameUserController {
    @Autowired
    private GameUserService gameUserService;

    //获取花费时间最长的用户（Top10）
    @RequestMapping(value = "/totalTimeTopUserList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserEntity> listTotalAmountsTopData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listTotalTimeTopUser()
        );
    }
    //每天新增用户数
    @RequestMapping(value = "/dayNewUserList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserResEntity> listDayNewUserData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listDayNewUser()
        );
    }
    //用户四个活跃时段数量
    @RequestMapping(value = "/mainPlayTimeDistributionList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserResEntity> listMainPlayTimeDistributionData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listMainPlayTimeDistribution()
        );
    }
    //操作系统的分布
    @RequestMapping(value = "/osDistributionList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserResEntity> listOsDistributionData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listOsDistribution()
        );
    }
    //每天登录次数
    @RequestMapping(value = "/dayLoginUserNumList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserResEntity> listDayLoginUserNumData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listDayLoginUserNum()
        );
    }
    //不同登陆天数用户统计
    @RequestMapping(value = "/dayLoginTimeNumList",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameUserResEntity> listDayLoginTimeNumData(){
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                gameUserService.listDayLoginTimeNum()
        );
    }
    //用户行为信息提要
    @RequestMapping(value = "/userBehaviorInfo",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameAllEntity> userBehaviorInfoData(){
        List<GameAllEntity> res = new ArrayList<>(Arrays.asList(gameUserService.UserBehaviorInfo()));
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                res
        );
    }

    //用户留存信息提要
    @RequestMapping(value = "/gameRetentionInfo",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody
    ResponseEntity<GameAllTwoEntity> gameRetentionInfoData(){
        List<GameAllTwoEntity> res = new ArrayList<>(Arrays.asList(gameUserService.GameRetentionInfo()));
        return new ResponseEntity<>(
                Response.GET_DATA_SUCCESS_CODE,
                "获取数据成功",
                res
        );
    }

    //用户信息查询
    @GetMapping("/userInfoList")
    public @ResponseBody
    ResponseEntity<GameUserEntity> userInfoData(String id){
        List <GameUserEntity> res = gameUserService.getUserInfo(id);
        return res != null ?
                new ResponseEntity<>(Response.GET_DATA_SUCCESS_CODE,"获取数据成功",res):
                new ResponseEntity<>(400, "该用户不存在", 0, null);
    }
}
