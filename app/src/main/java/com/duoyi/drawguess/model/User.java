package com.duoyi.drawguess.model;

/**
 * Created by YMlion on 2018/1/2.
 */

public class User {
    private String token;
    private String id;
    private String name;
    private String avatar;
    private boolean state;
    private String gameName;
    private String roomId;

    public User(String token, String id, String name, String avatar, boolean state, String gameName,
            String roomId) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.state = state;
        this.gameName = gameName;
        this.roomId = roomId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
