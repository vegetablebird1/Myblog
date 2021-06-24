package com.ming.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.entity.History;
import com.ming.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author ming
 * @data 2021/6/23 22:33
 */

@EnableAsync
@EnableScheduling
@Component
public class ScheduleTask {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HistoryService historyService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);
    private static final String VIEW_NUMBER = "view:number";

    @Value("${myBlog.domainName}")
    private String DOMAIN_NAME;

    //更新历史记录
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void updateHistory() {
        Integer newNumber = (Integer) redisTemplate.opsForValue().get(VIEW_NUMBER);
        Long views = null;
        History history = historyService.getOne(new QueryWrapper<History>().eq("domain_name",DOMAIN_NAME));
        if (newNumber != null) {
            views = newNumber.longValue();
            if (views > history.getViewNumber()) {
                //更新
                history.setViewNumber(views);
                historyService.saveOrUpdate(history);
            } else {
                redisTemplate.opsForValue().set(VIEW_NUMBER, history.getViewNumber());
            }
        } else {
            redisTemplate.opsForValue().set(VIEW_NUMBER, history.getViewNumber());
        }
        LOGGER.info("访问历史数记录更新成功! 目前访问记录数为:{}",history.getViewNumber());
    }

}
