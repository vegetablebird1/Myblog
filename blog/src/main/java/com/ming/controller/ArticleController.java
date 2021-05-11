package com.ming.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ming.common.lang.Result;
import com.ming.entity.Article;
import com.ming.service.ArticleService;
import com.ming.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    //分页展示所有文章
    @GetMapping("/articles")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage iPage = articleService.page(page,new QueryWrapper<Article>().orderByDesc("create_time"));
        return Result.success(iPage);
    }

    //查询文章详情
    @GetMapping("/article/{id}")
    public Result detail(@PathVariable Long id){
        Article article = articleService.getById(id);
        Assert.notNull(article,"该博客已被删除");

        return Result.success(article);
    }

    //编辑或添加文章,需要认证
    @RequiresAuthentication
    @PostMapping("/article/edit")
    public Result edit(@Validated @RequestBody Article article){
        Article temp = null;
        if (article.getArticleId() == null){
            //说明为新建
            temp = new Article();
            temp.setUserId(ShiroUtils.getProfile().getUserId());//设置文章用户id当前用户
        }else {
            //编辑状态
            temp = articleService.getById(article.getArticleId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getUserId()),"你不能修改其他人文章");
        }
        BeanUtils.copyProperties(article,temp,"articleId","userId","create_time","update_time");


        articleService.saveOrUpdate(temp);
        return Result.success(null);
    }

}
