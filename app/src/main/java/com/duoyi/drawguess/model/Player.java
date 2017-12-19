package com.duoyi.drawguess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家基本信息
 * Created by YMlion on 2017/12/15.
 */

public class Player {
    private String pid;
    private String name;
    private String avatar;
    private boolean state;

    public Player(String pid, String name, String avatar, boolean state) {
        this.pid = pid;
        this.name = name;
        this.avatar = avatar;
        this.state = state;
    }

    public Player(String name, String avatar, boolean state) {
        this.name = name;
        this.avatar = avatar;
        this.state = state;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isReady() {
        return state;
    }

    public void setReady(boolean state) {
        this.state = state;
    }

    public static List<Player> mockList(int num) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Player p = new Player("张三" + i,
                    "http://img5.imgtn.bdimg.com/it/u=1082195034,4139527125&fm=27&gp=0.jpg", false);
            if (i % 2 == 0) {
                p.setReady(true);
            }
            list.add(p);
        }
        return list;
    }
}
