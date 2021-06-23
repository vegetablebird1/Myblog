package com.ming.common.VO;

import com.ming.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ming
 * @data 2021/6/23 11:05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long articleId;

    private String categoryName;

    private Long userId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "摘要不能为空")
    private String digest;

    @NotBlank(message = "内容不能为空")
    private String content;
}
