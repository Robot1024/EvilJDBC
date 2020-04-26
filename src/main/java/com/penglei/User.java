package com.penglei;

import java.util.Date;

/**
 * @author ascetic
 * @version 1.0
 * @ClassName User
 * @Description 用户实体
 * @date 2020-04-26 19:32
 */
public class User {

    private Long id;
    private String username;
    private String password;
    private String birthday;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
