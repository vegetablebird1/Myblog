package com.ming.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.common.VO.CommentVo;
import com.ming.entity.Comment;
import com.ming.mapper.CommentMapper;
import com.ming.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章评论表 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-07-17
 */
@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    //根据文章id获得评论
    @Override
    public List<CommentVo> getComments(Long articleId) {
        List<CommentVo> allComment = commentMapper.getComments(articleId);
        List<CommentVo> res = allComment.stream()
                .map(comment -> {
                    comment.setChildren(getSonComment(comment.getCommentId(), allComment));
                    return comment;
                })
                //获得所有1级评论
                .filter(comment -> comment.getParentId() == 0)
                .collect(Collectors.toList());
        return res;
    }

    //获得子评论
    private List<CommentVo> getSonComment(Long parentId, List<CommentVo> commentVoList) {
        List<CommentVo> sonList = commentVoList.stream().filter(comment -> {
            return parentId.equals(comment.getParentId());
        }).collect(Collectors.toList());
        return sonList;
    }

    @Override
    public boolean addComment(Comment comment, Long userId) {
        if (userId == null) {
            log.error("评论失败");
            return false;
        }
        comment.setUserId(userId);
        log.info("用户: {} 进行了评论", userId);
        return this.save(comment);

    }
}
