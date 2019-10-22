package com.zjj.community.community.mapper;

import com.zjj.community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuesstionMappe {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)" +
            "values('${title}','${description}',${gmtCreate},${gmtModified},${creator},'${tag}')")
    void  create(Question question);

    @Select("SELECT * FROM question limit ${offset},${size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("SELECT * FROM question WHERE creator=#{userId} limit ${offset},${size}")
    List<Question> ListByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question WHERE creator=#{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question WHERE id=#{id}")
    Question getQuestionById(@Param(value = "id")Integer id);

    @Update("update question set title='${title}' ,description='${description}',gmt_modified='${gmtModified}',tag='${tag}' WHERE id=${id}")
    void update(Question question);
}
