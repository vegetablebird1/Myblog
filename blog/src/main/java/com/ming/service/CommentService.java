package com.ming.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ming.common.VO.CommentVo;
import com.ming.entity.Comment;

import java.util.List;

/**
 * <p>
 * 文章评论表 服务类
 * </p>
 *
 * @author ming
 * @since 2021-07-17
 */
public interface CommentService extends IService<Comment> {

    List<CommentVo> getComments(Long articleId);

    boolean addComment(Comment comment, Long userId);

}
