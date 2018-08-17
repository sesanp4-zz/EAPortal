/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;

/**
 *
 * @author omoniyikolade
 */
public class UserRequest {
    private String publickey,option,value;
    public UserInfo userinfo;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }
}
