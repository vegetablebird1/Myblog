package com.ming.service.impl;

import com.ming.entity.History;
import com.ming.mapper.HistoryMapper;
import com.ming.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
