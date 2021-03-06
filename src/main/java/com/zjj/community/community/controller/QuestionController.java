package com.zjj.community.community.controller;

import com.zjj.community.community.dto.QuestionDTO;
import com.zjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDTO questionDTO= questionService.getQuestionById(id);
        //阅读数增加
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
