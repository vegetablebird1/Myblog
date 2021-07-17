package com.ming.controller;


import com.ming.common.VO.CommentVo;
import com.ming.common.lang.Result;
import com.ming.entity.Comment;
import com.ming.service.CommentService;
import com.ming.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-07-17
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    //储存用户id
    private final ThreadLocal<Long> userId = ThreadLocal.withInitial(() -> ShiroUtils.getProfile().getUserId());

    @Autowired
    CommentService commentService;

    @GetMapping("/{id}")
    public Result getComments(@PathVariable("id") Long articleId) {
        List<CommentVo> comments = commentService.getComments(articleId);
        return Result.success(comments);
    }


    @RequiresAuthentication
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        boolean save = commentService.addComment(comment, userId.get());
        if (save) {
            return Result.success("评论成功");
        }
        return Result.fail("评论失败");
    }


}
