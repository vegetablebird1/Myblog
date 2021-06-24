package com.ming.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.entity.History;
import com.ming.mapper.HistoryMapper;
import com.ming.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 历史记录 服务实现类
 * </p>
 *
 * @author ming
 * @since 2021-06-23
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    RedisTemplate redisTemplate;

    private static final String VIEW_USER_SET = "view:users:ip";
    private static final String VIEW_NUMBER = "view:number";

    //记录网站访问次数，同一用户间隔3分钟访问有效
    @Override
    public void incrementViews(String address) {
        Long nowMillis = System.currentTimeMillis();
        double now = nowMillis.doubleValue();
        Set<String> set = redisTemplate.opsForZSet().rangeByScore(VIEW_USER_SET, now - 3 * 60 * 1000, now);
        if (!set.contains(address)) {
            //有效，更新数据
            redisTemplate.opsForValue().increment(VIEW_NUMBER);
        }
        //更新访问
        redisTemplate.opsForZSet().add(VIEW_USER_SET,address,now);

        //删除不在区间内的
        redisTemplate.opsForZSet().removeRangeByScore(VIEW_USER_SET,0, now - 3 * 60 * 1000 - 1);
    }
}
