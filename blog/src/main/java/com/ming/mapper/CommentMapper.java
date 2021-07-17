package com.ming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.common.VO.CommentVo;
import com.ming.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 文章评论表 Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-07-17
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentVo> getComments(Long articleId);
}
