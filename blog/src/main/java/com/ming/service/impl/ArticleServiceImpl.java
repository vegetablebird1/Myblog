package com.ming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.common.VO.ArticleVO;
import com.ming.common.VO.CommentVo;
import com.ming.common.constant.RedisConstant;
import com.ming.common.enumeration.ArticleStatusCode;
import com.ming.common.exception.ArticleException;
import com.ming.entity.Article;
import com.ming.mapper.ArticleMapper;
import com.ming.service.ArticleService;
import com.ming.service.CommentService;
import com.ming.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ArticleVO queryArticleVOById(Long id) {
        ArticleVO article = null;
        String articleInfo = (String) redisTemplate.opsForValue().get(RedisConstant.ARTICLE_PREFIX_NAME + id);
        if (StringUtils.hasText(articleInfo)) {
            try {
                article = objectMapper.readValue(articleInfo, ArticleVO.class);
            } catch (JsonProcessingException e) {
                log.error("JSON反序列化出错", e);
            }
            return article;
        } else if (redisTemplate.hasKey(RedisConstant.ARTICLE_PREFIX_NAME + id)) {
            log.error("用户访问了不存在或已删除的文章");
            return null;
        }
        //缓存没命中，查询数据库，使用锁机制解决高并发下缓存击穿
        try {
            article = queryArticleVO(id);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化出错", e);
        } catch (ArticleException e) {
            log.error(e.getMessage(), e);
        }
        return article;
    }

    private ArticleVO queryArticleVO(Long id) throws JsonProcessingException {
        //service单例能锁住
        synchronized (this) {
            ArticleVO article = null;
            //再次判断redis中是否有
            String articleInfo = (String) redisTemplate.opsForValue().get(RedisConstant.ARTICLE_PREFIX_NAME + id);
            if (StringUtils.hasText(articleInfo)) {
                article = objectMapper.readValue(articleInfo, ArticleVO.class);
                return article;
            } else if (redisTemplate.hasKey(RedisConstant.ARTICLE_PREFIX_NAME + id)) {
                throw new ArticleException(ArticleStatusCode.NOT_EXIST);
            }


            //查数据库
            article = articleMapper.queryArticleVOById(id);
            List<CommentVo> comments = commentService.getComments(id);
            if (article != null && comments != null) {
                article.setComments(comments);
            }
            if (article != null) {
                articleInfo = objectMapper.writeValueAsString(article);
                redisTemplate.opsForValue().set(RedisConstant.ARTICLE_PREFIX_NAME + id, articleInfo, 5, TimeUnit.MINUTES);
            } else {
                //缓存空数据
                redisTemplate.opsForValue().set(RedisConstant.ARTICLE_PREFIX_NAME + id, "", 5, TimeUnit.MINUTES);
            }
            return article;
        }
    }

    //TODO 分页缓存不符合预期

    // @Cacheable(value = {"article"},key = "#root.method.name")
    @Override
    public IPage<Article> queryPage(Long currentPage, int size) {
        //分页查询
        Page<Article> page = new Page<>(currentPage, size);
        return this.page(page, new QueryWrapper<Article>().orderByDesc("create_time"));
    }

    /**
     * 更新文章，删除已有缓存 value是缓存所在区域 Article::queryPage
     *
     * @param article
     */
    //@CacheEvict(value = {"article"},key = "'queryPage'")
    @Override
    public void saveOrUpdateArticle(Article article) {
        Article temp = null;
        if (article.getArticleId() == null) {
            //说明为新建
            temp = new Article();
            temp.setUserId(ShiroUtils.getProfile().getUserId());//设置文章用户id当前用户
        } else {
            //编辑状态
            temp = this.getById(article.getArticleId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getUserId()), "你不能修改其他人文章");
        }
        BeanUtils.copyProperties(article, temp, "articleId", "userId", "create_time", "update_time");

        if (article.getArticleId() != null) {
            redisTemplate.delete(RedisConstant.ARTICLE_PREFIX_NAME + article.getArticleId());
        }
        this.saveOrUpdate(temp);
    }
}
