package com.lmt.service.impl;

import com.lmt.dao.CommentDao;
import com.lmt.domain.Comment;
import com.lmt.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        List<Comment> comments=commentDao.findByBlogIdAndParentCommentNull(blogId);//所有父评论
        List<Comment> allComments= eachComment(comments);
        return allComments;
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentList=new ArrayList<>();
        for (Comment comment : comments) {
            comment.setReplyComments(commentDao.findReplyCommentsByParentId(comment.getId()));//设置父评论的回复
            commentList.add(comment);//存储所有父评论
        }
        combineChildren(commentList);//合并
        return commentList;
    }

    private List<Comment> tempReplys=new ArrayList<>();

    private void combineChildren(List<Comment> commentList) {
        for (Comment comment:commentList){//遍历所有组装好的父评论
            List<Comment> replys=comment.getReplyComments();//获取父评论的第一级子评论
            for (Comment reply:replys){//遍历所有第一级子评论
                tempReplys.add(reply);//整合所有子评论
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);//覆盖之前的子评论列表
            tempReplys=new ArrayList<>();//开始下一个第一级子评论的整合
        }
    }

    private void recursively(Comment comment) {
        comment.setReplyComments(commentDao.findReplyCommentsByParentId(comment.getId()));
        comment.setParentComment(commentDao.findByParentCommentId(comment.getParentCommentId()));
        if (comment.getReplyComments().size()>0){
            List<Comment> replys=comment.getReplyComments();
            for (Comment reply:replys){
                reply.setReplyComments(commentDao.findReplyCommentsByParentId(reply.getId()));
                reply.setParentComment(commentDao.findByParentCommentId(reply.getParentCommentId()));
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0)
                    recursively(reply);
            }
        }
    }

    @Override
    public void saveComment(Comment comment) {

        Long parentCommentId=comment.getParentComment().getId();
        comment.setParentCommentId(parentCommentId);
        if (parentCommentId!=-1){
            comment.setParentComment(commentDao.findByParentCommentId(parentCommentId));
        }else {
            comment.setParentCommentId((long) -1);
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        commentDao.saveComment(comment);
    }
}

