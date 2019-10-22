package com.zjj.community.community.mapper;

import com.zjj.community.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Controller;

@Mapper
public interface  UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url)values('${name}',${accountId},'${token}',${gmtCreate},${gmtModified},'${avatarUrl}')")
    void insert(User user);
    @Select("SELECT * FROM user WHERE token='${token}'")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE id=${id}")
    User findById(@Param("id") int id);

    @Select("SELECT * FROM user WHERE account_id='${accountId}'")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("update user set name='${name}' ,token='${token}',gmt_modified='${gmtModified}',avatar_url='${avatarUrl}' WHERE id=${id}")
    void update(User user);
}

