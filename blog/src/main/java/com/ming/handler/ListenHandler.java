package com.ming.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.entity.History;
import com.ming.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 监听Spring
 * @author ming
 * @data 2021/6/24 13:24
 */

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    @Autowired
    HistoryService historyService;
    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenHandler.class);

    private static final String VIEW_USER_SET = "view:users:ip";
    private static final String VIEW_NUMBER = "view:number";

    @Value("${myBlog.domainName}")
    private String DOMAIN_NAME;

    //把数据放入redis
    @PostConstruct
    public void beforeClose() {
        LOGGER.info("bean填充属性成功后，环境进行初始化操作");
        redisTemplate.opsForZSet().add(VIEW_USER_SET,"",0.0);
        redisTemplate.opsForValue().set(
                VIEW_NUMBER,
                historyService.getOne(
                        new QueryWrapper<History>().eq("domain_name",DOMAIN_NAME)).getViewNumber());
        LOGGER.info("redis数据初始化完成");
    }

    //将数据持久化到数据库中
    @PreDestroy
    public void beforeDestroy() {
        LOGGER.info("bean销毁前，进行数据持久化");
        History history = historyService.getOne(new QueryWrapper<History>().eq("domain_name", DOMAIN_NAME));
        Integer views = (Integer) redisTemplate.opsForValue().get(VIEW_NUMBER);
        history.setViewNumber(views.longValue());
        historyService.saveOrUpdate(history);
        LOGGER.info("数据持久化完成");
    }

}
