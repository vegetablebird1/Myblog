package com.ming.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.common.VO.ArticleVO;
import com.ming.common.lang.Result;
import com.ming.entity.Article;
import com.ming.service.ArticleService;
import com.ming.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    private static final String ARTICLE_PREFIX_NAME = "queryArticleById:";
    private static final String VIEW_NUMBER = "view:number";

    //分页展示所有文章
    @GetMapping("/articles")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        redisTemplate.opsForValue().increment(VIEW_NUMBER);
        Page page = new Page(currentPage,5);
        IPage iPage = articleService.page(page,new QueryWrapper<Article>().orderByDesc("create_time"));
        return Result.success(iPage);
    }

    //查询文章详情
    @GetMapping("/article/{id}")
    public Result detail(@PathVariable Long id){
        ArticleVO article = null;
        String articleInfo = (String) redisTemplate.opsForValue().get(ARTICLE_PREFIX_NAME + id);
        if (articleInfo != null) {
            try {
                article = objectMapper.readValue(articleInfo, ArticleVO.class);
            } catch (JsonProcessingException e) {
                LOGGER.error("JSON反序列化出错",e);
            }
            return Result.success(article);
        }
        article = articleService.queryArticleVOById(id);
        Assert.notNull(article,"该博客已被删除");

        try {
            articleInfo = objectMapper.writeValueAsString(article);
            redisTemplate.opsForValue().set(ARTICLE_PREFIX_NAME + id, articleInfo, 5, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON序列化出错",e);
        }
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

        if(article.getArticleId() != null) {
            redisTemplate.delete(ARTICLE_PREFIX_NAME + article.getArticleId());
        }

        articleService.saveOrUpdate(temp);
        return Result.success(null);
    }


    //删除文章
    @RequiresAuthentication
    @GetMapping("/article/delete/{id}")
    public Result delete(@PathVariable Long id){
        //逻辑删除，管理员可回收
        boolean flag = articleService.removeById(id);
        if (flag){
            redisTemplate.delete(ARTICLE_PREFIX_NAME + id);
            return Result.success("删除成功");
        }else {
            return Result.fail("删除失败");
        }
    }
}
