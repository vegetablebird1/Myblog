package com.ming.mapper;

import com.ming.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */

@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    Article queryBook(Long id);

}
