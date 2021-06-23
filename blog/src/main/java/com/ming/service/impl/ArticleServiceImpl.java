package com.ming.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.common.VO.ArticleVO;
import com.ming.entity.Article;
import com.ming.mapper.ArticleMapper;
import com.ming.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public ArticleVO queryArticleVOById(Long id) {
        return articleMapper.queryArticleVOById(id);
    }
}
