package com.iven.app.bean;

import java.io.Serializable;

/**
 * @author Iven
 * @date 2017/3/15 16:03
 * @Description
 */

public class CompanyBean implements Serializable{
    private String name;
    private String code;
    private String pinyinName;

    public String getPinyinName() {
        return pinyinName;
    }

    public CompanyBean setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CompanyBean setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyBean{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", pinyinName='" + pinyinName + '\'' +
                '}';
    }
}
