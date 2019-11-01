package com.zjj.community.community.mapper;

import com.zjj.community.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}
