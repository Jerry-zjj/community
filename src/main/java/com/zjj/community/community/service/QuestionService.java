package com.zjj.community.community.service;

import com.zjj.community.community.dto.PaginationDTO;
import com.zjj.community.community.dto.QuestionDTO;
import com.zjj.community.community.mapper.QuestionMapper;
import com.zjj.community.community.mapper.UserMapper;
import com.zjj.community.community.model.Question;
import com.zjj.community.community.model.QuestionExample;
import com.zjj.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionMapper questionMapper;
    @Autowired
    private PaginationDTO paginationDTO;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totolCount = questionMapper.countByExample(new QuestionExample());
        Integer totalpage=(totolCount%size==0)?(totolCount/size):(totolCount/size+1);
        Integer offset=(page<1)?0:size*(page-1);
        if (page>totalpage){
            offset=size*(totalpage-1);
        }
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
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
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totolCount = questionMapper.countByExample(questionExample);
        Integer totalpage=(totolCount%size==0)?(totolCount/size):(totolCount/size+1);
        Integer offset=(page<1)?0:size*(page-1);
        if (page>totalpage){
            offset=size*(totalpage-1);
        }
        QuestionExample example=new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO=new PaginationDTO();
        for (Question question:questions){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
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
        Question question= questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO=new QuestionDTO();
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            Question updateQuestion=new Question();
            updateQuestion.setGmtCreate(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example=new QuestionExample();
            example.createCriteria().andCreatorEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);
        }
    }
}
