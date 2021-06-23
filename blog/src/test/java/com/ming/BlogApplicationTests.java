package com.ming;

import com.ming.common.VO.ArticleVO;
import com.ming.entity.Category;
import com.ming.mapper.ArticleMapper;
import com.ming.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;

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

}
