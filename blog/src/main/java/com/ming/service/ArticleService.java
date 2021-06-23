package com.ming.service;

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

    ArticleVO queryArticleById(Long id);

}
