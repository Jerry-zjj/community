package com.zjj.community.community.dto;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
