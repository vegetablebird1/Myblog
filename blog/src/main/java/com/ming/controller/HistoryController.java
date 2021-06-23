package com.ming.controller;


import com.ming.common.lang.Result;
import com.ming.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 历史记录 前端控制器
 * </p>
 *
 * @author ming
 * @since 2021-06-23
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HistoryService historyService;

    private static final String VIEW_NUMBER = "view:number";

    @GetMapping("/views")
    public Result getViews() {
        Long views = (Long) redisTemplate.opsForValue().get(VIEW_NUMBER);
        return Result.success(views);
    }


}
