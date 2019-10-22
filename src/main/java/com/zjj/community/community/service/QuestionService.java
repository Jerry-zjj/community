package com.zjj.community.community.service;

import com.zjj.community.community.dto.PaginationDTO;
import com.zjj.community.community.dto.QuestionDTO;
import com.zjj.community.community.mapper.QuesstionMappe;
import com.zjj.community.community.mapper.UserMapper;
import com.zjj.community.community.model.Question;
import com.zjj.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuesstionMappe quesstionMappe;
    @Autowired
    private PaginationDTO paginationDTO;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totolCount = quesstionMappe.count();
        Integer totalpage=(totolCount%size==0)?(totolCount/size):(totolCount/size+1);
        Integer offset=(page<1)?0:size*(page-1);
        if (page>totalpage){
            offset=size*(totalpage-1);
        }
        List<Question> questions=quesstionMappe.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOList);
        paginationDTO.setPageination(totolCount,page,size);
        return paginationDTO;
    }

    public PaginationDTO listById(Integer userId, Integer page, Integer size) {
        Integer totolCount = quesstionMappe.countByUserId(userId);
        Integer totalpage=(totolCount%size==0)?(totolCount/size):(totolCount/size+1);
        Integer offset=(page<1)?0:size*(page-1);
        if (page>totalpage){
            offset=size*(totalpage-1);
        }
        List<Question> questions=quesstionMappe.ListByUserId(userId, offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestion(questionDTOList);
        paginationDTO.setPageination(totolCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {
        Question question= quesstionMappe.getQuestionById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        User user=userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            quesstionMappe.create(question);
        }else {
            question.setGmtCreate(System.currentTimeMillis());
            quesstionMappe.update(question);
        }
    }
}
