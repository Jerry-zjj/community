package com.zjj.community.community.service;

import com.zjj.community.community.enums.CommentTypeEnum;
import com.zjj.community.community.exception.CustomizeErrorCode;
import com.zjj.community.community.exception.CustomizeException;
import com.zjj.community.community.mapper.CommentMapper;
import com.zjj.community.community.mapper.QuestionExtMapper;
import com.zjj.community.community.mapper.QuestionMapper;
import com.zjj.community.community.model.Comment;
import com.zjj.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public  void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null ||!CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
           Comment dbComment= commentMapper.selectByPrimaryKey(comment.getParentId());
           if (dbComment==null){
               throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUNT);
           }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question= questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }

    }
}
