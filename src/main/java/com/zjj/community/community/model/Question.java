package com.zjj.community.community.model;

import lombok.Data;

@Data
public class Question {
    private  Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;

}
