package com.ming.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ming.common.VO.ArticleVO;
import com.ming.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
public interface ArticleService extends IService<Article> {

    ArticleVO queryArticleVOById(Long id);

    IPage<Article> queryPage(Long currentPage, int size);

    void saveOrUpdateArticle(Article article);

}
