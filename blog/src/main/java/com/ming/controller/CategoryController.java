package com.ming.controller;


import com.ming.common.lang.Result;
import com.ming.entity.Category;
import com.ming.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-05-05
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public Result getAllCategory() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

}
