package com.zjj.community.community.controller;

import com.zjj.community.community.dto.QuestionDTO;
import com.zjj.community.community.model.Question;
import com.zjj.community.community.model.User;
import com.zjj.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    public QuestionService questionService;

    //提问
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Long id,
                       Model model,
                       HttpServletRequest request){
       QuestionDTO question= questionService.getQuestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        request.getSession().setAttribute("id",id);
        return "/publish";
    }

    @GetMapping("/publish")
    public  String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public  String doPublish(
            @RequestParam(value = "title" ,required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag" ,required = false) String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        Long id = (Long) request.getSession().getAttribute("id");
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
