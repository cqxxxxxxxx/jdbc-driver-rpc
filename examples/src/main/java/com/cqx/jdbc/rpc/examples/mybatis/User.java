package com.cqx.jdbc.rpc.examples.mybatis;


import java.util.Random;

public class User {

    private Integer id;

    private String name;

    private Double height;

    private Long createTime;


    public static User genOne() {
        final Random random = new Random();
        User user = new User();
        user.setId(random.nextInt());
        user.setName("cqx" + random.nextInt());
        user.setHeight(random.nextDouble());
        user.setCreateTime(random.nextLong());
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}