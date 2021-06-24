package com.ming.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.common.lang.Result;
import com.ming.entity.History;
import com.ming.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${myBlog.domainName}")
    private String DOMAIN_NAME;

    private static final String VIEW_NUMBER = "view:number";

    @GetMapping("/views")
    public Result getViews() {
        Integer newNum = (Integer) redisTemplate.opsForValue().get(VIEW_NUMBER);
        if (newNum == null) {
            History history = historyService.getOne(new QueryWrapper<History>().eq("domain_name", DOMAIN_NAME));
            redisTemplate.opsForValue().set(VIEW_NUMBER,history.getViewNumber());
            return Result.success(history.getViewNumber());
        }
        return Result.success(newNum.longValue());
    }


}
