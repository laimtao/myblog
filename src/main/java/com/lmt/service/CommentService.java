package com.lmt.service;

import com.lmt.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByBlogId(Long blogId);

    void saveComment(Comment comment);
}
