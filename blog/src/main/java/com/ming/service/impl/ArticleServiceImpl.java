package com.ming.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.common.VO.ArticleVO;
import com.ming.entity.Article;
import com.ming.mapper.ArticleMapper;
import com.ming.service.ArticleService;
import com.ming.utils.ShiroUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String ARTICLE_PREFIX_NAME = "queryArticleById:";

    @Override
    public ArticleVO queryArticleVOById(Long id) {
        return articleMapper.queryArticleVOById(id);
    }

    //TODO 分页缓存不符合预期

    // @Cacheable(value = {"article"},key = "#root.method.name")
    @Override
    public IPage<Article> queryPage(Long currentPage, int size) {
        //分页查询
        Page<Article> page = new Page<>(currentPage, size);
        return this.page(page,new QueryWrapper<Article>().orderByDesc("create_time"));
    }

    /**
     * 更新文章，删除已有缓存 value是缓存所在区域 Article::queryPage
     * @param article
     */
    //@CacheEvict(value = {"article"},key = "'queryPage'")
    @Override
    public void saveOrUpdateArticle(Article article) {
        Article temp = null;
        if (article.getArticleId() == null){
            //说明为新建
            temp = new Article();
            temp.setUserId(ShiroUtils.getProfile().getUserId());//设置文章用户id当前用户
        }else {
            //编辑状态
            temp = this.getById(article.getArticleId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getUserId()),"你不能修改其他人文章");
        }
        BeanUtils.copyProperties(article,temp,"articleId","userId","create_time","update_time");

        if(article.getArticleId() != null) {
            redisTemplate.delete(ARTICLE_PREFIX_NAME + article.getArticleId());
        }
        this.saveOrUpdate(temp);
    }
}
