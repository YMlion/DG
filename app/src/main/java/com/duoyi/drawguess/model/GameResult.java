package com.duoyi.drawguess.model;

import java.util.List;

/**
 * 游戏结果
 * Created by YMlion on 2018/1/12.
 */

public class GameResult {
    /**
     * 用户最终获得的数量
     */
    public int num;
    /**
     * 胜者头像
     */
    public List<String> winners;
    /**
     * 败者ids
     */
    public List<String> failures;
}
