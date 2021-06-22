package com.ming;

import com.ming.entity.Article;
import com.ming.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Test
    public void test01(){
        redisTemplate.opsForValue().set("key","value");
        Object o = redisTemplate.opsForValue().get("key");
        System.out.println(o);
    }

    @Test
    public void test02(){
        Article article = articleMapper.queryBook(1L);
        System.out.println(article);
    }

}
