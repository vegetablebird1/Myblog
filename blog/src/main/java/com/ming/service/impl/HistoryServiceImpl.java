package com.ming.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.common.constant.RedisConstant;
import com.ming.entity.History;
import com.ming.mapper.HistoryMapper;
import com.ming.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    RedisTemplate redisTemplate;

    //记录网站访问次数，同一用户间隔3分钟访问有效
    @Override
    public void incrementViews(String address) {
        Long nowMillis = System.currentTimeMillis();
        double now = nowMillis.doubleValue();
        Set<String> set = redisTemplate.opsForZSet().rangeByScore(RedisConstant.VIEW_USER_SET, now - 3 * 60 * 1000, now);
        if (!set.contains(address)) {
            log.info("ip: {} 访问了网站",address);
            //有效，更新数据
            redisTemplate.opsForValue().increment(RedisConstant.VIEW_NUMBER);
        }
        //更新访问
        redisTemplate.opsForZSet().add(RedisConstant.VIEW_USER_SET,address,now);

        //删除不在区间内的
        redisTemplate.opsForZSet().removeRangeByScore(RedisConstant.VIEW_USER_SET,0, now - 3 * 60 * 1000 - 1);
    }
}
