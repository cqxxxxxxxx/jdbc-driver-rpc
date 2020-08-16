package com.cqx.jdbc.rpc.examples.mybatis;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAll();

    User selectById();
}