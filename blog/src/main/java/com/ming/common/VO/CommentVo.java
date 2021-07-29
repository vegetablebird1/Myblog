package com.ming.common.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论
 *
 * @author ming
 * @data 2021/7/17 20:14
 */

@Data
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    Long commentId;

    Long articleId;

    Long parentId;

    Long userId;

    String username;

    String avatar;

    /**
     * 评论内容
     */
    String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;

    /**
     * 子评论
     */
    List<CommentVo> children;
}
