package com.zjj.community.community.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class PaginationDTO {
    private  List<QuestionDTO> question;
    private  boolean showPrevious;
    private  boolean showFirstPage;
    private  boolean showNext;
    private  boolean showEndPage;
    private  Integer page;
    private  List<Integer> pages=new ArrayList<>();
    private  int totalpage;

    public  void setPageination(Integer totolCount, Integer page, Integer size) {
        //算出总页数
        totalpage=(totolCount%size==0)?(totolCount/size):(totolCount/size+1);
        if (page<1){
            page=1;
        }else if (page>totalpage){
            page=totalpage;
        }
        this.page=page;

        pages.add(page);
        for(int i = 1; i<=3; i++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalpage){
                pages.add(page+i);
            }
        }
        //上一页
        showPrevious=(page==1)?false:true;
        //下一页
        showNext=(totalpage==page)?false:true;
        //首页跳转
        showFirstPage=(pages.contains(1))?false:true;
        //最后一页跳转
        showEndPage=(pages.contains(totalpage))?false:true;

    }
}
