package com.ming;

import com.ming.common.VO.ArticleVO;
import com.ming.common.VO.CommentVo;
import com.ming.entity.Category;
import com.ming.mapper.ArticleMapper;
import com.ming.mapper.CommentMapper;
import com.ming.service.CategoryService;
import com.ming.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ming
 * @data 2021/6/22 19:47
 */

@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    CategoryService categoryService;
    @Test
    public void test01(){
        List<Category> list = categoryService.list();
        System.out.println(list);
    }

    @Test
    public void test02(){
        ArticleVO article = articleMapper.queryArticleVOById(1L);
        System.out.println(article);
    }

    @Test
    public void test03() {
        System.out.println(redisTemplate.opsForSet().add("list", 1));
        Long list = redisTemplate.opsForSet().add("list", 1);
        System.out.println(list);

        redisTemplate.expire("list",1, TimeUnit.MINUTES);
        System.out.println(redisTemplate.getExpire("list"));

    }

    @Test
    public void test04() {
        List<CommentVo> comments = commentService.getComments(1L);
        System.out.println(comments);
    }

}
