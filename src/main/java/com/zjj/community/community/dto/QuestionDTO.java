package com.zjj.community.community.dto;

import com.zjj.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private  int id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private User user;
}
